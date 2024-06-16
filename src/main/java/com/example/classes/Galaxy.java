package com.example.classes;

import java.time.LocalDate;
import java.util.*;

public class Galaxy {
    private static Set<Galaxy> galaxyExtent = new HashSet<>();

    private String name;
    private String galaxyCode;
    private Boolean atWar;
    private Integer solarFlareStrength;
    private LocalDate peacefulSince;
    private GalaxyType galaxyType;

    private Set<Ship> ships;
    private Set<Cargo> cargoSet;

    //Constructor is private. Static methods should be used instead.
    private Galaxy(String name, String galaxyCode, Boolean atWar, Integer solarFlareStrength, LocalDate peacefulSince, GalaxyType galaxyType) {
        galaxyCodeUniquenessCheck(galaxyCode);
        setName(name);
        setGalaxyCode(galaxyCode);

        if (galaxyType == GalaxyType.Peaceful) {
            setPeaceful(peacefulSince);
        }
        if (galaxyType == GalaxyType.Dangerous) {
            setDangerous(atWar, solarFlareStrength);
        }
        ships = new HashSet<>();
        cargoSet = new HashSet<>();
        galaxyExtent.add(this);
    }

    public static Galaxy peacefulGalaxyConstructor(String name, String galaxyCode, LocalDate peacefulSince) {
        return new Galaxy(name, galaxyCode, null, null, peacefulSince, GalaxyType.Peaceful);
    }

    public static Galaxy dangerousGalaxyConstructor(String name, String galaxyCode, Boolean atWar, Integer solarFlareStrength) {
        return new Galaxy(name, galaxyCode, atWar, solarFlareStrength, null, GalaxyType.Dangerous);
    }

    public static Set<Galaxy> getGalaxyExtent() {
        return Collections.unmodifiableSet(galaxyExtent);
    }

    public static void resetExtent() {
        galaxyExtent = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Util.validString(name);
        this.name = name;
    }

    public String getGalaxyCode() {
        return galaxyCode;
    }

    public void setGalaxyCode(String galaxyCode) {
        if (galaxyCode.equals(this.galaxyCode)) return;
        galaxyCodeUniquenessCheck(galaxyCode);
        Util.validString(galaxyCode);
        this.galaxyCode = galaxyCode;
    }

    public Boolean getAtWar() {
        if (galaxyType == GalaxyType.Peaceful)
            throw new RuntimeException("Peaceful galaxy doesn't have at war attribute");
        return atWar;
    }

    //to be used only by galaxies which have this attribute
    private void setAtWar(Boolean atWar) {
        if (atWar == null) throw new RuntimeException("At war can't be set to null");
        this.atWar = atWar;
    }

    public Integer getSolarFlareStrength() {
        if (galaxyType == GalaxyType.Peaceful)
            throw new RuntimeException("Peaceful galaxy doesn't have solar flare strength attribute");
        return solarFlareStrength;
    }

    private void setSolarFlareStrength(Integer solarFlareStrength) {
        if (atWar == null) throw new RuntimeException("Solar flare strength can't be set to null");
        this.solarFlareStrength = solarFlareStrength;
    }

    public LocalDate getPeacefulSince() {
        if (galaxyType == GalaxyType.Dangerous)
            throw new RuntimeException("Dangerous galaxy doesn't have peaceful since attribute");
        return peacefulSince;
    }

    private void setPeacefulSince(LocalDate peacefulSince) {
        if (peacefulSince == null) throw new RuntimeException("Solar flare strength can't be set to null");
        this.peacefulSince = peacefulSince;
    }

    public GalaxyType getGalaxyType() {
        return galaxyType;
    }

    public void setPeaceful(LocalDate peacefulSince) {
        this.galaxyType = GalaxyType.Peaceful;
        atWar = null;
        solarFlareStrength = null;
        setPeacefulSince(peacefulSince);
    }

    public void setDangerous(Boolean atWar, Integer solarFlareStrength) {
        this.galaxyType = GalaxyType.Dangerous;
        setAtWar(atWar);
        setSolarFlareStrength(solarFlareStrength);
        peacefulSince = null;
    }


    public Set<Ship> getShips() {
        return Collections.unmodifiableSet(ships);
    }

    public void addShip(Ship ship) {
        if (ship == null) {
            throw new RuntimeException("Can not add null");
        }

        if (ships.contains(ship)) return;

        ships.add(ship);
        ship.setGalaxy(this);
    }

    public void removeShip(Ship ship) {
        if (!ships.contains(ship)) return;
        ships.remove(ship);
        ship.setGalaxy(null);
    }

    public Set<Cargo> getCargoSet() {
        List<Cargo> list = new ArrayList<>(cargoSet);
        Collections.sort(list);
        return new LinkedHashSet<>(list);
    }

    public void addCargo(Cargo cargo) {
        if (cargo == null) {
            throw new RuntimeException("Can not add null");
        }

        if (cargoSet.contains(cargo)) return;

        cargoSet.add(cargo);
        cargo.setDestination(this);
    }

    public void removeCargo(Cargo cargo) {
        if (!cargoSet.contains(cargo)) return;
        cargoSet.remove(cargo);
        cargo.setDestination(null);
    }


    public void galaxyCodeUniquenessCheck(String galaxyCode) {
        if (galaxyExtent.stream().anyMatch(galaxy -> galaxy.getGalaxyCode().equals(galaxyCode)))
            throw new RuntimeException("Galaxy code is already in use");
    }

}
