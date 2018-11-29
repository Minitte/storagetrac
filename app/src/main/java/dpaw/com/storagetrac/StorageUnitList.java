package dpaw.com.storagetrac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dpaw.com.storagetrac.activity.LoginActivity;
import dpaw.com.storagetrac.activity.ShareListActivity;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.data.UserFireStoreData;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;
import dpaw.com.storagetrac.database.Firestone.IStorageUnitResultHandler;
import dpaw.com.storagetrac.database.Firestone.IUserDataResultHandler;
import dpaw.com.storagetrac.database.LocalStorageDatabaseReader;
import dpaw.com.storagetrac.database.LocalStorageDatabaseWriter;
import dpaw.com.storagetrac.database.StorageUnitDatabase;
import dpaw.com.storagetrac.ui.StorageUnitListAdapter;
import dpaw.com.storagetrac.ui.StorageUnitListListener;

/**
 * Activity for displaying a list of storage units.
 */
public class StorageUnitList extends AppCompatActivity implements StorageUnitListListener {

    public static final String DATABASE_NAME = "storage_units";

    /**
     * Flag for edit state.
     */
    public static boolean editing;

    /**
     * Storage unit list adapter for the recycler view.
     */
    private StorageUnitListAdapter _storageUnitListAdapter;

    /**
     * The local storage unit database.
     */
    public static StorageUnitDatabase _storageUnitDatabase;

    /**
     * Path of the database.
     */
    private static String _databasePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_unit_list);
        _databasePath = getApplicationInfo().dataDir + "/" + DATABASE_NAME;

        // Try to read from local database if it exists
        LocalStorageDatabaseReader databaseReader = new LocalStorageDatabaseReader(_databasePath);
        _storageUnitDatabase = databaseReader.Read();

        if (_storageUnitDatabase == null) {
            // Create a new database and write it
            _storageUnitDatabase = new StorageUnitDatabase();

            LocalStorageDatabaseWriter databaseWriter = new LocalStorageDatabaseWriter(_databasePath);
            databaseWriter.write(_storageUnitDatabase);
        }


        // Initialize the view
        initRecyclerView();
        initButtons();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            checkRemoteStorages();
            getMissingRemoteStorageUnits();
        } else {
            Log.i("SU_Sync_start", "Skipping sync because not logged in.");
        }
    }

    /**
     * Checks remote storages for lost of access
     */
    private void checkRemoteStorages() {
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        final List<StorageUnit> remote = _storageUnitDatabase.getAllRemoteStorages();

        Log.i("SU_Sync_start", "checking " + remote.size() + " storage units. ALL:" + _storageUnitDatabase.get_storageUnits().size());

        if (remote.size() == 0) {
            return;
        }

        final FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        db.getRemoteUserData(email, new IUserDataResultHandler() {
            @Override
            public void onUserDataResult(UserFireStoreData data) {
                if (data == null) {
                    return;
                }

                final UserFireStoreData userData = data;

                for (final StorageUnit su : remote) {
                    // no access to this storage unit now
                    if (!data.get_ownedStorage().contains(su.get_fireStoneID()) && !data.get_borrowedStorage().contains(su.get_fireStoneID())) {
                        _storageUnitDatabase.remove(su);
                        Log.i("SU_Sync_start", "Removed a storage unit (" + su.get_name() + ") because no ownership..");
                    } else {
                        db.getRemoteStorageUnit(su.get_fireStoneID(), new IStorageUnitResultHandler() {
                            @Override
                            public void onStorageUnitResult(StorageUnit remoteSU) {
                                // deleted by owner
                                if (su == null) {
                                    _storageUnitDatabase.remove(su);

                                    userData.get_borrowedStorage().remove(su.get_fireStoneID());
                                    userData.get_ownedStorage().remove(su.get_fireStoneID());

                                    return;
                                }

                                _storageUnitDatabase.remove(su);
                                _storageUnitDatabase.add(remoteSU);
                            }
                        });
                        Log.i("SU_Sync_start", "Updated a storage unit with remote storage unit");
                    }
                }
            }
        });
    }


    private void getMissingRemoteStorageUnits() {
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        final FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        db.getRemoteUserData(email, new IUserDataResultHandler() {
            @Override
            public void onUserDataResult(UserFireStoreData data) {
                if (data == null) {
                    return;
                }

                List<String> remoteSUID = data.get_borrowedStorage();
                remoteSUID.addAll(data.get_ownedStorage());

                for (String suID : remoteSUID) {
                    boolean found = false;

                    for (StorageUnit su : _storageUnitDatabase.get_storageUnits()) {
                        if (suID.equals(su.get_fireStoneID())) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        continue;
                    }

                    Log.i("Start_Fetch_SU", "Added a remote borrowed Storage Unit.");

                    // storage unit not found
                    db.getRemoteStorageUnit(suID, new IStorageUnitResultHandler() {
                        @Override
                        public void onStorageUnitResult(StorageUnit su) {
                            _storageUnitDatabase.add(su);
                            _storageUnitListAdapter.notifyDataSetChanged();
                            saveLocalDatabase();
                        }
                    });
                }
            }
        });
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.storageView);
        _storageUnitListAdapter = new StorageUnitListAdapter((ArrayList<StorageUnit>)_storageUnitDatabase.get_storageUnits(), this);
        recyclerView.setAdapter(_storageUnitListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Adds onClick listeners to the buttons.
     */
    private void initButtons() {
        ImageButton optionsButton = findViewById(R.id.options);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Doesn't do anything yet", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton editButton = findViewById(R.id.storageEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        ImageButton addStorageButton = findViewById(R.id.storageUnitCreate);
        addStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewStorageUnit();
            }
        });

        // If number of storage units is empty, show add button and hide edit button
        if (_storageUnitDatabase.get_storageUnits().size() == 0) {
            addStorageButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Toggles editing on or off.
     */
    private void toggleEditMode() {
        editing = !editing; // Toggle editing

        // Post a message to the screen
        String displayText = editing ? "Toggled editing on" : "Toggled editing off";
        Toast.makeText(getApplicationContext(), displayText, Toast.LENGTH_SHORT).show();

        _storageUnitListAdapter.notifyDataSetChanged(); // Tell the adapter to update

        ImageButton editButton = findViewById(R.id.storageEdit);
        ImageButton addStorageButton = findViewById(R.id.storageUnitCreate);

        // Toggle visibilities and images
        if (editing) {
            editButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
            addStorageButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setImageDrawable(getDrawable(R.drawable.ic_edit_black_24dp));
            addStorageButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Saves the local database.
     */
    public static void saveLocalDatabase() {
        LocalStorageDatabaseWriter databaseWriter = new LocalStorageDatabaseWriter(_databasePath);
        databaseWriter.write(_storageUnitDatabase);
    }

    /**
     * Starts the create storage unit activity.
     */
    private void createNewStorageUnit() {
        Intent intent = new Intent(this, CreateStorageUnit.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Adds a new storage unit to the list of storage units.
     * @param storageUnit the storage unit to add
     */
    private void addStorageUnit(StorageUnit storageUnit) {
        _storageUnitDatabase.get_storageUnits().add(storageUnit);
        _storageUnitListAdapter.notifyDataSetChanged();
    }

    /**
     * Called when the user selects a storage unit.
     * @param index the selected storage unit's index
     */
    @Override
    public void selectStorageUnit(int index) {
        // Pack the selected storage unit into an intent
        Intent intent = new Intent(this, StorageUnitActivity.class);
        intent.putExtra("index", index);

        // Pass it to the storage unit activity
        startActivity(intent);
    }

    /**
     * Currently used to get data back from other activities.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                // Add the new storage unit
                StorageUnit newStorageUnit = (StorageUnit)data.getSerializableExtra("unit");
                addStorageUnit(newStorageUnit);

                saveLocalDatabase();
            }
        }
    }

    @Override
    public void onRequireLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onOpenShareList(int index) {
        Intent i = new Intent(this, ShareListActivity.class);
//        i.putExtra("storage unit", _storageUnitDatabase.get_storageUnits().get(index));
        ShareListActivity.storageUnit = _storageUnitDatabase.get_storageUnits().get(index);
        startActivity(i);
    }
}
