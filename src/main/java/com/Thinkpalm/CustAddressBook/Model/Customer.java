package com.Thinkpalm.CustAddressBook.Model;

import jakarta.persistence.*;
import org.hibernate.generator.internal.GeneratedGeneration;

@Entity
@Table(name = "customerData")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String cust_uuid;

    @Column(name = "cust_code")
    private String code;

    @Column(name = "cust_name")
    private String name;

    public void setId(Integer id){
        this.id = id;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getId(){
        return id;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public String getCust_uuid() {
        return cust_uuid;
    }

    public void setCust_uuid(String cust_uuid) {
        this.cust_uuid = cust_uuid;
    }
}
