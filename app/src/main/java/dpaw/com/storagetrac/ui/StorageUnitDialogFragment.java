package dpaw.com.storagetrac.ui;

import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import java.util.ArrayList;

import dpaw.com.storagetrac.R;

/**
 * Class that handles the creation of dialog windows when making new storage units.
 */
public class StorageUnitDialogFragment extends DialogFragment {

    /**
     * Listener that handles communication between this dialog and the activity.
     */
    private StorageUnitDialogListener _listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _listener = (StorageUnitDialogListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Initialize the view
        View view = inflater.inflate(R.layout.item_alert_dialog, null);
        builder.setView(view);
        initButtons(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Adds on click listeners to the buttons.
     * @param view the dialog view
     */
    private void initButtons(View view) {
        // Get list of all buttons on the dialog
        ArrayList<View> allButtons;
        allButtons = ((TableLayout) view.findViewById(R.id.table)).getTouchables();

        // Add on click listener to each button
        for (final View button : allButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _listener.selectImage(((ImageButton)button).getDrawable(), button.getTag().toString()); // Select this image
                    dismiss(); // Close the dialog window
                }
            });
        }
    }
}
