package com.nhom1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom1.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
