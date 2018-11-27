package dpaw.com.storagetrac.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A storage unit containing a list of items
 */
public class StorageUnit {

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
    }

    /**
     * Adds to the storage unit
     * @param item the item to add
     */
    public void add(Item item) {
        Item match = findItemByNameAndExpiry(item);

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
        Item match = findItemByNameAndExpiry(item);

        if (match != null) {
            _items.remove(match);
            return true;
        }

        return false;
    }

    /**
     * Searches the list of a matching item by name and expiry date
     * @param item item to search for
     * @return returns the item that matches or a null if none found
     */
    public Item findItemByNameAndExpiry(Item item) {
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
     * @return returns the item that matches or a null if none found
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
