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
     * @param su
     */
    public void addStorageUnit(StorageUnit su) {

        HashMap data = new HashMap<String, String>();

        data.put("Name", su.get_name());
        data.put("_id", su.get_id());
        data.put("_iconId", su.get_iconId());
        data.put("_fireStoneID", su.get_fireStoneID());

        final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document();

        doc.set(data).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                doc.update("_fireStoneID", doc.getId());
            }
        });

        for (Item item : su.get_items()) {
            addItem(su, item);
        }
    }

    /**
     * Updates the entire storage unit entry in the firestone database
     * @param targetSU target storage unit to update
     */
    public void updateStorageUnit(final StorageUnit targetSU) {
        if (targetSU.get_fireStoneID() == null) {
            return;
        }

        db.collection(STORAGE_UNIT_COLLECTION_NAME)
            .document(targetSU.get_fireStoneID())
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    HashMap data = new HashMap<String, String>();

                    data.put("Name", targetSU.get_name());
                    data.put("_id", targetSU.get_id());
                    data.put("_iconId", targetSU.get_iconId());
                    data.put("_fireStoneID", targetSU.get_fireStoneID());

                    final DocumentReference doc = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(targetSU.get_fireStoneID());

                    doc.set(data).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            doc.update("_fireStoneID", doc.getId());
                        }
                    });

                    for (Item item : targetSU.get_items()) {
                        addItem(targetSU, item);
                    }
                }
            }
        );
    }

    /**
     * Adds a new item to the storage unit item collection in firestone db
     * @param targetSU the fireStoneID of the targeted Storage Unit
     * @param item item to add to the firebase
     */
    public void addItem(StorageUnit targetSU, final Item item) {
        if (targetSU.get_fireStoneID() == null) {
            return;
        }

        CollectionReference items = db.collection(STORAGE_UNIT_COLLECTION_NAME).document(targetSU.get_fireStoneID()).collection(ITEMS_COLLECTION_NAME);

        items.add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                DocumentReference itemDoc = task.getResult();

                // set local item's firestoneID
                item.set_fireStoneID(itemDoc.getId());

                // update remote item's firestoneID field
                itemDoc.update("_fireStoneID", item.get_fireStoneID());
            }
        });
    }

    /**
     * Updates an item in the firestone database
     * @param targetSU
     * @param item
     */
    public void updateItem(StorageUnit targetSU, Item item) {
        if (targetSU.get_fireStoneID() == null || item.get_fireStoneID() == null) {
            return;
        }

        DocumentReference itemDoc = db
            .collection(STORAGE_UNIT_COLLECTION_NAME)
            .document(targetSU.get_fireStoneID())
            .collection(ITEMS_COLLECTION_NAME)
            .document(item.get_fireStoneID());

        itemDoc.update("_name", item.get_name());
        itemDoc.update("_quantity", item.get_quantity());
        itemDoc.update("_unit", item.get_unit());
        itemDoc.update("_expiryDate", item.get_expiryDate());
        itemDoc.update("_iconId", item.get_iconId());
    }

    /**
     * Removes item from the firestone database
     * @param targetSU
     * @param item
     */
    public void removeItem(StorageUnit targetSU, Item item) {
        if (targetSU.get_fireStoneID() == null || item.get_fireStoneID() == null) {
            return;
        }

        db.collection(STORAGE_UNIT_COLLECTION_NAME)
                .document(targetSU.get_fireStoneID())
                .collection(ITEMS_COLLECTION_NAME)
                .document(item.get_fireStoneID()).delete();

        item.set_fireStoneID(null);
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
