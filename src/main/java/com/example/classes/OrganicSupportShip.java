package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an organic support ship that extends the Ship class.
 * Extends Ship and implements Serializable.
 */
public class OrganicSupportShip extends Ship implements Serializable {

    private Set<FoodType> foodTypes;

    /**
     * Private constructor to initialize an OrganicSupportShip object.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship.
     * @param solarFlareShieldStrength The solar flare shield strength of the ship.
     * @param foodTypes                The set of food types supported by the organic support ship.
     */
    private OrganicSupportShip(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, Set<FoodType> foodTypes) {
        super(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        this.foodTypes = foodTypes;
    }

    /**
     * Static factory method to create an OrganicSupportShip object.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship.
     * @param solarFlareShieldStrength The solar flare shield strength of the ship.
     * @param foodTypes                The set of food types supported by the organic support ship.
     * @return A new OrganicSupportShip object.
     * @throws RuntimeException If any of the arguments (name, maxCargoMassCapacity, shipType, solarFlareShieldStrength) is invalid.
     */
    public static OrganicSupportShip constructor(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, Set<FoodType> foodTypes) {
        validArgsCheck(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        return new OrganicSupportShip(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength, foodTypes);
    }

    /**
     * Retrieves the set of food types supported by the organic support ship.
     *
     * @return An unmodifiable set of food types.
     */
    public Set<FoodType> getFoodTypes() {
        return Collections.unmodifiableSet(foodTypes);
    }

    /**
     * Sets the set of food types supported by the organic support ship.
     *
     * @param foodTypes The set of food types to set.
     * @throws RuntimeException If foodTypes is null.
     */
    public void setFoodTypes(Set<FoodType> foodTypes) {
        if (foodTypes == null) {
            throw new RuntimeException("Food types can not be null");
        }
        this.foodTypes = foodTypes;
    }
}
