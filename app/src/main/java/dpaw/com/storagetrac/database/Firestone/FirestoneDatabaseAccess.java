package dpaw.com.storagetrac.database.Firestone;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;

import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.data.UserFireStoreData;
import dpaw.com.storagetrac.database.StorageUnitDatabase;

public class FirestoneDatabaseAccess {

    private static final String STORAGE_UNIT_COLLECTION_NAME = "Storage Units";

    private static final String USER_COLLETION_NAME = "Users";

    private static final String TAG = "firestoneDB";

    /**
     * Firebase firestone database object reference
     */
    private FirebaseFirestore db;

    private FirebaseAuth auth;

    /**
     * Constructor for a FirestoneDatabaseAccess
     */
    public FirestoneDatabaseAccess() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    /**
     * Add a new storage unit in the firestone database
     * @param su storage unit to add to the database
     */
    public void addStorageUnit(final StorageUnit su) {
        if (su.get_fireStoneID() != null) {
            return;
        }

        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document();

        doc.set(su).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    doc.update("_fireStoneID", doc.getId());

                    su.set_fireStoneID(doc.getId());

                    FirebaseUser user = auth.getCurrentUser();

                    addToOwned(su);
                }
            }
        });
    }

    /**
     * Updates the entire storage unit entry in the firestone database
     * @param targetSU target storage unit to update
     */
    public void updateStorageUnit(final StorageUnit targetSU) {
        if (targetSU.get_fireStoneID() == null) {
            return;
        }

        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(targetSU.get_fireStoneID());

        doc.set(targetSU).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    doc.update("_fireStoneID", doc.getId());
                }
            }
        });
    }

    /**
     * Deletes a storage unit from the firestone database
     * @param target stoarge unit to delete from the firestone database
     */
    public void deleteStorage(StorageUnit target) {
        if (target.get_fireStoneID() == null) {
            return;
        }

        db.collection(STORAGE_UNIT_COLLECTION_NAME).document(target.get_fireStoneID()).delete();

        target.set_fireStoneID(null);

        for (Item i : target.get_items()) {
            i.set_fireStoneID(null);
        }
    }

    /**
     * Adds su id to user's owned list
     * @param su storage unit to add to owned list
     */
    public void addToOwned(final StorageUnit su) {
        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference userDoc = db.collection(USER_COLLETION_NAME).document(auth.getCurrentUser().getEmail());

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DocumentSnapshot docSnap = task.getResult();

                        UserFireStoreData userData = docSnap.toObject(UserFireStoreData.class);

                        Log.i("FireStoreDB", userData.toString());

                        userData.get_ownedStorage().add(su.get_fireStoneID());

                        userDoc.set(userData);
                    } else {
                        UserFireStoreData userData = new UserFireStoreData();

                        userData.get_ownedStorage().add(su.get_fireStoneID());

                        userDoc.set(userData);
                    }
                } else {
                    Log.e("FirestoreDB", "Failed to get user data");
                }
            }
        });
    }

    /**
     * Adds the storage unit's firestore id to the user's borrowed list
     * @param email target user's email
     * @param su the storage unit to add to borrowed list
     */
    public void addToBorrowed(String email, final StorageUnit su) {
        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference userDoc = db.collection(USER_COLLETION_NAME).document(email);

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DocumentSnapshot docSnap = task.getResult();

                        UserFireStoreData userData = docSnap.toObject(UserFireStoreData.class);

                        userData.get_ownedStorage().add(su.get_fireStoneID());

                        userDoc.set(userData);
                    } else {
                        UserFireStoreData userData = new UserFireStoreData();

                        userData.get_borrowedStorage().add(su.get_fireStoneID());

                        userDoc.set(userData);
                    }
                }
            }
        });
    }

    public void updateLocalDatabase(final StorageUnitDatabase localDB) {
        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference userDoc = db.collection(USER_COLLETION_NAME).document(auth.getCurrentUser().getEmail());

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    DocumentSnapshot docSnap = task.getResult();

                    UserFireStoreData userData = docSnap.toObject(UserFireStoreData.class);

                    localDB.clearRemoteStorages();

                    final CollectionReference suCollection = db.collection(STORAGE_UNIT_COLLECTION_NAME);

                    // owned storage
                    for (String id : userData.get_ownedStorage()) {
                        suCollection.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                StorageUnit su1 = task.getResult().toObject(StorageUnit.class);
                                localDB.add(su1);
                            }
                        });
                    }

                    // borrowed storages
                    for (String id : userData.get_borrowedStorage()) {
                        suCollection.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                StorageUnit su1 = task.getResult().toObject(StorageUnit.class);
                                localDB.add(su1);
                            }
                        });
                    }

                }
            }
        });
    }

}
