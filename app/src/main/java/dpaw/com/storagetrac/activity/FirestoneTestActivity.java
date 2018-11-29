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

        StorageUnit su = new StorageUnit("Ozma storage test", 0);

        su.set_fireStoneID("07a8tXtzZpzfQRUIBQjw");

        Item carrotItem = new Item("Carrot",0, 999, QuantityUnit.KILOGRAMS);
        Item waterItem = new Item("Water", 0, 100, QuantityUnit.LITRES);
        Item potatoItem = new Item("Potato", 0, 30, QuantityUnit.KILOGRAMS);

        su.add(carrotItem);
        su.add(waterItem);
        su.add(potatoItem);

        db.updateStorageUnit(su);
    }
}
