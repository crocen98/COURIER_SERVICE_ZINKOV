package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

public enum UserStatus implements Identified<Integer> {

    BLOCKED(1),
    ACTIVE(2),
    WAITING_CONFIRMATION(3);

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer object) {
        throw new UnsupportedOperationException();
    }

    UserStatus(int id){
        this.id = id;
    }
}
