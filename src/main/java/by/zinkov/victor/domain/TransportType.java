package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TransportType implements Identified<Integer>, Serializable {
    private Integer id;
    private String transportType;
    private BigDecimal coefficient;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportType that = (TransportType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transportType, that.transportType) &&
                Objects.equals(coefficient, that.coefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transportType, coefficient);
    }

    @Override
    public String toString() {
        return "TransportType{" +
                "id=" + id +
                ", transportType='" + transportType + '\'' +
                ", coefficient=" + coefficient +
                '}';
    }
}
