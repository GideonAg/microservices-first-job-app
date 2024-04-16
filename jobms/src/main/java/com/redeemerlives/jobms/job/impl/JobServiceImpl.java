package com.redeemerlives.jobms.job.impl;

import com.redeemerlives.jobms.job.Job;
import com.redeemerlives.jobms.job.JobRepository;
import com.redeemerlives.jobms.job.JobService;
import com.redeemerlives.jobms.job.clients.CompanyClient;
import com.redeemerlives.jobms.job.clients.ReviewClient;
import com.redeemerlives.jobms.job.dto.JobDTO;
import com.redeemerlives.jobms.job.external.Company;
import com.redeemerlives.jobms.job.external.Review;
import com.redeemerlives.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    private int numberOfAttempts = 0;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> getAllJobs() {
        System.out.println("Attempts " + ++numberOfAttempts);
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertJobAndCompanyToDTO).toList();
    }

    private List<String> companyBreakerFallback(Exception e) {
        List<String> fallBackList = new ArrayList<>();
        fallBackList.add("something went wrong, try again later");
        return fallBackList;
    }

    private JobDTO convertJobAndCompanyToDTO(Job job) {
        int companyId = job.getCompanyId();
        Company company = companyClient.getCompanyById(companyId);
        List<Review> companyReviews = reviewClient.getReviews(companyId);

        return JobMapper.mapJobWithCompany(job, company, companyReviews);
    }

    @Override
    public JobDTO getJobById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.map(this::convertJobAndCompanyToDTO).orElse(null);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Boolean deleteJobById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        job.ifPresent(jobRepository::delete);
        return job.isPresent();
    }

    @Override
    public boolean updateJob(String id, Job jobUpdate) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            job.get().setTitle(jobUpdate.getTitle());
            job.get().setDescription(jobUpdate.getDescription());
            job.get().setMinSalary(jobUpdate.getMinSalary());
            job.get().setMaxSalary(jobUpdate.getMaxSalary());
            job.get().setLocation(jobUpdate.getLocation());
            jobRepository.save(job.get());
            return true;
        }

        return false;
    }
}
