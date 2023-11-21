package com.Thinkpalm.CustAddressBook.Repository;

import com.Thinkpalm.CustAddressBook.Model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    @Transactional
    @Modifying
    @Query(value="UPDATE address AS a JOIN customer_Data AS cust ON a.cust_uuid = cust.cust_uuid SET a.cust_id = cust.id WHERE a.cust_id IS NULL", nativeQuery = true)
    void updateAddress();

    @Query(value="select address,state,city from address where id = ?1", nativeQuery = true)
    List<Map<String,Object>> getAddressData(Integer addId);

    @Query(value = "select c.cust_name,a.address,a.state,a.city from customer_data as c inner join address as a on c.id=a.cust_id where c.cust_code = ?1",nativeQuery = true)
    List<Map<String,Object>> getCustomerAddress(String custCode);

}
