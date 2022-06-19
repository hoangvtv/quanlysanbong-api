package com.example.doannmcnpm.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class MatchDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "soccerField_id", foreignKey = @ForeignKey(name = "fk_match_soccerField"))
    SoccerField soccerField;


    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "yardLocation_id",foreignKey = @ForeignKey(name = "fk_match_yardLocation"))
    YardLocation yardLocation;



    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    @Column
    private Date dateBooking;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private Date startDate;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    @Column
    private double totalMoney;

    public MatchDetails(int id, SoccerField soccerField, YardLocation yardLocation, User user ,
                        String name, String phone ,Date startDate, Date startTime, Date endTime, double totalMoney) {
        this.id = id;
        this.soccerField = soccerField;
        this.yardLocation = yardLocation;
        this.user = user;
        this.name=name;
        this.phone= phone;
        this.dateBooking = new Date();
        this.startDate=  startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalMoney = totalMoney;
    }

    public MatchDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SoccerField getSoccerField() {
        return soccerField;
    }

    public void setSoccerField(SoccerField soccerField) {
        this.soccerField = soccerField;
    }

    public YardLocation getYardLocation() {
        return yardLocation;
    }

    public void setYardLocation(YardLocation yardLocation) {
        this.yardLocation = yardLocation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(Date dateBooking) {
        this.dateBooking = dateBooking;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
