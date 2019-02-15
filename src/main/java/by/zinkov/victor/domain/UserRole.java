package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

public enum UserRole implements Identified<Integer> {
    ADMINISTRATOR(1),
    COURIER(2),
    CLIENT(3);
    private int id;

     UserRole(int id){
        this.id = id;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer object) {
        id = object;
    }
}
