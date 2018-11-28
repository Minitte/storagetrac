package dpaw.com.storagetrac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.ui.StorageUnitAdapter;
import dpaw.com.storagetrac.ui.StorageUnitAdapterListener;

public class StorageUnitList extends AppCompatActivity implements StorageUnitAdapterListener {

    /**
     * Flag for edit state.
     */
    public static boolean editing;

    /***
     * List of storage units.
     */
    private ArrayList<StorageUnit> _storageUnits;

    /**
     * Storage unit adapter for the recycler view.
     */
    private StorageUnitAdapter _storageUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_unit_list);
        _storageUnits = new ArrayList<>();

        // For testing purposes
        for (int i = 1; i < 6; i++) {
            _storageUnits.add(new StorageUnit("Storage Unit " + i, R.drawable.ic_fridge));
        }

        // Initialize the view
        initRecyclerView();
        initButtons();
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.storageView);
        _storageUnitAdapter = new StorageUnitAdapter(_storageUnits, this);
        recyclerView.setAdapter(_storageUnitAdapter);
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
    }

    /**
     * Toggles editing on or off.
     */
    private void toggleEditMode() {
        editing = !editing; // Toggle editing

        // Post a message to the screen
        String displayText = editing ? "Toggled editing on" : "Toggled editing off";
        Toast.makeText(getApplicationContext(), displayText, Toast.LENGTH_SHORT).show();

        _storageUnitAdapter.notifyDataSetChanged(); // Tell the adapter to update

        ImageButton editButton = findViewById(R.id.storageEdit);
        ImageButton createNewButton = findViewById(R.id.storageUnitCreate);

        // Toggle visibilities and images
        if (editing) {
            editButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
            createNewButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setImageDrawable(getDrawable(R.drawable.ic_edit_black_24dp));
            createNewButton.setVisibility(View.INVISIBLE);
        }
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
        _storageUnits.add(storageUnit);
        _storageUnitAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectStorageUnit(int index) {
        Intent intent = new Intent(this, StorageUnitActivity.class);
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
            }
        }
    }



}
