package com.redeemerlives.companyms.company.Impl;

import com.redeemerlives.companyms.company.Company;
import com.redeemerlives.companyms.company.CompanyRepository;
import com.redeemerlives.companyms.company.CompanyService;
import com.redeemerlives.companyms.company.clients.ReviewClient;
import com.redeemerlives.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
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
        Double averageRating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        Optional<Company> company = companyRepository.findById(reviewMessage.getCompanyId());
        company.ifPresent(company1 -> {
            company1.setRating(averageRating);
            companyRepository.save(company1);
        });
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
