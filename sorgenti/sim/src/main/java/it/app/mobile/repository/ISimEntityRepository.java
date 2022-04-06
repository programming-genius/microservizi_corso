package it.app.mobile.repository;

import it.app.mobile.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ISimEntityRepository extends JpaRepository<Sim, Integer> {

    @Transactional(readOnly = true)
    @Query("select c from Sim c left outer join fetch c.products where c.id=:simId")
    Sim findSimById(@Param("simId") Integer simId);

    @Transactional(readOnly = true)
    @Query("select c from Sim c left outer join fetch c.products where c.userId=:userId")
    List<Sim> findSimByUserId(@Param("userId") Integer userId);

    @Transactional(readOnly = true)
    Sim findTopByUserIdOrderByIdDesc(Integer userId);
}
