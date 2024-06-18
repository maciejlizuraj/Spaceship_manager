package com.example.classes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MechanicalCrewMember extends CrewMember implements Serializable {

    private String serialNumber;
    private String modelNumber;

    private MechanicalCrewMember(String serialNumber, String modelNumber) {
        super();
        setSerialNumber(serialNumber);
        setModelNumber(modelNumber);
    }

    //Static method as constructor to avoid adding to superclass extent
    public static MechanicalCrewMember constructor(String serialNumber, String modelNumber) {
        Util.validString(serialNumber);
        Util.validString(modelNumber);
        return new MechanicalCrewMember(serialNumber, modelNumber);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    @Override
    public boolean canJoin(Ship ship) {
        return true;
    }


}
