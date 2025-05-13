package com.example.MedInventorySystem.Controller;
import com.example.MedInventorySystem.Model.Medicines;
import com.example.MedInventorySystem.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;


        @GetMapping("/index")
        public String showIndexPage(Model model) {
            model.addAttribute("newMedicine", new Medicines());
            return "index";
        }
    @GetMapping
    public String viewHomePage(Model model) {
        List<Medicines> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        model.addAttribute("newMedicine", new Medicines());
        return "index";
    }

    @PostMapping("/add")
    public String addMedicines(@ModelAttribute("newMedicine") Medicines medicine) {
        medicineService.saveMedicine(medicine);
        return "redirect:/medicines";
    }
    @PostMapping
    public ResponseEntity<Medicines> addMedicine(@RequestBody Medicines medicine) {
        Medicines savedMedicine = medicineService.saveMedicine(medicine);
        return ResponseEntity.ok(savedMedicine);
    }


    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> calculateTotalPrice(@RequestBody List<Long> medicineIds) {
        double totalPrice = 0;
        List<Medicines> selectedMedicines = new ArrayList<>();

        for (Long id : medicineIds) {
            Medicines medicine = medicineService.getMedicineById(id);
            if (medicine != null) {
                selectedMedicines.add(medicine);
                totalPrice += medicine.getPrice();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("medicines", selectedMedicines);
        response.put("totalPrice", totalPrice);

        return ResponseEntity.ok(response);
    }

//    @PostMapping("/cart")
//    @ResponseBody
//    public double calculateTotalPrice(@RequestBody List<Medicines> medicineRequests) {
//        double totalPrice = 0;
//        for (Medicines request : medicineRequests) {
//            Medicines medicine = medicineService.getMedicineById(request.getId());
//            if (medicine != null) {
//                totalPrice += medicine.getPrice() * request.getQuantity();
//            }
//        }
//        return totalPrice;
//    }

//    @GetMapping("/update/{id}")
//    public String showEditForm(@PathVariable("id") Long id, Model model) {
//        Medicines medicine = medicineService.getMedicineById(id);
//        if (medicine != null) {
//            model.addAttribute("medicine", medicine);
//            return "edit-medicine";
//        }
//        return "redirect:/medicines";
//    }

//    @PostMapping("/update/{id}")
//    public String updateMedicine(@PathVariable("id") Long id, @ModelAttribute("medicine") Medicines medicine) {
//        medicineService.updateMedicine(id, medicine);
//        return "redirect:/medicines";
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Medicines> updateMedicine(@PathVariable("id") Long id, @RequestBody Medicines medicine) {
        Medicines existingMedicine = medicineService.getMedicineById(id);
        if (existingMedicine != null) {
            existingMedicine.setName(medicine.getName());
            existingMedicine.setPrice(medicine.getPrice());
            existingMedicine.setQuantity(medicine.getQuantity());
            medicineService.saveMedicine(existingMedicine);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable("id") Long id) {
        boolean isDeleted = medicineService.deleteMedicine(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/api/all")
    @ResponseBody
    public List<Medicines> getAllMedicinesApi() {
        return medicineService.getAllMedicines();
    }

}


