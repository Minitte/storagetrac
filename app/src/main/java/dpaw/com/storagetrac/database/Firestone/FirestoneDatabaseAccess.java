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
     * Add/update a storage unit in the firestone database
     * @param su storage unit to add to the database
     */
    public void setStorageUnit(final StorageUnit su, final IStorageUnitResultHandler handler) {
        if (auth.getCurrentUser() == null) {
            return;
        }

        final DocumentReference doc;

        if (su.get_fireStoneID() == null) {
            doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document();
        } else {
            doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(su.get_fireStoneID());
        }

        doc.set(su).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    // set id
                    doc.update("_fireStoneID", doc.getId());
                    su.set_fireStoneID(doc.getId());

                    Log.i("BlAH", "ID set to " + su.get_fireStoneID());

                    if (handler != null) {
                        handler.onStorageUnitResult(su);
                    }
                    Log.i(TAG, "Successfully set remote storage unit");
                } else {
                    if (handler != null) {
                        handler.onStorageUnitResult(null);
                    }
                    Log.i(TAG, "Failed to set remote storage unit");
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

        target.get_sharedEmails().clear();
    }

    /**
     * Fetches UserFireStoreData from firestore
     * @param email the targeted email
     * @param handler the result handler
     */
    public void getRemoteUserData(String email, final IUserDataResultHandler handler) {
        DocumentReference userDoc = db.collection(USER_COLLETION_NAME).document(email);

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete() && task.getResult().exists()) {
                    UserFireStoreData userData = task.getResult().toObject(UserFireStoreData.class);
                    handler.onUserDataResult(userData);
                    Log.i(TAG, "Successfully obtained user data");
                } else {
                    handler.onUserDataResult(null);
                    Log.i(TAG, "failed to obtained user data");
                }
            }
        });
    }

    /**
     * updates the userdata
     * @param userData
     */
    public void setRemoteUserData(String email, final UserFireStoreData userData, final IUserDataResultHandler handler) {
        DocumentReference userDoc = db.collection(USER_COLLETION_NAME).document(email);

        userDoc.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (handler != null) {
                        handler.onUserDataResult(userData);
                    }
                    Log.i(TAG, "Successfully wrote user data");
                } else {
                    if (handler != null) {
                        handler.onUserDataResult(null);
                    }
                    Log.i(TAG, "Failed to write user data");
                }
            }
        });
    }

    /**
     * Gets a remote storage unit from the firestore db server
     * @param storageUnitID
     * @param handler
     */
    public void getRemoteStorageUnit(String storageUnitID, final IStorageUnitResultHandler handler) {
        if (auth.getCurrentUser() == null) {
            return;
        }

        DocumentReference suDoc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(storageUnitID);

        suDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    Log.i("LUL", task.getResult().toString());
                    StorageUnit su = task.getResult().toObject(StorageUnit.class);
                    handler.onStorageUnitResult(su);
                    Log.i(TAG, "Successfully obtained remote storage unit");
                } else {
                    handler.onStorageUnitResult(null);

                    Log.i(TAG, "Failed to obtain remote storage unit");
                }
            }
        });
    }

}
