package dpaw.com.storagetrac;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.ui.StorageUnitDialogListener;
import dpaw.com.storagetrac.ui.StorageUnitDialogFragment;

/**
 * Activity that handles the creation of new storage units.
 */
public class CreateStorageUnit extends AppCompatActivity implements StorageUnitDialogListener {

    /**
     * The name of the storage unit to create.
     */
    private TextView _storageName;

    /**
     * The icon of the storage unit to create.
     */
    private ImageButton _storageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_storage_unit);

        _storageIcon = findViewById(R.id.storageUnitIcon);
        _storageName = findViewById(R.id.storageUnitName);

        initButtons();
    }

    /**
     * Adds on click listeners to the buttons.
     */
    private void initButtons() {
        ImageButton doneButton = findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation
                if (_storageName.getText().length() > 0 && _storageIcon.getDrawable() != null) {
                    // Create a new storage unit
                    int drawableId = getApplicationContext().getResources().getIdentifier(_storageIcon.getTag().toString(), "drawable", getApplicationContext().getPackageName());
                    StorageUnit newUnit = new StorageUnit(_storageName.getText().toString(), drawableId);

                    // Pack the new storage unit into an intent
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), StorageUnitList.class);
                    bundle.putSerializable("unit", newUnit);
                    intent.putExtras(bundle);

                    // Pass it back to the main activity
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid parameters.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to storage unit page
            }
        });

        ImageButton storageIcon = findViewById(R.id.storageUnitIcon);
        storageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageUnitDialogFragment icons = new StorageUnitDialogFragment();
                icons.show(getFragmentManager(), "Choose an icon");
            }
        });
    }

    /**
     * Called when the user selects an icon in the icon picker dialog.
     * @param image the image selected
     * @param tag the tag of the image, used for referencing drawables
     */
    @Override
    public void selectImage(Drawable image, Object tag) {
        _storageIcon.setImageDrawable(image);
        _storageIcon.setTag(tag);
    }
}
