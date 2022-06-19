package com.example.doannmcnpm.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Date createdate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    MatchDetails matchDetails;

    @Column
    private String custommer;

    @Column
    private String nameSoccerField;


    @Column
    private double serviceMoney;

    @Column
    private double merchandiseMoney;

    @Column
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column
    private double totalMoney;

    public Bill(int id, MatchDetails matchDetails, double serviceMoney, double merchandiseMoney, User user, int status) {
        this.id = id;
        this.matchDetails= matchDetails;
        this.custommer=matchDetails.getName();
        this.createdate = new Date();
        this.serviceMoney=serviceMoney;
        this.merchandiseMoney= merchandiseMoney;
        this.nameSoccerField= matchDetails.getSoccerField().getName();
        this.totalMoney= matchDetails.getTotalMoney() + serviceMoney + merchandiseMoney;
        this.status = status;
        this.user = user;
    }

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate() {
        this.createdate = new Date();
    }

    public String getCustommer() {
        return custommer;
    }

    public void setCustommer(String custommer) {
        this.custommer = custommer;
    }

    public String getNameSoccerField() {
        return nameSoccerField;
    }

    public void setNameSoccerField(String nameSoccerField) {
        this.nameSoccerField = nameSoccerField;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public MatchDetails getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(MatchDetails matchDetails) {
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

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
