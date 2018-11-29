package dpaw.com.storagetrac.ui;

import android.graphics.drawable.Drawable;

/**
 * Interface for storage dialog listeners.
 * Handles communication between dialog and activity.
 */
public interface StorageUnitDialogListener {
    /**
     * Called when the user selects an image in the dialog window.
     * @param image the image selected
     */
    void selectImage(Drawable image, Object tag);
}
