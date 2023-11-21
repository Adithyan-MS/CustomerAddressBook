package com.Thinkpalm.CustAddressBook.Repository;


import com.Thinkpalm.CustAddressBook.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
