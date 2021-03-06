package dpaw.com.storagetrac.ui;

import dpaw.com.storagetrac.data.StorageUnit;

/**
 * Interface for storage unit listeners.
 * Handles communication between storage unit list and activity.
 */
public interface StorageUnitListListener {

    /**
     * Called when the user selects a storage unit.
     * @param index the selected storage unit's index
     */
    void selectStorageUnit(int index);

    /**
     * Called when share requires a login
     */
    void onRequireLogin();

    /**
     * Called when share open request
     * @param index the selected storage unit's index
     */
    void onOpenShareList(int index);
}
