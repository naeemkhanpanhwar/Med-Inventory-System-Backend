package com.example.MedInventorySystem.Service;

import com.example.MedInventorySystem.Model.Medicines;
import com.example.MedInventorySystem.Respository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicines> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicines saveMedicine(Medicines medicine) {
        return medicineRepository.save(medicine);
    }

    public Medicines getMedicineById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    public boolean deleteMedicine(Long id) {
        Optional<Medicines> medicine = medicineRepository.findById(id);
        if (medicine.isPresent()) {
            medicineRepository.delete(medicine.get());
            return true;
        }
        return false;
    }



    public Medicines updateMedicine(Long id, Medicines updatedMedicine) {
        if (medicineRepository.existsById(id)) {
            updatedMedicine.setId(id);
            return medicineRepository.save(updatedMedicine);
        }
        return null;
    }
}
