package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an organic crew member that extends the CrewMember class.
 * Extends CrewMember and implements Serializable.
 */
public class OrganicCrewMember extends CrewMember implements Serializable {

    private String name;
    private FoodType acceptableFoodType;

    /**
     * Private constructor to initialize an OrganicCrewMember object.
     *
     * @param name               The name of the organic crew member.
     * @param acceptableFoodType The acceptable food type for the crew member.
     */
    private OrganicCrewMember(String name, FoodType acceptableFoodType) {
        super();
        setName(name);
        setAcceptableFoodType(acceptableFoodType);
    }

    /**
     * Static factory method to create an OrganicCrewMember object.
     *
     * @param name               The name of the organic crew member.
     * @param acceptableFoodType The acceptable food type for the crew member.
     * @return A new OrganicCrewMember object.
     * @throws RuntimeException If name is null or empty.
     */
    public static OrganicCrewMember constructor(String name, FoodType acceptableFoodType) {
        Util.validString(name);
        return new OrganicCrewMember(name, acceptableFoodType);
    }

    /**
     * Overrides the canJoin method from CrewMember to check if the crew member
     * can join a specific ship based on acceptable food types.
     *
     * @param ship The ship to check if the crew member can join.
     * @return True if the ship is an instance of OrganicSupportShip and has the acceptable food type,
     * false otherwise.
     */
    @Override
    public boolean canJoin(Ship ship) {
        if (ship instanceof OrganicSupportShip) {
            return ((OrganicSupportShip) ship).getFoodTypes().contains(acceptableFoodType);
        }
        return false;

    }

    /**
     * Retrieves the name of the organic crew member.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the organic crew member.
     *
     * @param name The name to set.
     * @throws RuntimeException If name is null or empty.
     */
    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    /**
     * Retrieves the acceptable food type for the organic crew member.
     *
     * @return The acceptable food type.
     */
    public FoodType getAcceptableFoodType() {
        return acceptableFoodType;
    }

    /**
     * Sets the acceptable food type for the organic crew member.
     *
     * @param acceptableFoodType The acceptable food type to set.
     */
    public void setAcceptableFoodType(FoodType acceptableFoodType) {
        this.acceptableFoodType = acceptableFoodType;
    }
}
