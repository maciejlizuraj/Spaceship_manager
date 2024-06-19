package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The Cargo class represents a cargo item and extends ObjectPlus.
 * It implements Comparable and Serializable interfaces.
 * This class includes information about the cargo's name, mass, owner, destination, and associated ship.
 */
public class Cargo extends ObjectPlus implements Comparable<Cargo>, Serializable {
    /**
     * A set of registered owners of the cargo.
     */
    private static Set<String> registeredOwners = new HashSet<>();

    private String name;
    private int mass;
    private String owner;

    private Galaxy destination;
    private Ship ship;

    /**
     * Private constructor to initialize a Cargo object.
     *
     * @param name  the name of the cargo
     * @param mass  the mass of the cargo
     * @param owner the owner of the cargo
     */
    private Cargo(String name, int mass, String owner) {
        setName(name);
        setMass(mass);
        setOwner(owner);
    }

    /**
     * Static factory method to create a Cargo object.
     *
     * @param name  the name of the cargo
     * @param mass  the mass of the cargo
     * @param owner the owner of the cargo
     * @return a new {@code Cargo} object
     */
    public static Cargo constructor(String name, int mass, String owner) {
        Util.validString(name);
        Util.positiveIntCheck(mass);
        validOwnerCheck(owner);
        return new Cargo(name, mass, owner);
    }

    /**
     * Returns an unmodifiable set of registered owners.
     *
     * @return an unmodifiable set of registered owners
     */
    public static Set<String> getRegisteredOwners() {
        return Collections.unmodifiableSet(registeredOwners);
    }

    /**
     * Sets the registered owners of the cargo.
     *
     * @param registeredOwners the set of registered owners
     */
    public static void setRegisteredOwners(Set<String> registeredOwners) {
        Cargo.registeredOwners = registeredOwners;
    }

    /**
     * Adds a new owner to the set of registered owners.
     *
     * @param owner the new owner to be added
     */
    public static void addRegisteredOwner(String owner) {
        Util.validString(owner);
        registeredOwners.add(owner);
    }

    /**
     * Removes an owner from the set of registered owners.
     *
     * @param owner the owner to be removed
     * @throws Exception if the owner has existing cargo
     */
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

    /**
     * Returns the name of the cargo.
     *
     * @return the name of the cargo
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the cargo.
     *
     * @param name the new name of the cargo
     */
    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    /**
     * Returns the mass of the cargo.
     *
     * @return the mass of the cargo
     */
    public int getMass() {
        return mass;
    }

    /**
     * Sets the mass of the cargo.
     *
     * @param mass the new mass of the cargo
     */
    public void setMass(int mass) {
        Util.positiveIntCheck(mass);
        if (ship != null) {
            ship.canAddCargoCheck(mass - this.mass);
        }
        this.mass = mass;
    }

    /**
     * Returns the owner of the cargo.
     *
     * @return the owner of the cargo
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the cargo.
     *
     * @param owner the new owner of the cargo
     */
    public void setOwner(String owner) {
        validOwnerCheck(owner);
        this.owner = owner;
    }

    /**
     * Validates if owner is valid by checking validity of a string
     * and if owner is present in registered owners.
     *
     * @param owner owner whose validity should be checked
     */
    private static void validOwnerCheck(String owner) {
        Util.validString(owner);
        if (!registeredOwners.contains(owner)) throw new RuntimeException("Owner is not in registered owners.");
    }

    /**
     * Returns the destination galaxy of the cargo.
     *
     * @return the destination galaxy of the cargo
     */
    public Galaxy getDestination() {
        return destination;
    }

    /**
     * Sets the destination galaxy of the cargo.
     *
     * @param destination the new destination galaxy of the cargo
     */
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

    /**
     * Returns the ship associated with the cargo.
     *
     * @return the ship associated with the cargo
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets the ship associated with the cargo.
     *
     * @param ship the new ship associated with the cargo
     */
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

    /**
     * Compares this cargo to another cargo based on mass.
     *
     * @param o the other cargo to be compared
     * @return a negative integer, zero, or a positive integer as this cargo is less than, equal to, or greater than the specified cargo
     */
    @Override
    public int compareTo(Cargo o) {
        return Integer.compare(this.mass, o.mass);
    }
}
