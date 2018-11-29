package dpaw.com.storagetrac.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dpaw.com.storagetrac.data.StorageUnit;

public class StorageUnitDatabase implements Serializable {

    /**
     * List of storage units
     */
    private List<StorageUnit> _storageUnits;

    /**
     * Storage unit constructor.
     * Prepares the list
     */
    public StorageUnitDatabase() {
        _storageUnits = new ArrayList<>();
    }

    /**
     * Adds to the database
     * @param su storage unit to add
     */
    public void add(StorageUnit su) {
        _storageUnits.add(su);
    }

    /**
     * Attempts to remove a storage unit
     * @param su storage unit to remove
     * @return if the removal was successful
     */
    public boolean remove(StorageUnit su) {
        int indexToRemove = -1;

        for (int i = 0; i < _storageUnits.size(); i++) {
            if (_storageUnits.get(i).equals(su)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            return false;
        }

        _storageUnits.remove(indexToRemove);

        return true;
    }

    /**
     * Attempts to remove a storange unit
     * @param id id of the storage unit to remove
     * @return if the removal was successful
     */
    public boolean remove(int id) {
        int indexToRemove = -1;

        for (int i = 0; i < _storageUnits.size(); i++) {
            if (_storageUnits.get(i).get_id() == id) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            return false;
        }

        _storageUnits.remove(indexToRemove);

        return true;
    }

    /**
     * Gets the value of _storageUnits.
     * @return the value of _storageUnits as a list of StorageUnit
     */
    public List<StorageUnit> get_storageUnits() {
        return _storageUnits;
    }
}
