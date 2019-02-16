package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class CurrierCapability  implements Identified<Integer>, Serializable {
    private Integer id;
    private int currierId;
    private int transportId;
    private boolean isWork;


    @Override
    public Integer getId() {
        return id;
    }



    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    public int getCurrierId() {
        return currierId;
    }

    public void setCurrierId(int currierId) {
        this.currierId = currierId;
    }

    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }


    @Override
    public String toString() {
        return "CurrierCapability{" +
                "id=" + id +
                ", currierId=" + currierId +
                ", transportId=" + transportId +
                ", isWork=" + isWork +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrierCapability that = (CurrierCapability) o;
        return id == that.id &&
                currierId == that.currierId &&
                transportId == that.transportId &&
                isWork == that.isWork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currierId, transportId, isWork);
    }
}
