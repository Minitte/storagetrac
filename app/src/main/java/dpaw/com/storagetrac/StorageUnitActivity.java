package dpaw.com.storagetrac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.QuantityUnit;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.database.LocalStorageDatabaseReader;
import dpaw.com.storagetrac.database.LocalStorageDatabaseWriter;
import dpaw.com.storagetrac.database.StorageUnitDatabase;
import dpaw.com.storagetrac.ui.StorageUnitAdapter;
import dpaw.com.storagetrac.ui.StorageUnitListAdapter;
import dpaw.com.storagetrac.ui.StorageUnitListener;

/**
 * Activity for displaying items inside a storage unit.
 */
public class StorageUnitActivity extends AppCompatActivity implements StorageUnitListener {

    /**
     * The currently opened storage unit.
     */
    private StorageUnit _storageUnit;

    /**
     * Storage unit adapter for the recycler view.
     */
    private StorageUnitAdapter _storageUnitAdapter;

    /**
     * The local storage unit database.
     */
    private StorageUnitDatabase _storageUnitDatabase;

    /**
     * Path of the database.
     */
    private String _databasePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_unit);
        _databasePath = getApplicationInfo().dataDir + "/" + StorageUnitList.DATABASE_NAME;

        // Get reference to local database
        LocalStorageDatabaseReader databaseReader = new LocalStorageDatabaseReader(_databasePath);
        _storageUnitDatabase = databaseReader.Read();

        // Retrieve storage unit index from the intent
        Intent intent = getIntent();
        int storageIndex = intent.getIntExtra("index", -1);
        _storageUnit = _storageUnitDatabase.get_storageUnits().get(storageIndex); // Get storage unit from database

        initRecyclerView();
        initButtons();
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.itemView);
        _storageUnitAdapter = new StorageUnitAdapter(_storageUnit, this);
        recyclerView.setAdapter(_storageUnitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Adds on click listeners to the buttons.
     */
    private void initButtons() {
        ImageButton createItem = findViewById(R.id.itemCreate);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewItem();
            }
        });
    }

    /**
     * Saves the local database.
     */
    private void saveLocalDatabase() {
        LocalStorageDatabaseWriter databaseWriter = new LocalStorageDatabaseWriter(_databasePath);
        databaseWriter.write(_storageUnitDatabase);
    }

    /**
     * Starts the create new item activity.
     */
    private void createNewItem() {
        Intent intent = new Intent(this, CreateItem.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Adds a new item to the storage unit.
     * @param item the item to add
     */
    private void addItemToStorage(Item item) {
        _storageUnit.add(item);
        _storageUnitAdapter.notifyDataSetChanged();
        saveLocalDatabase();
    }

    /**
     * Currently used to get data back from other activities.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Creating a new item
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                // Add the new item
                Item newItem = (Item)data.getSerializableExtra("item");
                addItemToStorage(newItem);
                saveLocalDatabase();
            }
        }

        // Editing an existing item
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                // Get information from the intent
                Item newItem = (Item)data.getSerializableExtra("item");
                int index = data.getIntExtra("index", -1);

                // Replace item at specified index
                _storageUnit.get_items().set(index, newItem);
                _storageUnitAdapter.notifyDataSetChanged();
                saveLocalDatabase();
            }
        }
    }

    /**
     * Called when the user presses on an item.
     * @param index the selected item's index
     */
    @Override
    public void selectItem(int index) {
        Intent intent = new Intent(this, CreateItem.class);

        // Pack item into the intent
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", _storageUnit.get_items().get(index));
        intent.putExtras(bundle);
        intent.putExtra("index", index);

        // Start the item edit activity
        startActivityForResult(intent, 2);
    }
}
