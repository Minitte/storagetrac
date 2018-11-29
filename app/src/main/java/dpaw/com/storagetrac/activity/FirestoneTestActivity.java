package dpaw.com.storagetrac.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.QuantityUnit;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.data.UserFireStoreData;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;
import dpaw.com.storagetrac.database.Firestone.IOnGetRemoteStorageUnitHandler;
import dpaw.com.storagetrac.database.Firestone.IOnGetRemoteUserDataHandler;

public class FirestoneTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestone_test);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("test1@hello.com", "ozma123")
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i("Blah", "Successfully Logged in");
                        fireStoreTest();
                    } else {
                        Log.e("Blah", "Failed to login!");
                    }
                }
            });

    }

    private void fireStoreTest() {

        FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        StorageUnit su = new StorageUnit("Ozma storage test", 0);

        Item carrotItem = new Item("Carrot",0, 999, QuantityUnit.KILOGRAMS);
        Item waterItem = new Item("Water", 0, 100, QuantityUnit.LITRES);
        Item potatoItem = new Item("Potato", 0, 30, QuantityUnit.KILOGRAMS);

        su.add(carrotItem);
        su.add(waterItem);
        su.add(potatoItem);

//        db.addStorageUnit(su);

        db.getRemoteStorageUnit("VUhqeDM9700YBVD0c4TO", new IOnGetRemoteStorageUnitHandler() {
            @Override
            public void OnGetRemoteStorageUnit(StorageUnit su) {

            }
        });

//        db.getRemoteUserData("test1@hello.com", new IOnGetRemoteUserDataHandler() {
//            @Override
//            public void onGetRemoteUserData(UserFireStoreData data) {
//
//            }
//        });
    }
}
