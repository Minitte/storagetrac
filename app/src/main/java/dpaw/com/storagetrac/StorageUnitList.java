package dpaw.com.storagetrac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.database.StorageUnitAdapter;

public class StorageUnitList extends AppCompatActivity {

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
        for (int i = 0; i < 20; i++) {
            _storageUnits.add(new StorageUnit("Storage Unit " + i));
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
        _storageUnitAdapter = new StorageUnitAdapter(_storageUnits);
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

        ImageButton editDoneButton = findViewById(R.id.storageEditDone);
        editDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
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
        ImageButton editDoneButton = findViewById(R.id.storageEditDone);

        // Toggle visibility of edit buttons
        if (editing) {
            editButton.setVisibility(View.INVISIBLE);
            editDoneButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.VISIBLE);
            editDoneButton.setVisibility(View.INVISIBLE);
        }
    }

}
