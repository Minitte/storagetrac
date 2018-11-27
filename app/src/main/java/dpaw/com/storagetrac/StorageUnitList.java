package dpaw.com.storagetrac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dpaw.com.storagetrac.database.ContainerAdapter;

public class StorageUnitList extends AppCompatActivity {

    /***
     * List of container names.
     */
    private ArrayList<String> _containerNames;

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

        initRecyclerView();
    }

    /**
     * Initializes the recycler view.
     * Automatically updates as new list items are added.
     */
    private void initRecyclerView() {
        RecyclerView containerView = findViewById(R.id.containerView);
        ContainerAdapter adapter = new ContainerAdapter(_containerNames);
        containerView.setAdapter(adapter);
        containerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
