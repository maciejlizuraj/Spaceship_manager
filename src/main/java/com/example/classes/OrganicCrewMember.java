package com.example.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OrganicCrewMember extends CrewMember {

    private String name;
    private FoodType acceptableFoodType;

    public OrganicCrewMember(String name, FoodType acceptableFoodType) {
        super();
        setName(name);
        setAcceptableFoodType(acceptableFoodType);
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
