package dpaw.com.storagetrac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.QuantityUnit;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;

public class FirestoneTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestone_test);

        FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        StorageUnit su = new StorageUnit("Ozma storage test");

        Item carrotItem = new Item("Carrot", 30, QuantityUnit.KILOGRAMS);
        Item waterItem = new Item("Water", 100, QuantityUnit.LITRES);
        Item carrotItem2 = new Item("Carrot", 5, QuantityUnit.KILOGRAMS);
        Item carrotJuice = new Item("Carrot", 10000, QuantityUnit.LITRES);

        su.add(carrotItem);
        su.add(waterItem);
        su.add(carrotItem2);
        su.add(carrotJuice);

        db.addStorageUnit(su);
    }
}
