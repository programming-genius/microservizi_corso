package it.app.mobile.service;

import it.app.mobile.dto.ProductDTO;
import it.app.mobile.dto.SimDTO;
import it.app.mobile.entity.Product;
import it.app.mobile.entity.Sim;
import it.app.mobile.repository.IProductEntityRepository;
import it.app.mobile.repository.ISimEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SimService {

    @Autowired
    private ISimEntityRepository iSimRepository;
    @Autowired
    private IProductEntityRepository iProductRepository;

    public SimDTO createSim(SimDTO simDTO) {
        Sim simEntity = new Sim();
        simEntity.setImsi(simDTO.getImsi());
        simEntity.setMsisdn(simDTO.getMsisdn());
        iSimRepository.save(simEntity);
        return new SimDTO(simEntity.getId(),simEntity.getMsisdn(),
                simEntity.getImsi(),simEntity.getUserId());
    }

    public SimDTO acquiredByUser(Integer simId, Integer userId) {
        Sim simEntity = iSimRepository.findSimById(simId);
        simEntity.setUserId(userId);
        iSimRepository.save(simEntity);
        return new SimDTO(simEntity.getId(),simEntity.getMsisdn(),
                simEntity.getImsi(),simEntity.getUserId());
    }

    public SimDTO activateProduct(Integer simId, Integer productId) {
        Product productEntity = iProductRepository.getById(productId);
        Sim simEntity = iSimRepository.findSimById(simId);
        simEntity.getProducts().add(productEntity);
        List<ProductDTO> productDTO = new ArrayList<>();
        for(Product product: simEntity.getProducts()){
            productDTO.add(new ProductDTO(product.getId(),product.getName()));
        }
        SimDTO simDTO = new SimDTO(simEntity.getId(),simEntity.getMsisdn(), simEntity.getImsi(),
                simEntity.getUserId());
        simDTO.setProduct(productDTO);
        return simDTO;
    }

    public SimDTO activateProductList(List<ProductDTO> product, Integer simId) {
        Sim simEntity = iSimRepository.findSimById(simId);
        for(ProductDTO productDTO: product){
            Product productEntity = iProductRepository.getById(productDTO.getId());
            simEntity.getProducts().add(productEntity);
        }
        SimDTO simDTO = new SimDTO(simEntity.getId(),simEntity.getMsisdn(), simEntity.getImsi(),
                simEntity.getUserId());
        simDTO.setProduct(product);
        return simDTO;
    }

    public SimDTO deActivateProduct(Integer simId, Integer productId) {
        Product productEntity = iProductRepository.getById(productId);
        Sim simEntity = iSimRepository.findSimById(simId);
        simEntity.getProducts().remove(productEntity);
        List<ProductDTO> productDTO = new ArrayList<>();
        for(Product product: simEntity.getProducts()){
            productDTO.add(new ProductDTO(product.getId(),product.getName()));
        }
        SimDTO simDTO = new SimDTO(simEntity.getId(),simEntity.getMsisdn(), simEntity.getImsi(),
                simEntity.getUserId());
        simDTO.setProduct(productDTO);
        return simDTO;
    }

    @Transactional(readOnly = true)
    public List<SimDTO> findSimByUser(Integer userId) {
        List<Sim> simResult = iSimRepository.findSimByUserId(userId);
        List<SimDTO> simDTOs = new ArrayList<>();
        for(Sim sim: simResult){
            SimDTO simDTO = new SimDTO(sim.getId(),sim.getMsisdn(),sim.getImsi(), sim.getUserId());
            List<ProductDTO> productDTOs = new ArrayList<>();
            for(Product product :sim.getProducts()){
                productDTOs.add(new ProductDTO(product.getId(),product.getName()));
            }
            simDTO.setProduct(productDTOs);
            simDTOs.add(simDTO);
        }
        return simDTOs;
    }

    @Transactional(readOnly = true)
    public SimDTO findFreeSim() {
        Sim sim = iSimRepository.findTopByUserIdOrderByIdDesc(null);
        if(sim!=null)
            return new SimDTO(sim.getId(), sim.getMsisdn(), sim.getImsi(), sim.getUserId());
        return null;
    }
}
