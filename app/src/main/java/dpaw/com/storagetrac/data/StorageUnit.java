package dpaw.com.storagetrac.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A storage unit containing a list of items
 */
public class StorageUnit implements Serializable {

    /**
     * ID for the storage unit
     */
    private int _id;

    /**
     * firestone id of the storage unit
     */
    private String _fireStoneID;

    /**
     * Name of the storage unit
     */
    private String _name;

    /**
     * Icon of the storage unit.
     */
    private int _iconId;

    /**
     * List of items in this storage unit
     */
    private List<Item> _items;

    /**
     * List of emails that have share access
     */
    private List<String> _sharedEmails;

    /**
     * Blank/empty constructor for firestore
     */
    public StorageUnit() {
    }

    /**
     * COnstructor of a storage unit.
     * Automaticly sets up the list
     * @param name name of the storage unit
     */
    public StorageUnit(String name, int iconId) {
        this._name = name;
        this._iconId = iconId;

        _items = new ArrayList<>();

        String hashString = Calendar.getInstance().getTime().toString() + name;
        _id = hashString.hashCode();

        _sharedEmails = new ArrayList<>();
    }

    /**
     * Adds to the storage unit
     * @param item the item to add
     */
    public void add(Item item) {
        Item match = findItemByID(item);

        if (match == null) {
            _items.add(item);
        } else {
            match.AddQuantity(item.get_quantity());
        }
    }

    /**
     * Attempts to remove the item from the list
     * @param item the item to remove
     * @return if the item removal was successful
     */
    public boolean remove(Item item) {
        Item match = findItemByID(item);

        if (match != null) {
            _items.remove(match);
            return true;
        }

        return false;
    }

    /**
     * Searches the list of a matching item by id
     * @param item item to search for
     * @return returns the first item that matches or a null if none found
     */
    public Item findItemByID(Item item) {
        // search for item
        for (Item cur : _items) {
            // compare by name and expiry
           if (cur.equals(item)) {
               return cur;
           }
        }

        return null;
    }

    /**
     * Searches the list of matching item by name
     * @param item item to search for
     * @return returns the first item that matches or a null if none found
     */
    public Item findItemByName(Item item) {
        // search for item
        for (Item cur : _items) {
            // compare by name
            if (cur.get_name().equalsIgnoreCase(item.get_name())) {
                return cur;
            }
        }

        return null;
    }

    /**
     * Removes a shared email
     * @param email
     */
    public void removeShared(String email) {
        for (int i = 0; i < _sharedEmails.size(); i++) {
            if (_sharedEmails.get(i).equals(email)) {
                _sharedEmails.remove(i);
                return;
            }
        }
    }

    /**
     * Override of equals.
     * Checks if the ids of two storage units match.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StorageUnit)) {
            return false;
        }

        StorageUnit su = (StorageUnit)obj;

        return _id == su._id;
    }

    /**
     * Gets the value of _id
     *
     * @return a int
     */
    public int get_id() {
        return _id;
    }

    /**
     * Gets the value of _fireStoneID
     *
     * @return a java.lang.String
     */
    public String get_fireStoneID() {
        return _fireStoneID;
    }

    /**
     * Sets the _fireStoneID
     *
     * @param _fireStoneID set _fireStoneID to this value
     */
    public void set_fireStoneID(String _fireStoneID) {
        this._fireStoneID = _fireStoneID;
    }

    /**
     * Gets the value of _name
     *
     * @return a java.lang.String
     */
    public String get_name() {
        return _name;
    }

    /**
     * Gets the value of _icon.
     * @return the value of _icon as a int
     */
    public int get_iconId() {
        return _iconId;
    }

    /**
     * Sets the _name
     *
     * @param _name set _name to this value
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * Gets the value of _items
     *
     * @return a java.util.List<dpaw.com.storagetrac.data.Item>
     */
    public List<Item> get_items() {
        return _items;
    }
  
    /**
     * Sets the iconId.
     * @param iconId set _icon to this value
     */
    public void set_icon(int iconId) {
        this._iconId = iconId;
    }

    /**
     * Gets the value of _sharedEmails
     *
     * @return a java.util.List<java.lang.String>
     */
    public List<String> get_sharedEmails() {
        return _sharedEmails;
    }
}
