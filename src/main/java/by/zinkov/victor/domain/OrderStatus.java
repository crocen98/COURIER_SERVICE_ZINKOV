package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

public enum OrderStatus implements Identified<Integer> {
    PERFORMED(1),
    ORDERED(2),
    READY(3),
    CANCELED(4);

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer object) {
        throw new UnsupportedOperationException();
    }


    OrderStatus(Integer id) {
        this.id = id;
    }
}
