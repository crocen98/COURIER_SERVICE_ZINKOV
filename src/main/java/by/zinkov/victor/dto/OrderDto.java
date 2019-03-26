package by.zinkov.victor.dto;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class OrderDto implements Identified<Integer>, Serializable {
    private Integer id;

    private String courierLogin;
    private String courierFirstName;
    private String courierLastName;
    private String courierEmail;
    private String courierPhone;


    private BigDecimal price;
    private String status;
    private String startPoint;
    private String finishPoint;
    private String description;
    private Timestamp startTime;
    private Timestamp finishTime;
    private String transportType;
    private String cargoType;


    public String getCourierLogin() {
        return courierLogin;
    }

    public void setCourierLogin(String courierLogin) {
        this.courierLogin = courierLogin;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public void setCourierFirstName(String courierFirstName) {
        this.courierFirstName = courierFirstName;
    }

    public String getCourierLastName() {
        return courierLastName;
    }

    public void setCourierLastName(String courierLastName) {
        this.courierLastName = courierLastName;
    }

    public String getCourierEmail() {
        return courierEmail;
    }

    public void setCourierEmail(String courierEmail) {
        this.courierEmail = courierEmail;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(String finishPoint) {
        this.finishPoint = finishPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer object) {
        id = object;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", courierLogin='" + courierLogin + '\'' +
                ", courierFirstName='" + courierFirstName + '\'' +
                ", courierLastName='" + courierLastName + '\'' +
                ", courierEmail='" + courierEmail + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", finishPoint='" + finishPoint + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", transportType='" + transportType + '\'' +
                ", cargoType='" + cargoType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) &&
                Objects.equals(courierLogin, orderDto.courierLogin) &&
                Objects.equals(courierFirstName, orderDto.courierFirstName) &&
                Objects.equals(courierLastName, orderDto.courierLastName) &&
                Objects.equals(courierEmail, orderDto.courierEmail) &&
                Objects.equals(courierPhone, orderDto.courierPhone) &&
                Objects.equals(price, orderDto.price) &&
                Objects.equals(status, orderDto.status) &&
                Objects.equals(startPoint, orderDto.startPoint) &&
                Objects.equals(finishPoint, orderDto.finishPoint) &&
                Objects.equals(description, orderDto.description) &&
                Objects.equals(startTime, orderDto.startTime) &&
                Objects.equals(finishTime, orderDto.finishTime) &&
                Objects.equals(transportType, orderDto.transportType) &&
                Objects.equals(cargoType, orderDto.cargoType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courierLogin, courierFirstName, courierLastName, courierEmail, courierPhone, price, status, startPoint, finishPoint, description, startTime, finishTime, transportType, cargoType);
    }
}
