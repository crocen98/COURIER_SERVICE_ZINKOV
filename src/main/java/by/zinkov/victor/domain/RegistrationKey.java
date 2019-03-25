package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationKey implements Identified<Integer>, Serializable {
    private String key;
    private Integer userId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public void setId(Integer id) {
        this.userId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationKey that = (RegistrationKey) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, userId);
    }

    @Override
    public String toString() {
        return "RegistrationKey{" +
                "key='" + key + '\'' +
                ", userId=" + userId +
                '}';
    }
}
