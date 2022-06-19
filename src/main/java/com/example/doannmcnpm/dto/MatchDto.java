package com.example.doannmcnpm.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class MatchDto {

    @NotNull
    private int yardLocationId;

    @NotNull
    private int categoryId;

    @NotNull
    private String matchDate;

    @NotNull
    private String token;

    private String name;

    private String phone;



    @NotNull
    private String startTime;

    @NotNull
    private String endTime;





    public MatchDto(int yardLocationId, int categoryId, String token, String name, String phone , String matchDate, String startTime, String endTime) {
        this.yardLocationId = yardLocationId;
        this.categoryId = categoryId;
        this.token=token;
        this.name=name;
        this.phone=phone;
        this.matchDate = matchDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MatchDto() {
    }

    public int getYardLocationId() {
        return yardLocationId;
    }

    public void setYardLocationId(int yardLocationId) {
        this.yardLocationId = yardLocationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
