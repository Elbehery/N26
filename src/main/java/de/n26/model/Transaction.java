package de.n26.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction extends AbstractEntity {

    private String type;
    private Double amount;
    private Long parent_id;


    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        if (!super.equals(o))
            return false;

        Transaction that = (Transaction) o;

        return this.getType().equals(that.getType()) &&
                this.getAmount().equals(that.getAmount()) &&
                this.parent_id != null ? this.parent_id.equals(that.parent_id) : that.parent_id == null;
    }
}
