package dpaw.com.storagetrac.data;

import java.util.ArrayList;
import java.util.List;

public class UserFireStoreData {

    private List<String> _borrowedStorage;

    private List<String> _ownedStorage;

    /**
     * constructor for a user firestore data
     */
    public UserFireStoreData() {
        _borrowedStorage = new ArrayList<>();
        _ownedStorage = new ArrayList<>();
    }

    /**
     * Gets the value of _borrowedStorage
     *
     * @return a java.util.List<java.lang.String>
     */
    public List<String> get_borrowedStorage() {
        return _borrowedStorage;
    }

    /**
     * Sets the _borrowedStorage
     *
     * @param _borrowedStorage set _borrowedStorage to this value
     */
    public void set_borrowedStorage(List<String> _borrowedStorage) {
        this._borrowedStorage = _borrowedStorage;
    }

    /**
     * Gets the value of _ownedStorage
     *
     * @return a java.util.List<java.lang.String>
     */
    public List<String> get_ownedStorage() {
        return _ownedStorage;
    }

    /**
     * Sets the _ownedStorage
     *
     * @param _ownedStorage set _ownedStorage to this value
     */
    public void set_ownedStorage(List<String> _ownedStorage) {
        this._ownedStorage = _ownedStorage;
    }
}
