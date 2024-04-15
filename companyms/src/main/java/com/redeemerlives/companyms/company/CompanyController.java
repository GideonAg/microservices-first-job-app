package com.redeemerlives.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        if (company.isPresent()) return ResponseEntity.ok(company.get());
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return ResponseEntity.ok("company created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable int id, @RequestBody Company company) {
        boolean updated = companyService.updatedCompany(company, id);

        if (updated) return new ResponseEntity<>("company updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("company not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable int id) {
        boolean deleted = companyService.deleteCompany(id);

        if (deleted) return new ResponseEntity<>("company deleted successfully", HttpStatus.OK);
        else {
            return new ResponseEntity<>("company not found", HttpStatus.NOT_FOUND);
        }
    }
}
