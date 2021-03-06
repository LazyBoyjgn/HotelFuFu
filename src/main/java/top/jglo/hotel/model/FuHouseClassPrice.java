package top.jglo.hotel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "fu_house_class_price", schema = "HotelFuFu", catalog = "")
public class FuHouseClassPrice implements Serializable {
    private int id;
    private int classId;
    private int price;
    private Integer weekCon;
    private String dayCon;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "class_id")
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "week_con")
    public Integer getWeekCon() {
        return weekCon;
    }

    public void setWeekCon(Integer weekCon) {
        this.weekCon = weekCon;
    }

    @Basic
    @Column(name = "day_con")
    public String getDayCon() {
        return dayCon;
    }

    public void setDayCon(String dayCon) {
        this.dayCon = dayCon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuHouseClassPrice that = (FuHouseClassPrice) o;
        return id == that.id &&
                classId == that.classId &&
                price == that.price &&
                Objects.equals(weekCon, that.weekCon) &&
                Objects.equals(dayCon, that.dayCon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classId, price, weekCon, dayCon);
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
