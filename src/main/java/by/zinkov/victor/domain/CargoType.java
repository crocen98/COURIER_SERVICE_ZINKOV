package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class CargoType implements Identified<Integer>, Serializable {
    private Integer id;
    private String cargoType;



    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoType cargoType1 = (CargoType) o;
        return Objects.equals(id, cargoType1.id) &&
                Objects.equals(cargoType, cargoType1.cargoType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cargoType);
    }

    @Override
    public String toString() {
        return "CargoType{" +
                "id=" + id +
                ", cargoType='" + cargoType + '\'' +
                '}';
    }
}