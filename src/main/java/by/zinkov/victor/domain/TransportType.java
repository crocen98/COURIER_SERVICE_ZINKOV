package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class TransportType implements Identified<Integer>, Serializable {
    private Integer id;
    private String transportType;

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return "TransportType{" +
                "id=" + id +
                ", transportType='" + transportType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportType that = (TransportType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transportType, that.transportType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transportType);
    }
}
