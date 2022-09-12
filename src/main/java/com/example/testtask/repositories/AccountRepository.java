package com.example.testtask.repositories;

import com.example.testtask.models.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    @Query("SELECT a FROM Account AS a WHERE a.phoneNumber = :phone AND a.password = :password")
    Account findByNameAndPhone(@Param("phone") String phone, @Param("password") String password);

    @Query("SELECT a FROM Account AS a WHERE a.phoneNumber = :phone")
    Account findByPhone(@Param("phone") String phone);
}
