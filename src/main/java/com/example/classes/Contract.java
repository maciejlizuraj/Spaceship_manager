package com.example.classes;

import java.io.Serializable;

public class Contract extends ObjectPlus implements Serializable {
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
    }

    public static Contract constructor(String role, Double salary, Ship ship, CrewMember crewMember) {
        Util.validString(role);
        if (ship == null) throw new RuntimeException("Ship can't be null");
        if (crewMember == null) throw new RuntimeException("Crew member can't be null");
        validSalary(salary, crewMember);
        return new Contract(role, salary, ship, crewMember);
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
        validSalary(salary, getCrewMember());
        this.salary = salary;
    }

    public static void validSalary(Double salary, CrewMember crewMember) {
        if (crewMember instanceof MechanicalCrewMember && salary != null) {
            throw new RuntimeException("Mechanical crew member can't have a salary");
        } else if (crewMember instanceof OrganicCrewMember) {
            Util.nonNegativeDoubleCheck(salary);
        }
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
