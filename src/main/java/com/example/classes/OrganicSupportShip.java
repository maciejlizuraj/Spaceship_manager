package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OrganicSupportShip extends Ship implements Serializable {

    private Set<FoodType> foodTypes;

    private OrganicSupportShip(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, Set<FoodType> foodTypes) {
        super(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        this.foodTypes = foodTypes;
    }

    public static OrganicSupportShip constructor(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength, Set<FoodType> foodTypes){
        validArgsCheck(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        return new OrganicSupportShip(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength, foodTypes);
    }

    public Set<FoodType> getFoodTypes() {
        return Collections.unmodifiableSet(foodTypes);
    }

    public void setFoodTypes(Set<FoodType> foodTypes) {
        if (foodTypes == null) {
            throw new RuntimeException("Food types can not be null");
        }
        this.foodTypes = foodTypes;
    }
}
