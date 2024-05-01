package com.redeemerlives.companyms.company.Impl;

import com.redeemerlives.companyms.company.Company;
import com.redeemerlives.companyms.company.CompanyRepository;
import com.redeemerlives.companyms.company.CompanyService;
import com.redeemerlives.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(int id) {
        return companyRepository.findById(id);
    }

    @Override
    public void createCompany(Company company) {
        Company newCompany = new Company(company.getName(), company.getDescription());
        companyRepository.save(newCompany);
    }

    @Override
    public boolean updatedCompany(Company companyUpdate, int companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            company.get().setName(companyUpdate.getName());
            company.get().setDescription(companyUpdate.getDescription());
            companyRepository.save(company.get());
            return true;
        }

        return false;
    }

    @Override
    public void updatedCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
    }

    @Override
    public boolean deleteCompany(int id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {
            companyRepository.delete(company.get());
            return true;
        }
        return false;
    }
}
