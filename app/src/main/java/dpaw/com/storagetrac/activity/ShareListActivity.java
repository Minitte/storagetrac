package dpaw.com.storagetrac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.StorageUnitList;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.data.UserFireStoreData;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;
import dpaw.com.storagetrac.database.Firestone.IStorageUnitResultHandler;
import dpaw.com.storagetrac.database.Firestone.IUserDataResultHandler;
import dpaw.com.storagetrac.ui.EmailListAdapter;

public class ShareListActivity extends AppCompatActivity {

    /**
     * Target storage unit
     */
    public static StorageUnit storageUnit;

    /**
     * list adapter for shared emails
     */
    private EmailListAdapter adapter;

    /**
     * Field for emails
     */
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        emailField = findViewById(R.id.emailField);

//        storageUnit = (StorageUnit)savedInstanceState.getSerializable("storage unit");
//        storageUnit = (StorageUnit)getIntent().getSerializableExtra("storage unit");
        initRecyclerView();
    }

    /**
     * Initalizes the recycler view for emails
     */
    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.emailList);

        adapter = new EmailListAdapter(storageUnit);

        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Adds email from list
     * @param view
     */
    public void addEmailFromField(View view) {

        final String email = emailField.getText().toString();

        // fast referance to list
        final List<String> shared = storageUnit.get_sharedEmails();

        // database object
        final FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        final boolean firstTime = storageUnit.get_fireStoneID() == null;

        // get user data
        db.getRemoteUserData(email, new IUserDataResultHandler() {
            @Override
            public void onUserDataResult(UserFireStoreData data) {
                if (data == null) {
                    data = new UserFireStoreData();
                }

                final UserFireStoreData userData = data;

                // add to shared list
                storageUnit.get_sharedEmails().add(email);
                db.setStorageUnit(storageUnit, new IStorageUnitResultHandler() {
                    @Override
                    public void onStorageUnitResult(StorageUnit su) {
                        // set borrowed
                        userData.get_borrowedStorage().add(storageUnit.get_fireStoneID());
                        db.setRemoteUserData(email, userData, null);

                        // owner email
                        final String ownerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        // get owner user data
                        db.getRemoteUserData(ownerEmail, new IUserDataResultHandler() {
                            @Override
                            public void onUserDataResult(UserFireStoreData data) {
                                if (data == null) {
                                    data = new UserFireStoreData();
                                }

                                // add storage unit id to ownership list
                                data.get_ownedStorage().add(storageUnit.get_fireStoneID());

                                // set to database
                                db.setRemoteUserData(ownerEmail, data, null);

                                // clear text
                                emailField.setText("");

                                StorageUnitList.saveLocalDatabase();

                                adapter.notifyDataSetChanged();


                            }
                        });
                    }
                });
            }
        });
    }
}
