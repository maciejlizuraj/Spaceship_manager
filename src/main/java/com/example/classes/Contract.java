package com.example.classes;

import java.io.Serializable;
/**
 * Represents a contract between a crew member and a ship.
 * Extends ObjectPlus and implements Serializable.
 */
public class Contract extends ObjectPlus implements Serializable {
    private String role;
    private Double salary;
    private Ship ship;
    private CrewMember crewMember;

    /**
     * Constructs a Contract object with the specified role, salary, ship, and crew member.
     * @param role The role of the crew member in the contract.
     * @param salary The salary associated with the contract.
     * @param ship The ship to which the contract applies.
     * @param crewMember The crew member involved in the contract.
     */
    private Contract(String role, Double salary, Ship ship, CrewMember crewMember) {
        setRole(role);
        setSalary(salary);
        setShip(ship);
        setCrewMember(crewMember);
        ship.addContract(this);
        crewMember.addContract(this);
    }
    /**
     * Static factory method to create a Contract object with validations.
     * @param role The role of the crew member in the contract.
     * @param salary The salary associated with the contract.
     * @param ship The ship to which the contract applies.
     * @param crewMember The crew member involved in the contract.
     * @return A new Contract object.
     * @throws RuntimeException If validations fail (e.g., null ship or crew member, invalid salary for crew type).
     */
    public static Contract constructor(String role, Double salary, Ship ship, CrewMember crewMember) {
        Util.validString(role);
        if (ship == null) throw new RuntimeException("Ship can't be null");
        if (crewMember == null) throw new RuntimeException("Crew member can't be null");
        validSalary(salary, crewMember);
        return new Contract(role, salary, ship, crewMember);
    }
    /**
     * Sets ship and crew member references to null.
     */
    public void setToNulls() {
        ship = null;
        crewMember = null;
    }
    /**
     * Retrieves the role of the crew member in the contract.
     * @return The role of the crew member.
     */
    public String getRole() {
        return role;
    }
    /**
     * Sets the role of the crew member in the contract.
     * @param role The role of the crew member.
     * @throws RuntimeException If role is null or empty.
     */
    public void setRole(String role) {
        Util.validString(role);
        this.role = role;
    }
    /**
     * Retrieves the salary associated with the contract.
     * @return The salary.
     */
    public Double getSalary() {
        return salary;
    }
    /**
     * Sets the salary associated with the contract.
     * @param salary The salary to set.
     * @throws RuntimeException If salary is negative or if it violates specific rules based on crew member type.
     */
    public void setSalary(Double salary) {
        validSalary(salary, getCrewMember());
        this.salary = salary;
    }
    /**
     * Validates the salary based on the type of crew member.
     * @param salary The salary to validate.
     * @param crewMember The crew member for which the salary is validated.
     * @throws RuntimeException If the salary is invalid for the crew member type.
     */
    public static void validSalary(Double salary, CrewMember crewMember) {
        if (crewMember instanceof MechanicalCrewMember && salary != null) {
            throw new RuntimeException("Mechanical crew member can't have a salary");
        } else if (crewMember instanceof OrganicCrewMember) {
            Util.nonNegativeDoubleCheck(salary);
        }
    }
    /**
     * Retrieves the ship associated with the contract.
     * @return The ship.
     */
    public Ship getShip() {
        return ship;
    }
    /**
     * Sets the ship associated with the contract.
     * @param ship The ship to set.
     * @throws RuntimeException If ship is null.
     */
    private void setShip(Ship ship) {
        if (ship == null) throw new RuntimeException("Ship can not be null");
        this.ship = ship;
    }
    /**
     * Retrieves the crew member associated with the contract.
     * @return The crew member.
     */
    public CrewMember getCrewMember() {
        return crewMember;
    }

    /**
     * Sets the crew member associated with the contract.
     * @param crewMember The crew member to set.
     * @throws RuntimeException If crew member is null.
     */
    private void setCrewMember(CrewMember crewMember) {
        if (crewMember == null) throw new RuntimeException("Crew member can not be null");
        this.crewMember = crewMember;
    }


}
