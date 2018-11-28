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

        // For testing purposes
        _storageUnit.add(new Item("Apple", R.drawable.ic_apple, 3, QuantityUnit.UNIT));
        _storageUnit.add(new Item("Orange", R.drawable.ic_orange, 5, QuantityUnit.UNIT));
        _storageUnit.add(new Item("Tomato", R.drawable.ic_tomato, 10, QuantityUnit.UNIT));
        _storageUnit.add(new Item("Cabbage", R.drawable.ic_cabbage, 100, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("Milk", R.drawable.ic_milk, 1, QuantityUnit.LITRES));

        initRecyclerView();
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

}
