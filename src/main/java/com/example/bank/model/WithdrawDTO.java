package com.example.bank.model;

import java.util.Objects;

public class WithdrawDTO {
    private String ownerRequisite;
    private double amount;

    public WithdrawDTO(String ownerRequisite, double amount) {
        this.ownerRequisite = ownerRequisite;
        this.amount = amount;
    }

    public String getOwnerRequisite() {
        return ownerRequisite;
    }

    public void setOwnerRequisite(String ownerRequisite) {
        this.ownerRequisite = ownerRequisite;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WithdrawDTO that = (WithdrawDTO) o;
        return Double.compare(that.amount, amount) == 0
                && Objects.equals(ownerRequisite, that.ownerRequisite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerRequisite, amount);
    }
}
