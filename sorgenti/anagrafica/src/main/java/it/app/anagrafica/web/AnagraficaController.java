package it.app.anagrafica.web;

import it.app.anagrafica.dto.BusinessUserDTO;
import it.app.anagrafica.dto.BusinessUserFilterDTO;
import it.app.anagrafica.dto.BusinessUserPageDTO;
import it.app.anagrafica.service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnagraficaController {

    @Autowired
    private BusinessUserService userService;

   @PostMapping(value="/user/create", consumes="application/json", produces="application/json")
   public ResponseEntity<BusinessUserDTO> create(@RequestBody BusinessUserDTO user) {
       return new ResponseEntity<BusinessUserDTO>(userService.createUser(user), HttpStatus.CREATED);
   }

   @PutMapping(value="/user/update", consumes="application/json", produces="application/json")
   public ResponseEntity<BusinessUserDTO> update(@RequestBody BusinessUserDTO user) {
       return new ResponseEntity<BusinessUserDTO>(userService.updateUser(user), HttpStatus.OK);
   }

   @DeleteMapping(value="/user/delete/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable Integer id) {
       userService.deleteUser(id);
   }

   @GetMapping(value = "/user/findById/{id}")
   public ResponseEntity<BusinessUserDTO> findById(@PathVariable Integer id) {
       BusinessUserDTO user = userService.findById(id);
       if(user!=null)
           return new ResponseEntity<BusinessUserDTO>(new BusinessUserDTO(user.getId(),user.getFirstName(),user.getLastName(),user.getFiscalCode()),HttpStatus.OK);
       else
           return new ResponseEntity<BusinessUserDTO>(new BusinessUserDTO(0,"","",""),HttpStatus.NOT_FOUND);
   }

   @GetMapping(value = "/user/findUsersByFilter/{start}/{max}")
   public ResponseEntity<BusinessUserPageDTO> findUsersByFilter(@PathVariable Integer start, @PathVariable Integer max,
                                                                 @RequestBody BusinessUserFilterDTO filter) {
        BusinessUserPageDTO page = userService.findUsersByFilter(start,max,filter);
        return makeResponse(page);
   }

   private ResponseEntity<BusinessUserPageDTO> makeResponse(BusinessUserPageDTO page){
       if(page.getTotal()>0)
            return new ResponseEntity<BusinessUserPageDTO>(page,HttpStatus.OK);
       else
            return new ResponseEntity<BusinessUserPageDTO>(new BusinessUserPageDTO(0,0,0,null),HttpStatus.NOT_FOUND);
   }
}
