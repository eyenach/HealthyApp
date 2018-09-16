package com.example.eyenach.healthyapp.weight;

public class Weight {
    String date;
    String weight;
    String status;

    public Weight(String date, int weight, String status){
        this.date = date;
        this.weight = Integer.toString(weight);
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }

    public String getStatus() {
        return status;
    }

}
