package by.zinkov.victor.domain;

import by.zinkov.victor.dao.Identified;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Order implements Identified<Integer>, Serializable {
    private Integer id;
    private int idCourier;
    private int idCustomer;
    private BigDecimal price;
    private int id_status;
    private String start_point;
    private String finish_point;
    private String description;
    private Date start_time;
    private Date finish_time;
    private Date expected_time;
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



    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getStart_point() {
        return start_point;
    }

    public void setStart_point(String start_point) {
        this.start_point = start_point;
    }

    public String getFinish_point() {
        return finish_point;
    }

    public void setFinish_point(String finish_point) {
        this.finish_point = finish_point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public Date getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(Date expected_time) {
        this.expected_time = expected_time;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idCourier=" + idCourier +
                ", idCustomer=" + idCustomer +
                ", price=" + price +
                ", id_status=" + id_status +
                ", start_point='" + start_point + '\'' +
                ", finish_point='" + finish_point + '\'' +
                ", description='" + description + '\'' +
                ", start_time=" + start_time +
                ", finish_time=" + finish_time +
                ", expected_time=" + expected_time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idCourier == order.idCourier &&
                idCustomer == order.idCustomer &&
                id_status == order.id_status &&
                Objects.equals(id, order.id) &&
                Objects.equals(price, order.price) &&
                Objects.equals(start_point, order.start_point) &&
                Objects.equals(finish_point, order.finish_point) &&
                Objects.equals(description, order.description) &&
                Objects.equals(start_time, order.start_time) &&
                Objects.equals(finish_time, order.finish_time) &&
                Objects.equals(expected_time, order.expected_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCourier, idCustomer, price, id_status, start_point, finish_point, description, start_time, finish_time, expected_time);
    }
}
