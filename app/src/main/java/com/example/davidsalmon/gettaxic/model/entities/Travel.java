package com.example.davidsalmon.gettaxic.model.entities;

public class Travel {

    enum taxi{AVAILABLE,OCCUPIED,FINISHED};
    private taxi available;
    private String startLocation;
    private String destinationLocation;
    private double startTravelTime;
    private double endTravelTime;
    private String customerName;
    private String customerPhone;
    private String customerEmail;

    public Travel(taxi available, String startLocation, String destinationLocation, double startTravelTime, double endTravelTime, String customerName, String customerPhone, String customerEmail) {
        this.available = available;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.startTravelTime = startTravelTime;
        this.endTravelTime = endTravelTime;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
    }

    public taxi getAvailable() {
        return available;
    }

    public void setAvailable(taxi available) {
        this.available = available;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public double getStartTravelTime() {
        return startTravelTime;
    }

    public void setStartTravelTime(double startTravelTime) {
        this.startTravelTime = startTravelTime;
    }

    public double getEndTravelTime() {
        return endTravelTime;
    }

    public void setEndTravelTime(double endTravelTime) {
        this.endTravelTime = endTravelTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}