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
import dpaw.com.storagetrac.ui.ItemDialogListener;

public class CreateItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ItemDialogListener {

    /**
     * Name of the item.
     */
    private String _itemName;

    /**
     * Icon of the item.
     */
    private int _itemIcon;

    /**
     * Quantity of the item.
     */
    private double _quantity;

    /**
     * The type of unit for the item quantity.
     */
    private QuantityUnit _unit;

    /**
     * Expiration date of the item.
     */
    private Date _expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        initButtons();
    }

    /**
     * Adds on click listeners to the buttons.
     */
    private void initButtons() {
        ImageButton doneButton = findViewById(R.id.done);
        ImageButton cancelButton = findViewById(R.id.cancel);
        ImageButton pickDate = findViewById(R.id.pickDate);
        ImageButton pickIcon = findViewById(R.id.itemIcon);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // Create a new item
                    Item item = new Item(_itemName, _itemIcon, _quantity, _unit, _expiryDate);

                    // Pack the new item into an intent
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), StorageUnitActivity.class);
                    bundle.putSerializable("item", item);
                    intent.putExtras(bundle);

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
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });
    }

    /**
     * Validates the input text fields.
     * @return true if input is valid, otherwise false
     */
    private boolean validateInput() {
        EditText nameView = findViewById(R.id.name);
        ImageButton iconView = findViewById(R.id.itemIcon);
        EditText quantityView = findViewById(R.id.quantity);
        EditText unitView = findViewById(R.id.unit);
        TextView dateView = findViewById(R.id.date);

        // Make sure all required input is not empty
        if (nameView.getText().length() > 0 && quantityView.getText().length() > 0
                && unitView.getText().length() > 0 && dateView.getText().length() > 0
                && iconView.getDrawable() != null) {
            _itemName = nameView.getText().toString();
            _itemIcon = getApplicationContext().getResources().getIdentifier(iconView.getTag().toString(), "drawable", getApplicationContext().getPackageName());
            _quantity = Integer.parseInt(quantityView.getText().toString());
            _unit = QuantityUnit.UNIT; // TODO

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // Update calendar instance
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        // Set expiry date to calendar's date
        _expiryDate = calendar.getTime();
        TextView expiryDateView = findViewById(R.id.date);
        expiryDateView.setText(_expiryDate.toString());
    }

    @Override
    public void selectImage(Drawable image, Object tag) {
        // Update the image icon to the user selection
        ImageButton iconView = findViewById(R.id.itemIcon);
        iconView.setImageDrawable(image);
        iconView.setTag(tag);
    }
}
