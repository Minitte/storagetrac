package dpaw.com.storagetrac.data;

import java.io.Serializable;

public enum QuantityUnit implements Serializable {

    UNIT("units"),

    // WEIGHT
    TON("t"),
    POUNDS("lbs"),

    TONNE("t"),
    KILOGRAMS("kg"),
    GRAMS("g"),

    // VOLUME
    GALLON("gal"),
    QUART("qt"),
    CUPS("c"),
    LITRES("L"),
    MILLILITRES("mL");

    /**
     * Abbreviation of the unit
     */
    public final String abbreviation;

    /**
     * Constructor for a unit
     * @param abbreviation
     */
    private QuantityUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
