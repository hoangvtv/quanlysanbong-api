package com.example.doannmcnpm.service;


import com.example.doannmcnpm.dto.MatchDto;
import com.example.doannmcnpm.exceptions.FindSoccerFieldFailException;
import com.example.doannmcnpm.model.Bill;
import com.example.doannmcnpm.model.MatchDetails;
import com.example.doannmcnpm.model.SoccerField;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.repository.CategoryRepository;
import com.example.doannmcnpm.repository.MatchDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MatchDetailsService {
    @Autowired
    MatchDetailsRepository matchDetailsRepository;

    @Autowired
    SoccerFieldService soccerFieldService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    YardLocationService yardLocationService;

    @Autowired
    CategoryRepository categoryRepository;


    public void findMatch(MatchDto matchDto) {
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(matchDto.getMatchDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date startTime = null;
        try {
            startTime = new SimpleDateFormat("hh:mm").parse(matchDto.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startTime.getMinutes() >= 45) {
            startTime.setMinutes(0);
            startTime.setHours(startTime.getHours() + 1);
        } else if (startTime.getMinutes() >= 15) {
            startTime.setMinutes(30);
        } else {
            startTime.setMinutes(0);
        }

        Date endTime = null;
        try {
            endTime = new SimpleDateFormat("hh:mm").parse(matchDto.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (endTime.getMinutes() >= 45) {
            endTime.setMinutes(0);
            endTime.setHours(endTime.getHours() + 1);
        } else if (endTime.getMinutes() > 15) {
            endTime.setMinutes(30);
        } else {
            endTime.setMinutes(0);
        }


        double thoigianda = (endTime.getTime() - startTime.getTime()) / 3600000.0 < 1 ? 1 :
                ((endTime.getTime() - startTime.getTime()) / 3600000.0);

        if (thoigianda >2.0)    {
            throw new FindSoccerFieldFailException("Thời gian đá quá lớn");
        }


        if (endTime.getTime() - startTime.getTime() < 0) {
            Date tepm = endTime;
            endTime = startTime;
            startDate = tepm;
        }

        SoccerField soccerField = findSoccerField(matchDto, startTime, endTime);
        if (soccerField == null) {
            throw new FindSoccerFieldFailException("Không có sân trống");
        }

        MatchDetails matchDetails = new MatchDetails();
        matchDetails.setYardLocation(yardLocationService.findByID(matchDto.getYardLocationId()));
        matchDetails.setDateBooking(new Date());
        matchDetails.setName(matchDto.getName());
        matchDetails.setPhone(matchDto.getPhone());
        matchDetails.setStartDate(startDate);
        matchDetails.setStartTime(startTime);
        matchDetails.setEndTime(endTime);
        matchDetails.setTotalMoney(soccerField.getPrice() * thoigianda);
        matchDetails.setUser(authenticationService.getUser(matchDto.getToken()));
        matchDetails.setSoccerField(soccerField);
        matchDetailsRepository.save(matchDetails);
    }

    public SoccerField findSoccerField(MatchDto matchDto, Date startTime, Date endTime) {
        List<SoccerField> soccerFieldList = soccerFieldService.listSoccerFieldByCategoryAndYardlocation((long) matchDto.getCategoryId(),
                (long) matchDto.getYardLocationId());

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(matchDto.getMatchDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<MatchDetails> matchDetailsList = new ArrayList<>();
        Date finalStartDate = startDate;
        matchDetailsRepository.findAll().forEach(item -> {
            if (item.getYardLocation().getId() == matchDto.getYardLocationId()
                    && item.getSoccerField().getCategory().getId() == matchDto.getCategoryId()
                    && item.getStartDate().getTime()== finalStartDate.getTime() ) {
                matchDetailsList.add(item);
            }
        });

        Set<Integer> soccerFieldIdList = new HashSet<>();

        for (MatchDetails matchDetails : matchDetailsList) {
            if ((matchDetails.getStartTime().getTime() >= startTime.getTime() && matchDetails.getStartTime().getTime() <= endTime.getTime())
                    || (matchDetails.getEndTime().getTime() >= startTime.getTime() && matchDetails.getEndTime().getTime() <= endTime.getTime())) {
                soccerFieldIdList.add(matchDetails.getSoccerField().getId());
            }
        }
        for (SoccerField soccerField : soccerFieldList) {
            if (findSoccerId(soccerFieldIdList, soccerField.getId()) == false) {
                if(soccerField.getStatus()==1)  {
                    return soccerField;
                }
            }
        }
        return null;
    }

    public static boolean findSoccerId(Set<Integer> list, int x) {
        for (int item : list) {
            if (item == x) {
                return true;
            }
        }
        return false;
    }


    public List<MatchDetails> findAll() {
        return matchDetailsRepository.findAll();
    }


    public List<MatchDetails> getListMatchForUsser(User user) {
        List<MatchDetails> list = new ArrayList<>();
        Date date = new Date();
        matchDetailsRepository.findAll().forEach(item -> {
            if ( item.getUser().getEmail().equals(user.getEmail()) ) {
                list.add(item);
            }

//            if ( item.getUser().getEmail().equals(user.getEmail()) &&item.getStartDate().getYear() == date.getYear() &&
//                    date.getMonth() == item.getStartDate().getMonth() &&
//                    item.getStartDate().getDate() == date.getDate()) {
//                list.add(item);
//            }
        });
        return list;
    }

    public List<MatchDetails> listDate() {
        List<MatchDetails> list = new ArrayList<>();

        Date date = new Date();

        matchDetailsRepository.findAll().forEach(item -> {

            if (item.getStartDate().getYear() == date.getYear() &&
                    date.getMonth() == item.getStartDate().getMonth() &&
                    item.getStartDate().getDate() == date.getDate()) {
                list.add(item);
            }
        });
        return list;
    }

    public MatchDetails findById(int id) {
        return matchDetailsRepository.findById(id).get();
    }

    public void deleteMatch(int matchId) {
        MatchDetails matchDetails = matchDetailsRepository.findById(matchId).get();
        matchDetails.setSoccerField(null);
        matchDetails.setYardLocation(null);
        matchDetailsRepository.delete(matchDetails);
    }

    public List<MatchDetails> listAll() {
        return matchDetailsRepository.findAll();
    }

    public List<MatchDetails> listMatchForDate(String dateStart, String dateEnd) {
        List<MatchDetails> list = new ArrayList<>();
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

        matchDetailsRepository.findAll().forEach(item -> {
            if ((item.getStartDate().getYear() >= finalStartDate.getYear() && item.getStartDate().getMonth() >= finalStartDate.getMonth()
                    && item.getStartDate().getDate() >= finalStartDate.getDate()) && (item.getStartDate().getYear() <= finalEndDate.getYear() &&
                    item.getStartDate().getMonth() <= finalEndDate.getMonth() && item.getStartDate().getDate() <= finalEndDate.getDate())) {
                list.add(item);
            }
        });
        return list;
    }

//    public void updateMatch(int matchId, String timeStart, String timeEnd) {
//        MatchDetails matchDetails=matchDetailsRepository.findById(matchId).get();
//
//        Date startDate = matchDetails.getStartDate();
//        Date startTime = null;
//        try {
//            startTime = new SimpleDateFormat("hh:mm").parse(timeStart);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (startTime.getMinutes() >= 45) {
//            startTime.setMinutes(0);
//            startTime.setHours(startTime.getHours() + 1);
//        } else if (startTime.getMinutes() >= 15) {
//            startTime.setMinutes(30);
//        } else {
//            startTime.setMinutes(0);
//        }
//
//        Date endTime = null;
//        try {
//            endTime = new SimpleDateFormat("hh:mm").parse(timeEnd);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (endTime.getMinutes() >= 45) {
//            endTime.setMinutes(0);
//            endTime.setHours(endTime.getHours() + 1);
//        } else if (endTime.getMinutes() > 15) {
//            endTime.setMinutes(30);
//        } else {
//            endTime.setMinutes(0);
//        }
//
//
//        double thoigianda = (endTime.getTime() - startTime.getTime()) / 3600000.0 < 1 ? 1 :
//                ((endTime.getTime() - startTime.getTime()) / 3600000.0);
//
//        if (endTime.getTime() - startTime.getTime() < 0) {
//            Date tepm = endTime;
//            endTime = startTime;
//            startDate = tepm;
//        }
//
//        MatchDto matchDto= new MatchDto();
//        matchDto.setName(matchDetails.getName());
//        matchDto.setPhone(matchDetails.getPhone());
//        matchDto.setCategoryId(matchDetails.getSoccerField().getCategory().getId());
//        matchDto.setYardLocationId(matchDetails.getYardLocation().getId());
//        matchDto.setMatchDate(matchDetails.getStartDate().toString());
//        matchDto.setStartTime(timeStart);
//        matchDto.setEndTime(timeEnd);
//
//        SoccerField soccerField = findSoccerField(matchDto, startTime, endTime);
//        if (soccerField == null) {
//            throw new FindSoccerFieldFailException("Không có sân trống");
//        }
//
//        matchDetails.setDateBooking(new Date());
//        matchDetails.setStartDate(startDate);
//        matchDetails.setStartTime(startTime);
//        matchDetails.setEndTime(endTime);
//        matchDetails.setTotalMoney(soccerField.getPrice() * thoigianda);
//        matchDetails.setUser(authenticationService.getUser(matchDto.getToken()));
//        matchDetails.setSoccerField(soccerField);
//        matchDetailsRepository.save(matchDetails);
//
//    }
}
