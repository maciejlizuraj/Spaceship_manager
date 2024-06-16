package com.example.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Ship {
    Set<Ship> shipsExtent = new HashSet<>();

    private String name;
    private Integer maxCargoMassCapacity;
    private ShipType shipType;
    private Integer solarFlareShieldStrength;

    private Galaxy galaxy;
    private Set<Cargo> cargoSet;
    private Set<Contract> contracts;

    public Ship(String name, int maxCargoMassCapacity, ShipType shipType, Integer solarFlareShieldStrength) {
        setName(name);
        setMaxCargoMassCapacity(maxCargoMassCapacity);
        if (shipType == ShipType.Shielded) {
            if (solarFlareShieldStrength == null) {
                throw new RuntimeException("Shielded ship can't have null solar flare shield strength");
            }
            this.shipType = ShipType.Shielded;
            setSolarFlareShieldStrength(solarFlareShieldStrength);
        }
        if (shipType == ShipType.NoProtection) {
            if (solarFlareShieldStrength != null) {
                throw new RuntimeException("No protection ship can't have solar flare shield strength");
            }
            this.shipType = ShipType.NoProtection;
        }
        cargoSet = new HashSet<>();
        contracts = new HashSet<>();
        shipsExtent.add(this);
    }

    public Set<Ship> getShipsExtent() {
        return Collections.unmodifiableSet(shipsExtent);
    }

    public void resetExtent() {
        shipsExtent = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    public Integer getMaxCargoMassCapacity() {
        return maxCargoMassCapacity;
    }

    public void setMaxCargoMassCapacity(Integer maxCargoMassCapacity) {
        Util.positiveIntCheck(maxCargoMassCapacity);

        this.maxCargoMassCapacity = maxCargoMassCapacity;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Integer getSolarFlareShieldStrength() {
        if (shipType == ShipType.NoProtection) {
            throw new RuntimeException("No protection ship does not have solar flare shield strength attribute");
        }
        return solarFlareShieldStrength;
    }

    public void setSolarFlareShieldStrength(int solarFlareShieldStrength) {
        if (shipType == ShipType.NoProtection) {
            throw new RuntimeException("No protection ship does not have solar flare shield strength attribute");
        }
        Util.nonNegativeDoubleCheck(solarFlareShieldStrength);
        this.solarFlareShieldStrength = solarFlareShieldStrength;
    }

    public Galaxy getGalaxy() {
        return galaxy;
    }

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

    public Set<Cargo> getCargoSet() {
        return Collections.unmodifiableSet(cargoSet);
    }

    public void addCargo(Cargo cargo) {
        if (cargo == null) {
            throw new RuntimeException("Can not add null");
        }

        if (cargoSet.contains(cargo)) return;

        canAddCargoCheck(cargo);

        cargoSet.add(cargo);
        cargo.setShip(this);
    }

    public void removeCargo(Cargo cargo) {

        if (!cargoSet.contains(cargo)) return;
        cargoSet.remove(cargo);
        cargo.setShip(null);

    }

    public Set<Contract> getContracts() {
        return Collections.unmodifiableSet(contracts);
    }

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

    public void removeContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");


        if (!contracts.contains(contract)) return;

        contracts.remove(contract);
        contract.getCrewMember().removeContract(contract);
        contract.setToNulls();
    }

    private int getCurrentCargoMass() {
        int total = 0;
        for (Cargo cargo : cargoSet) {
            total += cargo.getMass();
        }
        return total;
    }

    public void canAddCargoCheck(Cargo cargo) {
        if (getCurrentCargoMass() + cargo.getMass() > maxCargoMassCapacity) {
            throw new RuntimeException("Adding this cargo would exceed total allowed mass");
        }
    }

    public void canAddCargoCheck(int i) {
        if (getCurrentCargoMass() + i > maxCargoMassCapacity) {
            throw new RuntimeException("Adding this cargo would exceed total allowed mass");
        }
    }

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
}
