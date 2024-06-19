package com.example.classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a galaxy.
 * Extends ObjectPlus and implements Serializable.
 */
public class Galaxy extends ObjectPlus implements Serializable {
    private String name;
    private String galaxyCode;
    private Boolean atWar;
    private Integer solarFlareStrength;
    private LocalDate peacefulSince;
    private GalaxyType galaxyType;

    private Set<Ship> ships;
    private Set<Cargo> cargoSet;

    /**
     * Private constructor to initialize a Galaxy object.
     *
     * @param name               The name of the galaxy.
     * @param galaxyCode         The unique code identifying the galaxy.
     * @param atWar              Whether the galaxy is at war (only for Dangerous type).
     * @param solarFlareStrength The strength of solar flares (only for Dangerous type).
     * @param peacefulSince      The date since which the galaxy has been peaceful (only for Peaceful type).
     * @param galaxyType         The type of the galaxy (Peaceful or Dangerous).
     */
    private Galaxy(String name, String galaxyCode, Boolean atWar, Integer solarFlareStrength, LocalDate peacefulSince, GalaxyType galaxyType) {
        galaxyCodeUniquenessCheck(galaxyCode);
        setName(name);
        setGalaxyCode(galaxyCode);

        if (galaxyType == GalaxyType.Peaceful) {
            changeToPeaceful(peacefulSince);
        }
        if (galaxyType == GalaxyType.Dangerous) {
            changeToDangerous(atWar, solarFlareStrength);
        }
        ships = new HashSet<>();
        cargoSet = new HashSet<>();
    }

    /**
     * Static factory method to create a Peaceful type Galaxy.
     *
     * @param name          The name of the galaxy.
     * @param galaxyCode    The unique code identifying the galaxy.
     * @param peacefulSince The date since which the galaxy has been peaceful.
     * @return A new Galaxy object of Peaceful type.
     * @throws RuntimeException If name, galaxyCode, or peacefulSince is null or invalid.
     */
    public static Galaxy peacefulGalaxyConstructor(String name, String galaxyCode, LocalDate peacefulSince) {
        Util.validString(name);
        galaxyCodeUniquenessCheck(galaxyCode);
        if (peacefulSince == null) throw new RuntimeException("Peaceful since can't be null");
        return new Galaxy(name, galaxyCode, null, null, peacefulSince, GalaxyType.Peaceful);
    }

    /**
     * Static factory method to create a Dangerous type Galaxy.
     *
     * @param name               The name of the galaxy.
     * @param galaxyCode         The unique code identifying the galaxy.
     * @param atWar              Whether the galaxy is at war.
     * @param solarFlareStrength The strength of solar flares.
     * @return A new Galaxy object of Dangerous type.
     * @throws RuntimeException If name, galaxyCode, atWar, or solarFlareStrength is null or invalid, or solarFlareStrength is negative.
     */
    public static Galaxy dangerousGalaxyConstructor(String name, String galaxyCode, Boolean atWar, Integer solarFlareStrength) {
        Util.validString(name);
        galaxyCodeUniquenessCheck(galaxyCode);
        if (atWar == null) throw new RuntimeException("At war can't be null");
        if (solarFlareStrength == null) throw new RuntimeException("Solar flare strength can't be null");
        Util.nonNegativeIntCheck(solarFlareStrength);
        return new Galaxy(name, galaxyCode, atWar, solarFlareStrength, null, GalaxyType.Dangerous);
    }

    /**
     * Changes the galaxy to a Dangerous type with specified attributes.
     *
     * @param atWar              Whether the galaxy is at war.
     * @param solarFlareStrength The strength of solar flares.
     * @throws RuntimeException If the galaxy is already of Dangerous type.
     */
    public void changeToDangerous(boolean atWar, int solarFlareStrength) {
        if (galaxyType == GalaxyType.Dangerous) {
            throw new RuntimeException("Galaxy is already of dangerous type");
        }
        galaxyType = GalaxyType.Dangerous;
        this.atWar = atWar;
        peacefulSince = null;
        setSolarFlareStrength(solarFlareStrength);
    }

    /**
     * Changes the galaxy to a Peaceful type with specified peaceful since date.
     *
     * @param peacefulSince The date since the galaxy has been peaceful.
     * @throws RuntimeException If the galaxy is already of Peaceful type.
     */
    public void changeToPeaceful(LocalDate peacefulSince) {
        if (galaxyType == GalaxyType.Peaceful) {
            throw new RuntimeException("Galaxy is already of peaceful type");
        }
        galaxyType = GalaxyType.Peaceful;
        setPeacefulSince(peacefulSince);
        atWar = null;
        solarFlareStrength = null;
    }


    /**
     * Retrieves the name of the galaxy.
     *
     * @return The name of the galaxy.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the galaxy.
     *
     * @param name The name to set.
     * @throws RuntimeException If name is null or empty.
     */
    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    /**
     * Retrieves the galaxy code.
     *
     * @return The galaxy code.
     */
    public String getGalaxyCode() {
        return galaxyCode;
    }

    /**
     * Sets the galaxy code.
     *
     * @param galaxyCode The galaxy code to set.
     * @throws RuntimeException If galaxyCode is null, empty, or already in use.
     */
    public void setGalaxyCode(String galaxyCode) {
        if (galaxyCode.equals(this.galaxyCode)) return;
        galaxyCodeUniquenessCheck(galaxyCode);
        Util.validString(galaxyCode);
        this.galaxyCode = galaxyCode;
    }

    /**
     * Retrieves whether the galaxy is at war.
     *
     * @return True if the galaxy is at war (only for Dangerous type), otherwise throws RuntimeException.
     * @throws RuntimeException If called on a Peaceful type galaxy.
     */
    public Boolean getAtWar() {
        if (galaxyType == GalaxyType.Peaceful)
            throw new RuntimeException("Peaceful galaxy doesn't have at war attribute");
        return atWar;
    }

    /**
     * Sets the at war status of the galaxy (only for Dangerous type).
     *
     * @param atWar The at war status to set.
     * @throws RuntimeException If atWar is null.
     */
    private void setAtWar(Boolean atWar) {
        if (atWar == null) throw new RuntimeException("At war can't be set to null");
        this.atWar = atWar;
    }

    /**
     * Retrieves the solar flare strength of the galaxy (only for Dangerous type).
     *
     * @return The solar flare strength.
     * @throws RuntimeException If called on a Peaceful type galaxy.
     */
    public Integer getSolarFlareStrength() {
        if (galaxyType == GalaxyType.Peaceful)
            throw new RuntimeException("Peaceful galaxy doesn't have solar flare strength attribute");
        return solarFlareStrength;
    }

    /**
     * Sets the solar flare strength of the galaxy (only for Dangerous type).
     *
     * @param solarFlareStrength The solar flare strength to set.
     * @throws RuntimeException If solarFlareStrength is null or negative.
     */
    private void setSolarFlareStrength(Integer solarFlareStrength) {
        if (atWar == null) throw new RuntimeException("Solar flare strength can't be set to null");
        Util.nonNegativeIntCheck(solarFlareStrength);
        this.solarFlareStrength = solarFlareStrength;
    }

    /**
     * Retrieves the date since the galaxy has been peaceful (only for Peaceful type).
     *
     * @return The date since the galaxy has been peaceful.
     * @throws RuntimeException If called on a Dangerous type galaxy.
     */
    public LocalDate getPeacefulSince() {
        if (galaxyType == GalaxyType.Dangerous)
            throw new RuntimeException("Dangerous galaxy doesn't have peaceful since attribute");
        return peacefulSince;
    }

    /**
     * Sets the date since the galaxy has been peaceful (only for Peaceful type).
     *
     * @param peacefulSince The date to set.
     * @throws RuntimeException If peacefulSince is null.
     */
    private void setPeacefulSince(LocalDate peacefulSince) {
        if (peacefulSince == null) throw new RuntimeException("Solar flare strength can't be set to null");
        this.peacefulSince = peacefulSince;
    }

    /**
     * Retrieves the type of the galaxy (Peaceful or Dangerous).
     *
     * @return The galaxy type.
     */
    public GalaxyType getGalaxyType() {
        return galaxyType;
    }

    /**
     * Retrieves the unmodifiable set of ships in the galaxy.
     *
     * @return The set of ships.
     */
    public Set<Ship> getShips() {
        return Collections.unmodifiableSet(ships);
    }

    /**
     * Adds a ship to the galaxy.
     * @param ship The ship to add.
     * @throws RuntimeException If ship is null.
     */
    public void addShip(Ship ship) {
        if (ship == null) {
            throw new RuntimeException("Can not add null");
        }

        if (ships.contains(ship)) return;

        ships.add(ship);
        ship.setGalaxy(this);
    }
    /**
     * Removes a ship from the galaxy.
     * @param ship The ship to remove.
     */
    public void removeShip(Ship ship) {
        if (!ships.contains(ship)) return;
        ships.remove(ship);
        ship.setGalaxy(null);
    }
    /**
     * Retrieves the set of cargo in the galaxy, sorted in their natural order.
     * @return The sorted set of cargo.
     */
    public Set<Cargo> getCargoSet() {
        List<Cargo> list = new ArrayList<>(cargoSet);
        Collections.sort(list);
        return new LinkedHashSet<>(list);
    }
    /**
     * Adds a cargo to the galaxy.
     * @param cargo The cargo to add.
     * @throws RuntimeException If cargo is null.
     */
    public void addCargo(Cargo cargo) {
        if (cargo == null) {
            throw new RuntimeException("Can not add null");
        }

        if (cargoSet.contains(cargo)) return;

        cargoSet.add(cargo);
        cargo.setDestination(this);
    }
    /**
     * Removes a cargo from the galaxy.
     * @param cargo The cargo to remove.
     */
    public void removeCargo(Cargo cargo) {
        if (!cargoSet.contains(cargo)) return;
        cargoSet.remove(cargo);
        cargo.setDestination(null);
    }

    /**
     * Checks the uniqueness of a galaxy code across all existing galaxy instances.
     * @param galaxyCode The galaxy code to check for uniqueness.
     * @throws RuntimeException If galaxyCode is already in use by another galaxy.
     */
    public static void galaxyCodeUniquenessCheck(String galaxyCode) {
        Util.validString(galaxyCode);
        try {
            Iterable<Galaxy> extent = getExtent(Galaxy.class);
            if (extent != null) {
                for (Galaxy galaxy : extent) {
                    if (galaxy.getGalaxyCode()!=null){
                        if (galaxy.getGalaxyCode().equals(galaxyCode)) {
                            throw new RuntimeException("Galaxy code is already in use");
                        }
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
