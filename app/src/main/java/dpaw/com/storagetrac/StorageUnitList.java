package dpaw.com.storagetrac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import dpaw.com.storagetrac.database.ContainerAdapter;

public class StorageUnitList extends AppCompatActivity {

    /**
     * Flag for edit state.
     */
    public static boolean editing;

    /***
     * List of container names.
     */
    private ArrayList<String> _containerNames;

    /**
     * Storage unit adapter for the recycler view.
     */
    private ContainerAdapter _containerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_unit_list);
        _containerNames = new ArrayList<>();

        // For testing purposes
        _containerNames.add("Container 1");
        _containerNames.add("Container 2");
        _containerNames.add("Container 3");
        _containerNames.add("Container 4");
        _containerNames.add("Container 5");
        _containerNames.add("Container 6");
        _containerNames.add("Container 7");
        _containerNames.add("Container 8");
        _containerNames.add("Container 9");
        _containerNames.add("Container 10");
        _containerNames.add("Container 11");

        // Initialize the view
        initRecyclerView();
        initButtons();
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView containerView = findViewById(R.id.containerView);
        _containerAdapter = new ContainerAdapter(_containerNames);
        containerView.setAdapter(_containerAdapter);
        containerView.setLayoutManager(new LinearLayoutManager(this));
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

        ImageButton editButton = findViewById(R.id.containerEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        ImageButton editDoneButton = findViewById(R.id.containerEditDone);
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

        _containerAdapter.notifyDataSetChanged(); // Tell the adapter to update

        ImageButton editButton = findViewById(R.id.containerEdit);
        ImageButton editDoneButton = findViewById(R.id.containerEditDone);

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
