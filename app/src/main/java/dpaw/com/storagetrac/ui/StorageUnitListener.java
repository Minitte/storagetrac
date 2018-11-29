package dpaw.com.storagetrac.ui;

/**
 * Interface for storage unit listeners.
 * Handles communication between storage unit and activity.
 */
public interface StorageUnitListener {

    /**
     * Called when the user selects an item within a storage unit.
     * @param index the selected item's index
     */
    void selectItem(int index);
}
