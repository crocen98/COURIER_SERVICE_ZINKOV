package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class CustomerReviews implements Identified<Integer>, Serializable {
        private Integer id;
        private Integer customerId;
        private Integer courierId;
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
    public String toString() {
        return "CustomerReviews{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", courierId=" + courierId +
                ", mark=" + mark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerReviews that = (CustomerReviews) o;
        return mark == that.mark &&
                Objects.equals(id, that.id) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(courierId, that.courierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, courierId, mark);
    }
}
