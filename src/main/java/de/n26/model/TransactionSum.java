package de.n26.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionSum {

    @JsonProperty("sum")
    private Double sum;

    public TransactionSum(double sum) {
        this.sum = sum;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
