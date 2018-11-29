package dpaw.com.storagetrac.database.Firestone;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;

import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.StorageUnit;

public class FirestoneDatabaseAccess {

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
        data.put("_fireStoneID", su.get_fireStoneID());

        final DocumentReference doc = db.collection("Storage Units").document();

        doc.set(data).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                doc.update("_fireStoneID", doc.getId());
            }
        });

        for (Item item : su.get_items()) {
            addItemsToStorageUnit(doc.getId(), item);
        }
    }

    /**
     * Adds a new item to the storage unit item collection in firestone db
     * @param targetSU the fireStoneID of the targeted Storage Unit
     * @param item item to add to the firebase
     */
    public void addItemsToStorageUnit(String targetSU, final Item item) {
        CollectionReference items = db.collection("Storage Units").document(targetSU).collection("Items");

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
}
