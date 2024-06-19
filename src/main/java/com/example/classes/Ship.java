package com.example.classes;

import java.util.*;

/**
 * Represents an abstract ship with basic attributes and functionality.
 * Provides methods to manage cargo, contracts, and interactions with galaxies.
 * Extends ObjectPlus and implements Serializable.
 */
public abstract class Ship {

    private String name;
    private Integer maxCargoMassCapacity;
    private ShipType shipType;
    private Integer solarFlareShieldStrength;

    private Galaxy galaxy;
    private Set<Cargo> cargoSet;
    private Set<Contract> contracts;

    /**
     * Protected constructor to initialize a Ship object with specified attributes.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship.
     * @param solarFlareShieldStrength The solar flare shield strength of the ship.
     */
    protected Ship(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength) {
        validArgsCheck(name, maxCargoMassCapacity, shipType, solarFlareShieldStrength);
        setName(name);
        setMaxCargoMassCapacity(maxCargoMassCapacity);
        this.shipType = shipType;
        this.solarFlareShieldStrength = solarFlareShieldStrength;

        cargoSet = new HashSet<>();
        contracts = new HashSet<>();
    }

    /**
     * Static method to validate arguments passed to the Ship constructor.
     *
     * @param name                     The name of the ship.
     * @param maxCargoMassCapacity     The maximum cargo mass capacity of the ship.
     * @param shipType                 The type of the ship.
     * @param solarFlareShieldStrength The solar flare shield strength of the ship.
     * @throws RuntimeException If any of the arguments (name, maxCargoMassCapacity, shipType, solarFlareShieldStrength) is invalid.
     */
    public static void validArgsCheck(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength) {
        Util.validString(name);
        Util.positiveIntCheck(maxCargoMassCapacity);
        if (shipType == ShipType.Shielded) {
            if (solarFlareShieldStrength == null) {
                throw new RuntimeException("Shielded ship can't have null solar flare shield strength");
            }
            Util.nonNegativeIntCheck(solarFlareShieldStrength);
        }
        if (shipType == ShipType.NoProtection) {
            if (solarFlareShieldStrength != null) {
                throw new RuntimeException("No protection ship can't have solar flare shield strength");
            }
        }
    }

    /**
     * Retrieves the name of the ship.
     *
     * @return The name of the ship.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ship.
     *
     * @param name The name to set.
     * @throws RuntimeException If name is null or empty.
     */
    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    /**
     * Retrieves the maximum cargo mass capacity of the ship.
     *
     * @return The maximum cargo mass capacity.
     */
    public Integer getMaxCargoMassCapacity() {
        return maxCargoMassCapacity;
    }

    /**
     * Sets the maximum cargo mass capacity of the ship.
     *
     * @param maxCargoMassCapacity The maximum cargo mass capacity to set.
     * @throws RuntimeException If maxCargoMassCapacity is not a positive integer.
     */
    public void setMaxCargoMassCapacity(Integer maxCargoMassCapacity) {
        Util.positiveIntCheck(maxCargoMassCapacity);
        this.maxCargoMassCapacity = maxCargoMassCapacity;
    }

    /**
     * Retrieves the type of the ship.
     *
     * @return The ship type.
     */
    public ShipType getShipType() {
        return shipType;
    }

    /**
     * Retrieves the solar flare shield strength of the ship.
     *
     * @return The solar flare shield strength.
     * @throws RuntimeException If the ship type is NoProtection.
     */
    public Integer getSolarFlareShieldStrength() {
        if (shipType == ShipType.NoProtection) {
            throw new RuntimeException("No protection ship does not have solar flare shield strength attribute");
        }
        return solarFlareShieldStrength;
    }

    /**
     * Sets the solar flare shield strength of the ship.
     *
     * @param solarFlareShieldStrength The solar flare shield strength to set.
     * @throws RuntimeException If the ship type is NoProtection or solarFlareShieldStrength is negative.
     */
    public void setSolarFlareShieldStrength(int solarFlareShieldStrength) {
        if (shipType == ShipType.NoProtection) {
            throw new RuntimeException("No protection ship does not have solar flare shield strength attribute");
        }
        Util.nonNegativeIntCheck(solarFlareShieldStrength);
        this.solarFlareShieldStrength = solarFlareShieldStrength;
    }

    /**
     * Retrieves the galaxy to which the ship belongs.
     *
     * @return The galaxy object.
     */
    public Galaxy getGalaxy() {
        return galaxy;
    }

    /**
     * Sets the galaxy to which the ship belongs.
     *
     * @param galaxy The galaxy object to set.
     */
    public void setGalaxy(Galaxy galaxy) {
        if (this.galaxy == galaxy) return;

        if (this.galaxy != null) {
            this.galaxy.removeShip(this);
            if (galaxy == null) {
                this.galaxy = null;
                return;
            }
        }
        this.galaxy = galaxy;
        galaxy.addShip(this);
    }

    /**
     * Retrieves the set of cargo currently on the ship.
     *
     * @return An unmodifiable set of cargo.
     */
    public Set<Cargo> getCargoSet() {
        return Collections.unmodifiableSet(cargoSet);
    }

    /**
     * Adds a cargo to the ship's cargo set.
     *
     * @param cargo The cargo object to add.
     * @throws RuntimeException If cargo is null or adding the cargo would exceed total allowed mass.
     */
    public void addCargo(Cargo cargo) {
        if (cargo == null) {
            throw new RuntimeException("Can not add null");
        }

        if (cargoSet.contains(cargo)) return;

        canAddCargoCheck(cargo);

        cargoSet.add(cargo);
        cargo.setShip(this);
    }

    /**
     * Removes a cargo from the ship's cargo set.
     *
     * @param cargo The cargo object to remove.
     */
    public void removeCargo(Cargo cargo) {

        if (!cargoSet.contains(cargo)) return;
        cargoSet.remove(cargo);
        cargo.setShip(null);

    }

    /**
     * Retrieves the set of contracts associated with the ship.
     *
     * @return An unmodifiable set of contracts.
     */
    public Set<Contract> getContracts() {
        return Collections.unmodifiableSet(contracts);
    }

    /**
     * Adds a contract to the ship's contract set.
     *
     * @param contract The contract object to add.
     * @throws RuntimeException If contract is null or not intended for this ship,
     *                          or if a contract with the same crew member already exists.
     */
    public void addContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");

        if (contracts.contains(contract)) return;
        if (contract.getShip() != this) throw new IllegalArgumentException("This contract is not for this ship");
        for (Contract contr : contracts) {
            if (contr.getCrewMember() == contract.getCrewMember()) {
                throw new IllegalArgumentException("Contract between this crew member and ship already exists");
            }
        }
        contracts.add(contract);
    }

    /**
     * Removes a contract from the ship's contract set.
     *
     * @param contract The contract object to remove.
     * @throws RuntimeException If contract is null.
     */
    public void removeContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");


        if (!contracts.contains(contract)) return;

        contracts.remove(contract);
        contract.getCrewMember().removeContract(contract);
        contract.setToNulls();
    }

    /**
     * Retrieves the current total mass of cargo on the ship.
     *
     * @return The current total cargo mass.
     */
    private int getCurrentCargoMass() {
        int total = 0;
        for (Cargo cargo : cargoSet) {
            total += cargo.getMass();
        }
        return total;
    }

    /**
     * Checks if adding a specific cargo would exceed the total allowed mass capacity of the ship.
     *
     * @param cargo The cargo object to check.
     * @throws RuntimeException If adding the cargo would exceed total allowed mass.
     */
    public void canAddCargoCheck(Cargo cargo) {
        if (getCurrentCargoMass() + cargo.getMass() > maxCargoMassCapacity) {
            throw new RuntimeException("Adding this cargo would exceed total allowed mass");
        }
    }

    /**
     * Checks if adding a specific cargo mass would exceed the total allowed mass capacity of the ship.
     *
     * @param i The cargo mass to check.
     * @throws RuntimeException If adding the cargo mass would exceed total allowed mass.
     */
    public void canAddCargoCheck(int i) {
        if (getCurrentCargoMass() + i > maxCargoMassCapacity) {
            throw new RuntimeException("Adding this cargo would exceed total allowed mass");
        }
    }

    /**
     * Checks if the ship can go to a specific galaxy based on its ship type and galaxy conditions.
     *
     * @param galaxy The galaxy object to check.
     * @return True if the ship can go to the galaxy, false otherwise.
     * @throws RuntimeException If galaxy is null.
     */
    public boolean canGoToGalaxy(Galaxy galaxy) {
        if (galaxy == null) throw new RuntimeException("Galaxy can not be null");
        return switch (shipType) {
            case Shielded -> switch (galaxy.getGalaxyType()) {
                case Peaceful -> true;
                case Dangerous ->
                        !(galaxy.getAtWar() || galaxy.getSolarFlareStrength() > getSolarFlareShieldStrength());
            };
            case NoProtection -> galaxy.getGalaxyType().equals(GalaxyType.Peaceful);
        };
    }

    /**
     * Retrieves a mapping of galaxies to the set of cargo destined for each galaxy.
     *
     * @return A map where each key is a galaxy and the value is a set of cargo destined for that galaxy.
     */
    public Map<Galaxy, Set<Cargo>> getGalaxyToCargo() {
        Map<Galaxy, Set<Cargo>> map = new HashMap<>();
        for (Cargo cargo : cargoSet) {
            Galaxy galaxyDest = cargo.getDestination();
            map.computeIfAbsent(galaxyDest, g -> new HashSet<>()).add(cargo);
        }
        return map;
    }

    /**
     * Unloads cargo that is destined for a specific galaxy.
     *
     * @param galaxy The galaxy object representing the destination.
     */
    public void unloadCargoGoingTo(Galaxy galaxy) {
        cargoSet.removeIf(cargo -> cargo.getDestination() == galaxy);
    }

    /**
     * Unloads all cargo that cannot reach any destination galaxy based on ship's capabilities.
     */
    public void unloadAllUnreachableCargo() {
        cargoSet.removeIf(cargo -> !canGoToGalaxy(cargo.getDestination()));
    }
}
