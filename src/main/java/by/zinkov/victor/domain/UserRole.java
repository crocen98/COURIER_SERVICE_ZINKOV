package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

public enum UserRole implements Identified<Integer> {
    ADMINISTRATOR(1, "Administrator"),
    COURIER(2, "Courier"),
    CLIENT(3, "Client");
    private Integer id;
    private String role;

    UserRole(int id, String role) {
        this.id = id;
        this.role = role;

    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
