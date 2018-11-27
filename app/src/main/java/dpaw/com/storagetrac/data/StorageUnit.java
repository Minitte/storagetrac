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
     * id of the storage unit
     */
    private int _id;

    /**
     * Name of the storage unit
     */
    private String _name;

    /**
     * List of items in this storage unit
     */
    private List<Item> _items;

    /**
     * COnstructor of a storage unit.
     * Automaticly sets up the list
     * @param name name of the storage unit
     */
    public StorageUnit(String name) {
        this._name = name;

        _items = new ArrayList<>();

        String hashString = Calendar.getInstance().getTime().toString() + name;
        _id = hashString.hashCode();
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
     * Attempts to remove the item from the list
     * @param id id of the item to remove
     * @return if the item removal was successful
     */
    public boolean remove(int id) {
        Item match = findItemByID(id);

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
     * Searches the list of a matching item by id
     * @param id id of the storage unit to remove
     * @return returns the first item that matches or a null if none found
     */
    public Item findItemByID(int id) {
        // search for item
        for (Item cur : _items) {
            // compare by name and expiry
            if (cur.get_id() == id) {
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
     * Gets the value of _name
     *
     * @return a java.lang.String
     */
    public String get_name() {
        return _name;
    }

    /**
     * Sets the _name
     *
     * @param _name set _name to this value
     */
    public void set_name(String _name) {
        this._name = _name;
    }
}
