package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Cargo extends ObjectPlus implements Comparable<Cargo>, Serializable {
    private static Set<String> registeredOwners = new HashSet<>();

    private String name;
    private int mass;
    private String owner;

    private Galaxy destination;
    private Ship ship;

    private Cargo(String name, int mass, String owner) {
        setName(name);
        setMass(mass);
        setOwner(owner);
    }

    public static Cargo constructor(String name, int mass, String owner) {
        Util.validString(name);
        Util.positiveIntCheck(mass);
        validOwnerCheck(owner);
        return new Cargo(name, mass, owner);
    }

    public static Set<String> getRegisteredOwners() {
        return Collections.unmodifiableSet(registeredOwners);
    }

    public static void setRegisteredOwners(Set<String> registeredOwners) {
        Cargo.registeredOwners = registeredOwners;
    }

    public static void addRegisteredOwner(String owner) {
        Util.validString(owner);
        registeredOwners.add(owner);
    }

    public static void removeRegisteredOwner(String owner) throws Exception {
        Iterable<Cargo> extent = getExtent(Cargo.class);
        if (extent != null) {
            for (Cargo cargo : extent) {
                if (cargo.owner.equals(owner))
                    throw new RuntimeException("Can not remove an owner that already has cargo.");
            }
            registeredOwners.remove(owner);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        Util.positiveIntCheck(mass);
        if (ship != null) {
            ship.canAddCargoCheck(mass - this.mass);
        }
        this.mass = mass;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        validOwnerCheck(owner);
        this.owner = owner;
    }

    private static void validOwnerCheck(String owner) {
        Util.validString(owner);
        if (!registeredOwners.contains(owner)) throw new RuntimeException("Owner is not in registered owners.");
    }

    public Galaxy getDestination() {
        return destination;
    }

    public void setDestination(Galaxy destination) {
        if (this.destination == destination) return;

        if (this.destination != null) {
            this.destination.removeCargo(this);
            if (destination == null) {
                this.destination = null;
                return;
            }
        }
        this.destination = destination;
        destination.addCargo(this);
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        if (this.ship == ship) return;
        if (this.ship != null) {
            this.ship.removeCargo(this);
            if (ship == null) {
                this.ship = null;
                return;
            }
        }
        this.ship = ship;
        ship.addCargo(this);

        this.ship = ship;
    }

    @Override
    public int compareTo(Cargo o) {
        return Integer.compare(this.mass, o.mass);
    }
}
