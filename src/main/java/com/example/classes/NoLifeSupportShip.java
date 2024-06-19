package com.example.classes;

import java.io.Serializable;

/**
 * Represents a spaceship that does not support life onboard, extending the base Ship class.
 * It includes additional attributes and methods specific to ships without life support systems.
 */
public class NoLifeSupportShip extends Ship implements Serializable {

    private String shipAIType;

    /**
     * Constructor for creating a NoLifeSupportShip object.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship (Shielded or NoProtection).
     * @param solarFlareShieldStrength The strength of the solar flare shield (null for NoProtection ships).
     * @param shipAIType               The type of artificial intelligence used on the ship.
     */
    private NoLifeSupportShip(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, String shipAIType) {
        super(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        this.shipAIType = shipAIType;
    }

    /**
     * Static method to construct a NoLifeSupportShip object, ensuring validity of arguments.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship (Shielded or NoProtection).
     * @param solarFlareShieldStrength The strength of the solar flare shield (null for NoProtection ships).
     * @param shipAIType               The type of artificial intelligence used on the ship.
     * @return A new instance of NoLifeSupportShip with the specified parameters.
     * @throws RuntimeException If any argument validation fails.
     */
    public static NoLifeSupportShip constructor(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, String shipAIType) {
        validArgsCheck(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        Util.validString(shipAIType);
        return new NoLifeSupportShip(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength, shipAIType);
    }

    /**
     * Retrieves the type of artificial intelligence used on the ship.
     *
     * @return The ship's artificial intelligence type.
     */
    public String getShipAIType() {
        return shipAIType;
    }

    /**
     * Sets the type of artificial intelligence used on the ship.
     *
     * @param shipAIType The ship's artificial intelligence type to set.
     */
    public void setShipAIType(String shipAIType) {
        Util.validString(shipAIType);
        this.shipAIType = shipAIType;
    }
}
