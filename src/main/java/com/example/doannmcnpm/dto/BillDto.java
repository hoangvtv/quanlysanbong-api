package com.example.doannmcnpm.dto;

import com.example.doannmcnpm.model.MatchDetails;

public class BillDto {
    private int matchDetails;
    private double serviceMoney;
    private double merchandiseMoney;
    private int status;


    public int getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(int matchDetails) {
        this.matchDetails = matchDetails;
    }

    public double getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(double serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public double getMerchandiseMoney() {
        return merchandiseMoney;
    }

    public void setMerchandiseMoney(double merchandiseMoney) {
        this.merchandiseMoney = merchandiseMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
