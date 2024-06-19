package com.example.classes;

import java.io.Serializable;

/**
 * Represents a mechanical crew member that extends the CrewMember class.
 * Extends CrewMember and implements Serializable.
 */
public class MechanicalCrewMember extends CrewMember implements Serializable {

    private String serialNumber;
    private String modelNumber;

    /**
     * Private constructor to initialize a MechanicalCrewMember object.
     *
     * @param serialNumber The serial number of the mechanical crew member.
     * @param modelNumber  The model number of the mechanical crew member.
     */
    private MechanicalCrewMember(String serialNumber, String modelNumber) {
        super();
        setSerialNumber(serialNumber);
        setModelNumber(modelNumber);
    }

    /**
     * Static factory method to create a MechanicalCrewMember object.
     *
     * @param serialNumber The serial number of the mechanical crew member.
     * @param modelNumber  The model number of the mechanical crew member.
     * @return A new MechanicalCrewMember object.
     * @throws RuntimeException If serialNumber or modelNumber is invalid
     */
    public static MechanicalCrewMember constructor(String serialNumber, String modelNumber) {
        Util.validString(serialNumber);
        Util.validString(modelNumber);
        return new MechanicalCrewMember(serialNumber, modelNumber);
    }

    /**
     * Retrieves the serial number of the mechanical crew member.
     *
     * @return The serial number.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number of the mechanical crew member.
     *
     * @param serialNumber The serial number to set.
     * @throws RuntimeException If serialNumber is invalid
     */
    public void setSerialNumber(String serialNumber) {
        Util.validString(serialNumber);
        this.serialNumber = serialNumber;
    }

    /**
     * Retrieves the model number of the mechanical crew member.
     *
     * @return The model number.
     */
    public String getModelNumber() {
        return modelNumber;
    }

    /**
     * Sets the model number of the mechanical crew member.
     *
     * @param modelNumber The model number to set.
     * @throws RuntimeException If modelNumber is invalid
     */
    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    /**
     * Overrides the canJoin method from CrewMember to allow joining any ship.
     *
     * @param ship The ship to check if the crew member can join.
     * @return Always returns true for mechanical crew members.
     */
    @Override
    public boolean canJoin(Ship ship) {
        return true;
    }


}
