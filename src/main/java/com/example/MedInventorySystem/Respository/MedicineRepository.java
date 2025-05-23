package com.example.MedInventorySystem.Respository;

import com.example.MedInventorySystem.Model.Medicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicines, Long> {
}
