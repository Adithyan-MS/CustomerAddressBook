package com.Thinkpalm.CustAddressBook.Controller;

import com.Thinkpalm.CustAddressBook.Service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/custApi/v1")
public class CustomerController {

    private final CustomerService custSer;

    @Autowired
    public CustomerController(CustomerService custSer){
        this.custSer = custSer;
    }

    @PostMapping("/createAddressBook")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        String res = custSer.uploadCsvFile(file);
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAddTable(){
        String res = custSer.updateAddTable();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/getAddressData/{addId}")
    public ResponseEntity<Map> getAddressData(@PathVariable Integer addId)throws JsonProcessingException {
        Map<String,Object> res = custSer.getAddressData(addId);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/getCustomerAddress/{custCode}")
    public ResponseEntity<Map> getCustomerAddress(@PathVariable String custCode)throws JsonProcessingException {
        Map<String,Object> res = custSer.getCustomerAddress(custCode);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
