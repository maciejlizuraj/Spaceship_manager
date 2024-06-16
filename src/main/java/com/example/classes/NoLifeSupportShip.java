package com.example.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NoLifeSupportShip extends Ship {

    private String shipAIType;

    private NoLifeSupportShip(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, String shipAIType) {
        super(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        this.shipAIType = shipAIType;
    }

    public static NoLifeSupportShip constructor(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, String shipAIType) {
        Util.validString(shipAIType);
        return new NoLifeSupportShip(name, maxCargoMassCapacity,shipType,solarFlareShieldStrength,shipAIType);
    }

    public String getShipAIType() {
        return shipAIType;
    }

    public void setShipAIType(String shipAIType) {
        this.shipAIType = shipAIType;
    }
}
