package com.example.bank.model;

import java.util.Objects;

public class TransferDTO {
    private String senderRequisite;
    private String recipientRequisite;
    double amount;

    public TransferDTO(String senderRequisite, String recipientRequisite, double amount) {
        this.senderRequisite = senderRequisite;
        this.recipientRequisite = recipientRequisite;
        this.amount = amount;
    }

    public String getSenderRequisite() {
        return senderRequisite;
    }

    public void setSenderRequisite(String senderRequisite) {
        this.senderRequisite = senderRequisite;
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
        TransferDTO that = (TransferDTO) o;
        return Double.compare(that.amount, amount) == 0
                && Objects.equals(senderRequisite, that.senderRequisite)
                && Objects.equals(recipientRequisite, that.recipientRequisite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderRequisite, recipientRequisite, amount);
    }
}
