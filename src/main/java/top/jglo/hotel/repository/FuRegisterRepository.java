package top.jglo.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.jglo.hotel.model.FuRegister;
import top.jglo.hotel.model.FuRole;
import top.jglo.hotel.model.result.ChartInfo;

import java.util.List;


/**
 * @author jingening
 */
@Repository
public interface FuRegisterRepository extends JpaRepository<FuRegister,Integer> { //id序列化,传入id的类型

    List<FuRegister> findByStartDateAndHotelId(String startDate,int hotelId);

    @Query(value = "SELECT r FROM FuRegister r " +
            "WHERE (r.hotelId=?1 or ?1 is null or ?1 ='') "+
            "and (r.houseClassId=?2 or ?2 is null or ?2 ='') " +
            "and (r.status=?3 or ?3 is null or ?3 ='') " +
            "and (r.commitTime>=?4 or ?4 is null or ?4 ='') " +
            "and (r.commitTime<=?5 or ?5 is null or ?5 ='') " +
            "and (r.startDate<=?6 or ?6 is null or ?6 ='') " +
            "and (r.endDate>=?6 or ?6 is null or ?6 ='') ")
    List<FuRegister> findByFindInfo(int hotelId, int houseClassId, int status, String commitStartTime, String commitEndTime, String liveDate);

    @Query(value = "select COUNT(r) FROM FuRegister r where r.houseClassId=?2 and r.startDate <=?1 and r.endDate>?1 and r.status<2")
    int countByStartDateAndHouseClassId(String date,int houseClassId);

    List<FuRegister> findByUserId(int userId);

    @Query(value = "select r FROM FuRegister r where r.userId=?2 and r.startDate <=?1 and r.endDate>?1 and r.status<2")
    FuRegister findByStartDateAndUserId(String date,int userId);

    @Query(nativeQuery = true,value =
            "SELECT sum(price) FROM fu_house_class_price p1 ,  " +
                    "(SELECT p.class_id as cid,r.id,max(p.`status`) as s,dada.product_date FROM fu_house_class_price p ,fu_register r ,(SELECT  " +
                    "@num\\:=@num+1 num,  " +
                    "DATE_ADD(DATE_FORMAT(?1, '%Y-%m-%d'),INTERVAL @num DAY) as product_date  " +
                    "FROM  " +
                    "(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) xc1,  " +
                    "(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) xc2,  " +
                    "(SELECT 1 UNION SELECT 2 ) xc3,  " +
                    "(select @num\\:=-1) num_t  " +
                    "WHERE  " +
                    "@num < (SELECT DAYOFMONTH(LAST_DAY(?1)) - 1)) as dada  " +
                    "WHERE p.class_id=r.house_class_id   " +
                    "AND (p.week_con=WEEKDAY(dada.product_date) or p.day_con=dada.product_date or p.`status`=0)  " +
                    "AND r.start_time<dada.product_date   " +
                    "AND r.end_time>=dada.product_date   " +
                    "AND r.hotel_id=?2   " +
                    "AND r.`status`=1   " +
                    "GROUP BY r.id ,product_date) da1  " +
                    "WHERE p1.`status`=da1.s AND p1.class_id=da1.cid ")
    Integer findMonSales(String date,int hotelId);



    @Query(nativeQuery = true,value =
            "SELECT c.`name` as `key`," +
                    "CAST(COUNT(1)as char) as `value` " +
                    "    FROM fu_house_class c,fu_register r " +
                    "    WHERE r.house_class_id=c.id " +
                    "    AND r.hotel_id=?1 " +
                    "    GROUP BY c.id")
    List<Object[]> findHouseSales(int hotelId);

    @Query(nativeQuery = true,value =
            "SELECT product_date,CAST(sum(price)as char) FROM fu_house_class_price p1 , " +
                    "(SELECT p.class_id as cid,r.id,max(p.`status`) as s,dada.product_date FROM fu_house_class_price p ,fu_register r ,(SELECT " +
                    "@num\\:=@num-1 num, " +
                    "DATE_ADD(DATE_FORMAT(  ?1, '%Y-%m-%d'),INTERVAL @num DAY) as product_date " +
                    "FROM " +
                    "(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7) xc1, " +
                    "(select @num\\:=1) num_t " +
                    ") as dada  " +
                    "WHERE p.class_id=r.house_class_id  " +
                    "AND (p.week_con=WEEKDAY(dada.product_date) or p.day_con=dada.product_date or p.`status`=0) " +
                    "AND r.start_time<dada.product_date  " +
                    "AND r.end_time>=dada.product_date  " +
                    "AND r.hotel_id=?2  " +
                    "AND r.`status`=1 " +
                    "GROUP BY r.id ,product_date) da1 " +
                    "WHERE p1.`status`=da1.s AND p1.class_id=da1.cid  " +
                    "GROUP BY product_date")
    List<Object[]> findWeekSales(String date,int hotelId);

}