package dpaw.com.storagetrac.database.Firestone;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.StorageUnit;

public class FirestoneDatabaseAccess {

    private static final String STORAGE_UNIT_COLLECTION_NAME = "Storage Units";

    private static final String ITEMS_COLLECTION_NAME = "Items";

    private static final String TAG = "firestoneDB";

    /**
     * Firebase firestone database object reference
     */
    private FirebaseFirestore db;

    /**
     * Constructor for a FirestoneDatabaseAccess
     */
    public FirestoneDatabaseAccess() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Add a new storage unit in the firestone database
     * @param su storage unit to add to the database
     */
    public void addStorageUnit(final StorageUnit su) {
        if (su.get_fireStoneID() != null) {
            return;
        }

        final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document();

        doc.set(su).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                doc.update("_fireStoneID", doc.getId());

                su.set_fireStoneID(doc.getId());
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

        final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(targetSU.get_fireStoneID());

        doc.set(targetSU).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                doc.update("_fireStoneID", doc.getId());
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
}
