package com.redeemerlives.jobms.job.mapper;

import com.redeemerlives.jobms.job.Job;
import com.redeemerlives.jobms.job.dto.JobDTO;
import com.redeemerlives.jobms.job.external.Company;
import com.redeemerlives.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapJobWithCompany(Job job, Company company, List<Review> review) {
        JobDTO jobWithCompanyDTO = new JobDTO();
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReview(review);
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());

        return jobWithCompanyDTO;
    }
}
