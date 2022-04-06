package it.app.anagrafica.service;

import it.app.anagrafica.dto.BusinessUserDTO;
import it.app.anagrafica.dto.BusinessUserFilterDTO;
import it.app.anagrafica.dto.BusinessUserPageDTO;
import it.app.anagrafica.entity.BusinessUser;
import it.app.anagrafica.repository.IBusinessUserEntityRepository;
import it.app.anagrafica.specification.BusinessUserSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BusinessUserService {

    @Autowired
    private IBusinessUserEntityRepository iBusinessUserRepository;

    public BusinessUserDTO createUser(BusinessUserDTO user) {
        BusinessUser entity = new BusinessUser();
        entity.setFirstName(user.getFirstName());
        entity.setFiscalCode(user.getFiscalCode());
        entity.setLastName(user.getLastName());
        iBusinessUserRepository.save(entity);
        user.setId(entity.getId());
        return user;
    }

    @Transactional
    public BusinessUserDTO updateUser(BusinessUserDTO user) {
        BusinessUser entity = iBusinessUserRepository.findById(user.getId()).get();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setFiscalCode(user.getFiscalCode());
        return user;
    }

    @Transactional
    public void deleteUser(Integer id) {
        BusinessUser entity = iBusinessUserRepository.getById(id);
        iBusinessUserRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public BusinessUserDTO findById(Integer id) {
        return iBusinessUserRepository.findUserById(id);
    }

    @Transactional(readOnly = true)
    public BusinessUserPageDTO findUsersByFilter(int start, int max, BusinessUserFilterDTO filter) {
        Specification<BusinessUser> spec = BusinessUserSpec.getUsersByFilter(filter);
        Page<BusinessUser> page = iBusinessUserRepository.findAll(spec, PageRequest.of(start-1,max));
        return makeDTO(page,start,max);
    }

    private BusinessUserPageDTO makeDTO(Page<BusinessUser> page,int start, int max){
        List<BusinessUserDTO> dtoList = new ArrayList<>();
        for(BusinessUser businessUser: page.getContent()){
            dtoList.add(new BusinessUserDTO(businessUser.getId(),businessUser.getFirstName(),
                    businessUser.getLastName(),businessUser.getFiscalCode()));
        }
        return new BusinessUserPageDTO(page.getTotalElements(),start,max, dtoList);
    }
}
