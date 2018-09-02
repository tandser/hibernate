package ru.tandser.hibernate.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Amount implements Serializable {

    private BigDecimal amount;
    private Currency currency;

    public Amount(BigDecimal amount, Currency currency) {
        this.amount   = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Amount that = (Amount) obj;

        return Objects.equals(this.amount, that.amount) &&
               Objects.equals(this.currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount.intValue() + " " + currency.getCurrencyCode();
    }

    public static Amount valueOf(String amount) {
        String[] split = amount.split(" ");

        return new Amount(new BigDecimal(split[0]), Currency.getInstance(split[1]));
    }

    /* Setters and Getters */

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}