package com.wahyurds.bcadigital.credits.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class JsonResponse {
    private String vehicleType;
    private String vehicleCondition;
    private int vehicleYear;
    private long totalLoanAmount;
    private int loanTenure; // in years
    private long downPayment;
    private double annualInterestRate; // in percent per year, optional

    public JsonResponse() {}

    @JsonProperty("vehicleType")
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    @JsonProperty("vehicleCondition")
    public String getVehicleCondition() { return vehicleCondition; }
    public void setVehicleCondition(String vehicleCondition) { this.vehicleCondition = vehicleCondition; }

    @JsonProperty("vehicleYear")
    public int getVehicleYear() { return vehicleYear; }
    public void setVehicleYear(int vehicleYear) { this.vehicleYear = vehicleYear; }

    @JsonProperty("totalLoanAmount")
    public long getTotalLoanAmount() { return totalLoanAmount; }
    public void setTotalLoanAmount(long totalLoanAmount) { this.totalLoanAmount = totalLoanAmount; }

    @JsonProperty("loanTenure")
    public int getLoanTenure() { return loanTenure; }
    public void setLoanTenure(int loanTenure) { this.loanTenure = loanTenure; }

    @JsonProperty("downPayment")
    public long getDownPayment() { return downPayment; }
    public void setDownPayment(long downPayment) { this.downPayment = downPayment; }
}
