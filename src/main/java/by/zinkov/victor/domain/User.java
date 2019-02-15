package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class User implements Identified<Integer>, Serializable {
    private int id;
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;
    private String location;

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                userRole == user.userRole &&
                userStatus == user.userStatus &&
                Objects.equals(location, user.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstName, lastName, email, phone, userRole, userStatus, location);
    }
}
