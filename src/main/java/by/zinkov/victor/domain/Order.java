package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private Date startTime;
    private Date finishTime;
    private Date expectedTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idCourier == order.idCourier &&
                idCustomer == order.idCustomer &&
                idStatus == order.idStatus &&
                Objects.equals(id, order.id) &&
                Objects.equals(price, order.price) &&
                Objects.equals(startPoint, order.startPoint) &&
                Objects.equals(finishPoint, order.finishPoint) &&
                Objects.equals(description, order.description) &&
                Objects.equals(startTime, order.startTime) &&
                Objects.equals(finishTime, order.finishTime) &&
                Objects.equals(expectedTime, order.expectedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCourier, idCustomer, price, idStatus, startPoint, finishPoint, description, startTime, finishTime, expectedTime);
    }
}
