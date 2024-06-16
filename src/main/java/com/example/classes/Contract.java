package com.example.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Contract {
    private static Set<Contract> contractExtent = new HashSet<>();

    private String role;
    private Double salary;
    private Ship ship;
    private CrewMember crewMember;


    public Contract(String role, Double salary, Ship ship, CrewMember crewMember) {
        setRole(role);
        setSalary(salary);
        setShip(ship);
        setCrewMember(crewMember);
        ship.addContract(this);
        crewMember.addContract(this);
        contractExtent.add(this);
    }

    public static Set<Contract> getContractExtent() {
        return Collections.unmodifiableSet(contractExtent);
    }

    public static void resetExtent() {
        contractExtent = new HashSet<>();
    }

    public void setToNulls() {
        ship = null;
        crewMember = null;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        Util.validString(role);
        this.role = role;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        if (crewMember instanceof MechanicalCrewMember && salary != null) {
            throw new RuntimeException("Mechanical crew member can't have a salary");
        } else if (crewMember instanceof OrganicCrewMember) {
            Util.nonNegativeDoubleCheck(salary);
        }

        this.salary = salary;
    }

    public Ship getShip() {
        return ship;
    }

    private void setShip(Ship ship) {
        if (ship == null) throw new RuntimeException("Ship can not be null");
        this.ship = ship;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }


    private void setCrewMember(CrewMember crewMember) {
        if (crewMember == null) throw new RuntimeException("Crew member can not be null");
        this.crewMember = crewMember;
    }


}
