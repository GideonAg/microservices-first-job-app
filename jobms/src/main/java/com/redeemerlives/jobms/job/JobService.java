package com.redeemerlives.jobms.job;

import com.redeemerlives.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> getAllJobs();

    void createJob(Job job);

    JobDTO getJobById(String id);

    Boolean deleteJobById(String id);

    boolean updateJob(String id, Job jobUpdate);
}
