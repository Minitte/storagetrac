package dpaw.com.storagetrac.data;

import java.util.Date;

/**
 * A class that represents an item that is stored in a storage unit
 */
public class Item {

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
