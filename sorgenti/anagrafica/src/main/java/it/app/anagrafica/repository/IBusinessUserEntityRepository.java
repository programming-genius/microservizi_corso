package it.app.anagrafica.repository;

import it.app.anagrafica.dto.BusinessUserDTO;
import it.app.anagrafica.entity.BusinessUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IBusinessUserEntityRepository extends JpaRepository<BusinessUser, Integer>, JpaSpecificationExecutor<BusinessUser> {

    @Transactional(readOnly = true)
    @Query("select new it.app.anagrafica.dto.BusinessUserDTO(c.id,c.firstName,c.lastName,c.fiscalCode) from BusinessUser c where c.id=:id")
    BusinessUserDTO findUserById(@Param("id") Integer id);

    @Transactional(readOnly = true)
    Page<BusinessUser> findAll(Specification<BusinessUser> spec, Pageable pageable);

}
