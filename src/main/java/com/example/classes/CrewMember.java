package com.example.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class CrewMember extends ObjectPlus implements Serializable {

    private Set<Contract> contracts;

    protected CrewMember() {
        contracts = new HashSet<>();
    }

    public Set<Contract> getContracts() {
        return Collections.unmodifiableSet(contracts);
    }

    public void addContract(Contract contract) {
        if (contract == null) throw new RuntimeException("Contract can not be null");

        if (contracts.contains(contract)) return;
        if (contract.getCrewMember() != this)
            throw new IllegalArgumentException("This contract is not for this crew member");
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
        contract.getShip().removeContract(contract);
        contract.setToNulls();
    }

    public abstract boolean canJoin(Ship ship);
}
