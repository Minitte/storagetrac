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
import dpaw.com.storagetrac.ui.StorageUnitAdapter;
import dpaw.com.storagetrac.ui.StorageUnitListAdapter;

/**
 * Activity for displaying items inside a storage unit.
 */
public class StorageUnitActivity extends AppCompatActivity {

    /**
     * The currently opened storage unit.
     */
    private StorageUnit _storageUnit;

    /**
     * Storage unit adapter for the recycler view.
     */
    private StorageUnitAdapter _storageUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_unit);

        // Retrieve storage unit from the intent
        Intent intent = getIntent();
        _storageUnit = (StorageUnit)intent.getSerializableExtra("unit");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        // For testing purposes
        _storageUnit.add(new Item("Apple", R.drawable.ic_apple, 3, QuantityUnit.UNIT, date));
        _storageUnit.add(new Item("Orange", R.drawable.ic_orange, 5, QuantityUnit.UNIT, date));
        _storageUnit.add(new Item("Tomato", R.drawable.ic_tomato, 10, QuantityUnit.UNIT, date));
        _storageUnit.add(new Item("Cabbage", R.drawable.ic_cabbage, 100, QuantityUnit.GRAMS, date));
        _storageUnit.add(new Item("Milk", R.drawable.ic_milk, 1, QuantityUnit.LITRES, date));

        initRecyclerView();
        initButtons();
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.itemView);
        _storageUnitAdapter = new StorageUnitAdapter(_storageUnit);
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
    }

    /**
     * Currently used to get data back from other activities.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                // Add the new item
                Item newItem = (Item)data.getSerializableExtra("item");
                addItemToStorage(newItem);
            }
        }
    }

}