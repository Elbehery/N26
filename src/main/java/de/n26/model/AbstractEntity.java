package de.n26.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        return this.id != null && that.id != null && this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
