package com.example.bank.model;

import java.util.Objects;

public class DepositDTO {
    private String recipientRequisite;
    private double amount;

    public DepositDTO(String recipientRequisite, double amount) {
        this.recipientRequisite = recipientRequisite;
        this.amount = amount;
    }

    public String getRecipientRequisite() {
        return recipientRequisite;
    }

    public void setRecipientRequisite(String recipientRequisite) {
        this.recipientRequisite = recipientRequisite;
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
        DepositDTO that = (DepositDTO) o;
        return Double.compare(that.amount, amount) == 0
                && Objects.equals(recipientRequisite, that.recipientRequisite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientRequisite, amount);
    }
}
