package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class SupportedCargoTypes implements Identified<Integer>, Serializable {
    private int id;
    private int currierCapabilityId;
    private int typeId;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public int getCurrierCapabilityId() {
        return currierCapabilityId;
    }

    public void setCurrierCapabilityId(int currierCapabilityId) {
        this.currierCapabilityId = currierCapabilityId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupportedCargoTypes that = (SupportedCargoTypes) o;
        return id == that.id &&
                currierCapabilityId == that.currierCapabilityId &&
                typeId == that.typeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currierCapabilityId, typeId);
    }
}
