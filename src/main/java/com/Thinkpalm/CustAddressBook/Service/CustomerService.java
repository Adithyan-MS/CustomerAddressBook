package com.Thinkpalm.CustAddressBook.Service;

import com.Thinkpalm.CustAddressBook.Model.Address;
import com.Thinkpalm.CustAddressBook.Model.Customer;
import com.Thinkpalm.CustAddressBook.Repository.AddressRepository;
import com.Thinkpalm.CustAddressBook.Repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CustomerService {
    @Value("${spring.servlet.multipart.location}")
    private String filePath;
    private final CustomerRepository custRep;
    private final AddressRepository addRep;

    @Autowired
    public CustomerService(CustomerRepository custRep, AddressRepository addRep){
        this.custRep = custRep;
        this.addRep = addRep;
    }

    public String uploadCsvFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            String csvFileName = file.getOriginalFilename();
            Path path = Paths.get(filePath,csvFileName);
            Files.write(path,file.getBytes());

            try(CSVReader reader = new CSVReader(new FileReader(path.toString()))){
                String[] lines;
                Boolean isFirstLine = true;
                List<String> attributes = new ArrayList<>();
                while((lines = reader.readNext())!=null){

                    if(isFirstLine){
                        for(int i=0;i<12;i++){
                            attributes.add(lines[i]);
                        }
                        isFirstLine=false;
                        continue;
                    }

                    Map<String,String>  data= new HashMap<>();

                    for(int i = 0; i < 12; i++){
                        data.put(attributes.get(i),lines[i]);
                    }

                    String uuid = UUID.randomUUID().toString();

                    Customer cust = new Customer();
                    cust.setCust_uuid(uuid);
                    cust.setCode(data.get("cust_code"));
                    cust.setName(data.get("cust_name"));
                    custRep.save(cust);


                    for(int i = 1;i <= 2;i++){
                        Address add = new Address();
                        add.setCust_uuid(uuid);
                        add.setAddress(data.get("address"+i));
                        add.setCity(data.get("city"+i));
                        add.setState(data.get("state"+i));
                        add.setCountry(data.get("country"+i));
                        add.setPincode(data.get("pincode"+i));
                        addRep.save(add);
                    }

                }
                addRep.updateAddress();

            }catch (IOException e){
                e.printStackTrace();
            }
            return "Datas inserted to Database:)";
        }else{
            return "upload error";
        }
    }

    public String updateAddTable(){
        addRep.updateAddress();
        return "updated";
    }

    public Map<String,Object> getAddressData(Integer addId)throws JsonProcessingException {
        List<Map<String,Object>> dataMap = addRep.getAddressData(addId);
        Map<String,Object> response = new HashMap<>();
        response.put("message","Address data is here.");
        response.put("HttpStatus", HttpStatus.OK);
        response.put("data",dataMap);
        return response;
    }

    public Map<String,Object> getCustomerAddress(String custCode){
        List<Map<String,Object>> custAddressData = addRep.getCustomerAddress(custCode);
        Map<String,Object> custAddressResponse = new HashMap<>();
        custAddressResponse.put("message","Customer address details is here.");
        custAddressResponse.put("HttpStatus",HttpStatus.OK);
        custAddressResponse.put("data",custAddressData);
        return custAddressResponse;
    }

}
