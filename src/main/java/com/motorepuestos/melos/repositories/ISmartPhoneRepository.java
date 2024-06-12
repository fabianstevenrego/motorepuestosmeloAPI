package com.motorepuestos.melos.repositories;

import com.motorepuestos.melos.models.SmartPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISmartPhoneRepository extends JpaRepository<SmartPhone, Long> {
}
