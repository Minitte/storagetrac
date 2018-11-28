package dpaw.com.storagetrac.ui;

import dpaw.com.storagetrac.data.StorageUnit;

/**
 * Interface for storage unit listeners.
 * Handles communication between storage unit list and activity.
 */
public interface StorageUnitListAdapterListener {

    /**
     * Called when the user selects a storage unit.
     * @param index the selected storage unit's index
     */
    void selectStorageUnit(int index);
}
