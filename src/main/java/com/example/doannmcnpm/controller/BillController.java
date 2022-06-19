package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.dto.BillDto;
import com.example.doannmcnpm.model.Bill;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.repository.BillRepository;
import com.example.doannmcnpm.service.BillService;
import com.example.doannmcnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bill")
@CrossOrigin
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    BillRepository billRepository;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public List<Bill> listBill()   {
        return billService.listAll();
    }

    @GetMapping("/listDate")
    public List<Bill> listBillDate(@RequestParam String startDate, @RequestParam String  endDate)   {
        return billService.listBillDate(startDate, endDate);
    }

    @PostMapping("/create/{token}")
    public void createBill(@PathVariable("token") String token,@RequestBody BillDto billDto)    {
        User user= userService.getUser(token);
        billService.createBill(billDto, user);
    }

    @PutMapping("/update/{billId}/{token}")
    public ResponseEntity<ApiResponse> updateBill(@PathVariable("billId") int billId, @PathVariable("token") String token, @RequestBody BillDto billDto)  {


        User user= userService.getUser(token);
        if(billRepository.findById(billId).isEmpty())   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Bil does not exists"), HttpStatus.NOT_FOUND);
        }
        billService.updateBill(billId,user, billDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Bil has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{billId}")
    public  ResponseEntity<ApiResponse> deleteBill(@PathVariable("billId") int billId)  {
        System.out.println("ID: "+ billId);
        if(billRepository.findById(billId).isEmpty())   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Bil does not exists"), HttpStatus.NOT_FOUND);
        }
        billService.deleteBill(billId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Bill has been delete"), HttpStatus.OK);
    }

}
