package dpaw.com.storagetrac.ui;

import android.graphics.drawable.Drawable;

import dpaw.com.storagetrac.data.QuantityUnit;

/**
 * Interface for storage dialog listeners.
 * Handles communication between dialog and activity.
 */
public interface QuantityDialogListener {
    /**
     * Called when the user selects an quantity unit in the dialog window.
     * @param unit the quantity unit selected
     */
    void selectQuantityUnit(QuantityUnit unit);
}
