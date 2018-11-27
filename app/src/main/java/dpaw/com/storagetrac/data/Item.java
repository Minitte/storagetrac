package dpaw.com.storagetrac.data;

import java.util.Date;

/**
 * A class that represents an item that is stored in a storage unit
 */
public class Item {

    /**
     * id of the item
     */
    private int _id;

    /**
     * Name of the item
     */
    private String _name;

    /**
     * Quantity of the item
     */
    private double _quantity;

    /**
     * Unit associated with this item
     */
    private QuantityUnit _unit;

    /**
     * Item's expiry date
     */
    private Date _expiryDate;

    /**
     * Constructor for an item object
     * The unit will be set to default unit.
     * @param name name of the item
     * @param quantity qty of the item
     */
    public Item(String name, double quantity) {
        this._name = name;
        this._quantity = quantity;
        this._unit = QuantityUnit.UNIT;

        _id = (name + _expiryDate.toString()).hashCode();
    }

    /**
     * Constructor for an item object
     * @param name name of the item
     * @param quantity qty of the item
     * @param unit the unit of the item
     */
    public Item(String name, double quantity, QuantityUnit unit) {
        this._name = name;
        this._quantity = quantity;
        this._unit = unit;

        _id = (name + _expiryDate.toString()).hashCode();
    }

    /**
     /**
     * Constructor for an item object
     * @param name name of the item
     * @param quantity qty of the item
     * @param unit the unit of the item
     * @param expiryDate the expiry date of this item
     */
    public Item(String name, double quantity, QuantityUnit unit, Date expiryDate) {
        this._name = name;
        this._quantity = quantity;
        this._unit = unit;
        this._expiryDate = expiryDate;

        _id = (name + _expiryDate.toString()).hashCode();
    }

    /**
     * Adds to the item's quantity
     * @param amt the amount to add to the item
     */
    public void AddQuantity(double amt) {
        _quantity += amt;
    }

    /**
     * Subtracts from the item's quantity
     * @param amt the amount to subtract from the item
     */
    public void SubtractQuantity(double amt) {
        _quantity -= amt;
    }

    /**
     * Override equals method.
     * Checks if the object is an item, if the item's name and expiry date matches.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Item)) {
            return false;
        }

        Item i = (Item)obj;

        return _name.equalsIgnoreCase(i._name) && _expiryDate.equals(i._expiryDate);
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
     * Sets the _id
     *
     * @param _id set _id to this value
     */
    public void set_id(int _id) {
        this._id = _id;
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

    /**
     * Gets the value of _quantity
     *
     * @return a double
     */
    public double get_quantity() {
        return _quantity;
    }

    /**
     * Sets the _quantity
     *
     * @param _quantity set _quantity to this value
     */
    public void set_quantity(double _quantity) {
        this._quantity = _quantity;
    }

    /**
     * Gets the value of _unit
     *
     * @return a dpaw.com.storagetrac.data.QuantityUnit
     */
    public QuantityUnit get_unit() {
        return _unit;
    }

    /**
     * Sets the _unit
     *
     * @param _unit set _unit to this value
     */
    public void set_unit(QuantityUnit _unit) {
        this._unit = _unit;
    }

    /**
     * Gets the value of _expiryDate
     *
     * @return a java.util.Date
     */
    public Date get_expiryDate() {
        return _expiryDate;
    }

    /**
     * Sets the _expiryDate
     *
     * @param _expiryDate set _expiryDate to this value
     */
    public void set_expiryDate(Date _expiryDate) {
        this._expiryDate = _expiryDate;
    }
}
