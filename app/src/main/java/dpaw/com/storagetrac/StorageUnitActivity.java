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

public class StorageUnitActivity extends AppCompatActivity {

    /**
     * Flag for edit state.
     */
    public static boolean editing;

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
        _storageUnit.add(new Item("Spaghetti", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("Apple", R.drawable.ic_launcher_foreground, 3, QuantityUnit.UNIT));
        _storageUnit.add(new Item("Juice", R.drawable.ic_launcher_foreground, 1000, QuantityUnit.MILLILITRES));
        _storageUnit.add(new Item("a", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("b", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("c", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("d", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("e", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("f", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("g", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));
        _storageUnit.add(new Item("h", R.drawable.ic_launcher_foreground, 3, QuantityUnit.GRAMS));

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
