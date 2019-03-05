package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Order implements Identified<Integer>, Serializable {
    private Integer id;
    private Integer idCourier;
    private Integer idCustomer;
    private BigDecimal price;
    private Integer idStatus;
    private String startPoint;
    private String finishPoint;
    private String description;
    private Timestamp startTime;
    private Timestamp finishTime;
    private Timestamp expectedTime;
    private Integer idTransportType;
    private Integer idCargoType;


    public Integer getIdTransportType() {
        return idTransportType;
    }

    public void setIdTransportType(Integer idTransportType) {
        this.idTransportType = idTransportType;
    }

    public Integer getIdCargoType() {
        return idCargoType;
    }

    public void setIdCargoType(Integer idCargoType) {
        this.idCargoType = idCargoType;
    }

    public void setIdCourier(Integer idCourier) {
        this.idCourier = idCourier;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdCourier() {
        return idCourier;
    }

    public void setIdCourier(int idCourier) {
        this.idCourier = idCourier;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
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

    public Timestamp getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Timestamp expectedTime) {
        this.expectedTime = expectedTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idCourier=" + idCourier +
                ", idCustomer=" + idCustomer +
                ", price=" + price +
                ", idStatus=" + idStatus +
                ", startPoint='" + startPoint + '\'' +
                ", finishPoint='" + finishPoint + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", expectedTime=" + expectedTime +
                ", idTransportType=" + idTransportType +
                ", idCargoType=" + idCargoType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(idCourier, order.idCourier) &&
                Objects.equals(idCustomer, order.idCustomer) &&
                Objects.equals(price, order.price) &&
                Objects.equals(idStatus, order.idStatus) &&
                Objects.equals(startPoint, order.startPoint) &&
                Objects.equals(finishPoint, order.finishPoint) &&
                Objects.equals(description, order.description) &&
                Objects.equals(startTime, order.startTime) &&
                Objects.equals(finishTime, order.finishTime) &&
                Objects.equals(expectedTime, order.expectedTime) &&
                Objects.equals(idTransportType, order.idTransportType) &&
                Objects.equals(idCargoType, order.idCargoType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCourier, idCustomer, price, idStatus, startPoint, finishPoint, description, startTime, finishTime, expectedTime, idTransportType, idCargoType);
    }
}
