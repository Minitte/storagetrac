package dpaw.com.storagetrac.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import java.util.ArrayList;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.data.QuantityUnit;

/**
 * Class that handles the creation of dialog windows when making new storage units.
 */
public class QuantityDialogFragment extends DialogFragment {

    /**
     * Listener that handles communication between this dialog and the create item activity.
     */
    private QuantityDialogListener _quantityDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _quantityDialogListener = (QuantityDialogListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // List of possible inputs
        final String[] units = {
                QuantityUnit.UNIT.abbreviation,
                QuantityUnit.TON.abbreviation,
                QuantityUnit.POUNDS.abbreviation,
                QuantityUnit.TONNE.abbreviation,
                QuantityUnit.KILOGRAMS.abbreviation,
                QuantityUnit.GRAMS.abbreviation,
                QuantityUnit.GALLON.abbreviation,
                QuantityUnit.QUART.abbreviation,
                QuantityUnit.CUPS.abbreviation,
                QuantityUnit.LITRES.abbreviation,
                QuantityUnit.MILLILITRES.abbreviation};

        // Set the on click listener
        builder.setItems(units, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Convert string to quantity unit
                QuantityUnit selected = null;
                switch (which) {
                    case 0: selected = QuantityUnit.UNIT; break;
                    case 1: selected = QuantityUnit.TON; break;
                    case 2: selected = QuantityUnit.POUNDS; break;
                    case 3: selected = QuantityUnit.TONNE; break;
                    case 4: selected = QuantityUnit.KILOGRAMS; break;
                    case 5: selected = QuantityUnit.GRAMS; break;
                    case 6: selected = QuantityUnit.GALLON; break;
                    case 7: selected = QuantityUnit.QUART; break;
                    case 8: selected = QuantityUnit.CUPS; break;
                    case 9: selected = QuantityUnit.LITRES; break;
                    case 10: selected = QuantityUnit.MILLILITRES; break;
                }

                _quantityDialogListener.selectQuantityUnit(selected); // Send it to the item activity
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
