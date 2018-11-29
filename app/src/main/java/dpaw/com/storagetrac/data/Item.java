package dpaw.com.storagetrac.data;

import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents an item that is stored in a storage unit
 */
public class Item implements Serializable {

    /**
     * ID from Firestone
     */
    private String _fireStoneID;

    /**
     * Icon of the item.
     */
    private int _iconId;

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
     * Default constructor.
     */
    public Item() {}

    /**
     * Constructor for an item object
     * The unit will be set to default unit.
     * @param name name of the item
     * @param iconId id of the item icon
     * @param quantity qty of the item
     */
    public Item(String name, int iconId, double quantity) {
        this._name = name;
        this._iconId = iconId;
        this._quantity = quantity;
        this._unit = QuantityUnit.UNIT;
    }

    /**
     * Constructor for an item object
     * @param name name of the item
     * @param iconId id of the item icon
     * @param quantity qty of the item
     * @param unit the unit of the item
     */
    public Item(String name, int iconId, double quantity, QuantityUnit unit) {
        this._name = name;
        this._iconId = iconId;
        this._quantity = quantity;
        this._unit = unit;
    }

    /**
     /**
     * Constructor for an item object
     * @param name name of the item
     * @param iconId id of the item icon
     * @param quantity qty of the item
     * @param unit the unit of the item
     * @param expiryDate the expiry date of this item
     */
    public Item(String name, int iconId, double quantity, QuantityUnit unit, Date expiryDate) {
        this._name = name;
        this._iconId = iconId;
        this._quantity = quantity;
        this._unit = unit;
        this._expiryDate = expiryDate;
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

        if (i._expiryDate != null || _expiryDate != null) {

            if (_expiryDate == null || i._expiryDate == null) {
                return false;
            }

            return _name == i._name && _unit == i._unit && _expiryDate == i._expiryDate;

        } else {
            // compare name and unit
            return _name == i._name && _unit == i._unit;
        }
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

    /**
     * Gets the value of _iconId
     * @return the value of _iconId
     */
    public int get_iconId() {
        return _iconId;
    }

    /**
     * Sets the _iconId
     * @param _iconId set _iconId to this value
     */
    public void set_iconId(int _iconId) {
        this._iconId = _iconId;
    }
}
