package com.redeemerlives.jobms.job.external;

public class Company {

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int companyId) {
        this.id = companyId;
    }

    public Company(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
