package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class CustomerReviews implements Identified<Integer>, Serializable {
        private int id;
        private int customerId;
        private int courierId;
        private byte mark;

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public byte getMark() {
        return mark;
    }

    public void setMark(byte mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerReviews that = (CustomerReviews) o;
        return id == that.id &&
                customerId == that.customerId &&
                courierId == that.courierId &&
                mark == that.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, courierId, mark);
    }
}
