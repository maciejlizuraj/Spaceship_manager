package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class representing a crew member.
 * Extends ObjectPlus and implements Serializable.
 */
public abstract class CrewMember extends ObjectPlus implements Serializable {

    private Set<Contract> contracts;

    /**
     * Constructs a CrewMember object initializing contracts set.
     */
    protected CrewMember() {
        contracts = new HashSet<>();
    }

    /**
     * Retrieves the unmodifiable set of contracts associated with this crew member.
     *
     * @return The set of contracts.
     */
    public Set<Contract> getContracts() {
        return Collections.unmodifiableSet(contracts);
    }

    /**
     * Adds a contract to the crew member's list of contracts.
     *
     * @param contract The contract to add.
     * @throws RuntimeException If contract is null, is not for this crew member or already exists.
     */
    public void addContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");

        if (contracts.contains(contract)) return;
        if (contract.getCrewMember() != this)
            throw new RuntimeException("This contract is not for this crew member");
        for (Contract contr : contracts) {
            if (contr.getCrewMember() == contract.getCrewMember()) {
                throw new RuntimeException("Contract between this crew member and ship already exists");
            }
        }
        contracts.add(contract);
    }

    /**
     * Removes a contract from the crew member's list of contracts.
     *
     * @param contract The contract to remove.
     * @throws RuntimeException If contract is null.
     */
    public void removeContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");

        if (!contracts.contains(contract)) return;

        contracts.remove(contract);
        contract.getShip().removeContract(contract);
        contract.setToNulls();
    }

    /**
     * Abstract method to be implemented by subclasses to check if the crew member can join a specific ship.
     *
     * @param ship The ship to check.
     * @return True if the crew member can join the ship, false otherwise.
     */
    public abstract boolean canJoin(Ship ship);
}
