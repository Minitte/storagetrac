package dpaw.com.storagetrac;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.QuantityUnit;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.ui.DatePickerFragment;
import dpaw.com.storagetrac.ui.ItemDialogFragment;
import dpaw.com.storagetrac.ui.ItemDialogListener;
import dpaw.com.storagetrac.ui.QuantityDialogFragment;
import dpaw.com.storagetrac.ui.QuantityDialogListener;
import dpaw.com.storagetrac.ui.StorageUnitDialogFragment;

/**
 * Activity for creating or editing items.
 */
public class CreateItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ItemDialogListener, QuantityDialogListener {

    /**
     * Name of the item.
     */
    private Item _item;

    /**
     * Index of the item in its storage unit.
     */
    private int _index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        // Retrieve item from the intent
        Intent intent = getIntent();
        Item item = (Item)intent.getSerializableExtra("item");
        _index = intent.getIntExtra("index", -1);

        // Item not being null means the user is editing
        if (item != null) {
            _item = item;
            updateViews();
        } else {
            _item = new Item();
        }

        initButtons();
    }

    /**
     * Updates the view text fields with the item properties.
     */
    private void updateViews() {
        EditText nameView = findViewById(R.id.name);
        ImageButton iconView = findViewById(R.id.itemIcon);
        EditText quantityView = findViewById(R.id.quantity);
        TextView unitView = findViewById(R.id.unit);
        TextView dateView = findViewById(R.id.date);

        // Set view texts
        nameView.setText(_item.get_name());
        iconView.setImageResource(_item.get_iconId());
        quantityView.setText("" + (int)_item.get_quantity());

        // Set quantity unit if it exists
        if (_item.get_unit() != null) {
            unitView.setText(_item.get_unit().abbreviation);
        }


        // Set expiry date if it exists
        if (_item.get_expiryDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(_item.get_expiryDate());
            dateView.setText(calendar.getTime().toString());
        }

    }

    /**
     * Adds on click listeners to the buttons.
     */
    private void initButtons() {
        ImageButton doneButton = findViewById(R.id.done);
        ImageButton cancelButton = findViewById(R.id.cancel);
        ImageButton pickDate = findViewById(R.id.pickDate);
        ImageButton pickIcon = findViewById(R.id.itemIcon);
        ImageButton pickUnit = findViewById(R.id.pickUnit);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    EditText nameView = findViewById(R.id.name);
                    ImageButton iconView = findViewById(R.id.itemIcon);
                    EditText quantityView = findViewById(R.id.quantity);

                    // Update item properties
                    _item.set_name(nameView.getText().toString());
                    _item.set_quantity(Integer.parseInt(quantityView.getText().toString()));
                    if (iconView.getTag() != null) { // Image is optional
                        _item.set_iconId(getApplicationContext().getResources().getIdentifier
                                (iconView.getTag().toString(), "drawable", getApplicationContext().getPackageName()));
                    }

                    // Pack the item into an intent
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), StorageUnitActivity.class);
                    bundle.putSerializable("item", _item);
                    intent.putExtras(bundle);
                    intent.putExtra("index", _index);

                    // Pass it back to the storage unit activity
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid input.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to previous activity
            }
        });

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a date picker dialog
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        pickIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an icon picker dialog
                ItemDialogFragment icons = new ItemDialogFragment();
                icons.show(getFragmentManager(), "Choose an icon");
            }
        });

        pickUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a quantity type dialog
                QuantityDialogFragment quantity = new QuantityDialogFragment();
                quantity.show(getFragmentManager(), "Choose a quantity type");
            }
        });
    }

    /**
     * Validates the input text fields.
     * @return true if input is valid, otherwise false
     */
    private boolean validateInput() {
        // References to the views
        EditText nameView = findViewById(R.id.name);
        ImageButton iconView = findViewById(R.id.itemIcon);
        EditText quantityView = findViewById(R.id.quantity);
        TextView unitView = findViewById(R.id.unit);
        TextView dateView = findViewById(R.id.date);

        // Check if all required input is not empty
        if (nameView.getText().length() > 0 && quantityView.getText().length() > 0
                && unitView.getText().length() > 0 && dateView.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the expiry date of the item.
     * @param datePicker the date picker dialog
     * @param year the expiry year
     * @param month the expiry month
     * @param day the expiry day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // Create new calendar instance
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        // Set expiry date to calendar's date
        _item.set_expiryDate(calendar.getTime());
        TextView expiryDateView = findViewById(R.id.date);
        expiryDateView.setText(calendar.getTime().toString());
    }

    /**
     * Called when the user selects an icon in the icon picker dialog.
     * @param image the image selected
     * @param tag the tag of the image, used for referencing drawables
     */
    @Override
    public void selectImage(Drawable image, Object tag) {
        // Update the image icon to the user selection
        ImageButton iconView = findViewById(R.id.itemIcon);
        iconView.setImageDrawable(image);
        iconView.setTag(tag);
    }

    /**
     * Called when the user selects a quantity type in the quantity dialog.
     * @param unit the quantity unit selected
     */
    @Override
    public void selectQuantityUnit(QuantityUnit unit) {
        // Update the quantity unit to the user selection
        _item.set_unit(unit);
        TextView unitView = findViewById(R.id.unit);
        unitView.setText(unit.abbreviation);
    }
}
