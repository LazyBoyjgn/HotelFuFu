package top.jglo.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.jglo.hotel.model.FuHouse;
import top.jglo.hotel.model.FuHouseOpen;

import java.util.List;


/**
 * @author jingening
 */
@Repository
public interface FuHouseOpenRepository extends JpaRepository<FuHouseOpen,Integer> { //id序列化,传入id的类型

    List<FuHouseOpen> findByStatus(int status);

    @Modifying
    @Query("UPDATE FuHouseOpen p set p.status=2 WHERE p.status=1 AND p.houseId=?1")
    void returnByHouse(int houseId);

    @Modifying
    @Query("UPDATE FuHouseOpen p set p.status=2 WHERE p.status=1 AND p.userId=?1")
    void returnByUser(int userId);
}