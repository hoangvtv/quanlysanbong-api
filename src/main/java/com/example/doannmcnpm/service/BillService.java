package com.example.doannmcnpm.service;


import com.example.doannmcnpm.dto.BillDto;
import com.example.doannmcnpm.model.Bill;
import com.example.doannmcnpm.model.MatchDetails;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.repository.BillRepository;
import com.example.doannmcnpm.repository.MatchDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    MatchDetailsRepository matchDetailsRepository;

    public List<Bill> listAll() {
        Date date = new Date();
        List<Bill> billList = new ArrayList<>();

        billRepository.findAll().forEach(bill -> {
                    if (bill.getCreatedate().getYear() == date.getYear() && bill.getCreatedate().getMonth() == date.getMonth()
                            && bill.getCreatedate().getDate() == date.getDate()) {
                        billList.add(bill);
                    }
                }
        );

        return billList;
    }

    public List<Bill> listBillDate(String dateStart, String dateEnd) {
        List<Bill> list = new ArrayList<>();
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date finalStartDate = startDate;
        Date finalEndDate = endDate;
        billRepository.findAll().forEach(item -> {
            if ((item.getCreatedate().getYear() >= finalStartDate.getYear() && item.getCreatedate().getMonth() >= finalStartDate.getMonth()
                    && item.getCreatedate().getDate() >= finalStartDate.getDate()) && (item.getCreatedate().getYear() <= finalEndDate.getYear() &&
                    item.getCreatedate().getMonth() <= finalEndDate.getMonth() && item.getCreatedate().getDate() <= finalEndDate.getDate())) {
                list.add(item);
            }
        });
        return list;
    }


    public void createBill(BillDto billDto, User user) {
        Bill bill = new Bill();
        bill.setCreatedate(new Date());
        MatchDetails matchDetails = matchDetailsRepository.findById(billDto.getMatchDetails()).get();
        bill.setMatchDetails(matchDetails);
        bill.setCustommer(matchDetails.getName());
        bill.setServiceMoney(billDto.getServiceMoney());
        bill.setMerchandiseMoney(billDto.getMerchandiseMoney());
        bill.setNameSoccerField(matchDetails.getSoccerField().getName());
        bill.setTotalMoney(billDto.getMerchandiseMoney() + billDto.getServiceMoney() + matchDetails.getTotalMoney());
        bill.setStatus(billDto.getStatus());
        bill.setUser(user);
        billRepository.save(bill);
    }

    public void updateBill(int billId, User user, BillDto billDto) {
        Bill bill = billRepository.getById(billId);
        bill.setStatus(billDto.getStatus());
        bill.setCreatedate(new Date());
        bill.setServiceMoney(billDto.getServiceMoney());
        bill.setServiceMoney(billDto.getMerchandiseMoney());
        bill.setUser(user);
        billRepository.save(bill);
    }

    public void deleteBill(int billId) {
        Bill bill= billRepository.findById(billId).get();
        bill.setUser(null);
        bill.setMatchDetails(null);
        bill.setNameSoccerField(null);
        billRepository.deleteById(billId);
    }
}
