package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OrganicCrewMember extends CrewMember implements Serializable {

    private String name;
    private FoodType acceptableFoodType;

    private OrganicCrewMember(String name, FoodType acceptableFoodType) {
        super();
        setName(name);
        setAcceptableFoodType(acceptableFoodType);
    }

    public static OrganicCrewMember constructor(String name, FoodType acceptableFoodType) {
        Util.validString(name);
        return new OrganicCrewMember(name, acceptableFoodType);
    }


    @Override
    public boolean canJoin(Ship ship) {
        if (ship instanceof OrganicSupportShip) {
            return ((OrganicSupportShip) ship).getFoodTypes().contains(acceptableFoodType);
        }
        return false;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    public FoodType getAcceptableFoodType() {
        return acceptableFoodType;
    }

    public void setAcceptableFoodType(FoodType acceptableFoodType) {
        this.acceptableFoodType = acceptableFoodType;
    }
}
