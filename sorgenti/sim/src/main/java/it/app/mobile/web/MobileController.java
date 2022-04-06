package it.app.mobile.web;

import it.app.mobile.dto.ProductDTO;
import it.app.mobile.dto.SimDTO;
import it.app.mobile.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class MobileController {

    @Autowired
    private SimService simService;

    @PostMapping(value="/sim/create", consumes="application/json")
    public ResponseEntity<SimDTO> createSim(@RequestBody SimDTO sim) {
        return new ResponseEntity<SimDTO>(simService.createSim(sim), HttpStatus.CREATED);
    }

    @PutMapping(value="/sim/user/activate", consumes="application/json")
    public ResponseEntity<SimDTO> acquiredByUser(@RequestBody SimDTO sim) {
        return new ResponseEntity<SimDTO>(simService.acquiredByUser(sim.getId(), sim.getUserId()), HttpStatus.OK);
    }

    @PutMapping(value="/sim/activate/{productId}/{simId}")
    public ResponseEntity<SimDTO> activateProduct(@PathVariable Integer simId, @PathVariable  Integer productId) {
        return new ResponseEntity<SimDTO>(simService.activateProduct(simId, productId),HttpStatus.OK);
    }

    @PutMapping(value="/sim/activate/list/{simId}")
    public ResponseEntity<SimDTO> activateProductList(@RequestBody List<ProductDTO> product, @PathVariable Integer simId) {
        return new ResponseEntity<SimDTO>(simService.activateProductList(product,simId),HttpStatus.OK);
    }

    @PutMapping(value="/sim/deactivate/{productId}/{simId}")
    public ResponseEntity<SimDTO> deactivateProduct(@PathVariable Integer simId, @PathVariable  Integer productId) {
        return new ResponseEntity<SimDTO>(simService.deActivateProduct(simId, productId),HttpStatus.OK);
    }

    @GetMapping(value = "/sim/user/{userId}")
    public ResponseEntity<List<SimDTO>> findSimByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(simService.findSimByUser(userId),HttpStatus.OK);
    }

    @GetMapping(value = "/sim/free")
    public ResponseEntity<SimDTO> freeSim() {
        SimDTO simDTO = simService.findFreeSim();
        if(simDTO!=null)
            return new ResponseEntity<>(simDTO,HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
}
