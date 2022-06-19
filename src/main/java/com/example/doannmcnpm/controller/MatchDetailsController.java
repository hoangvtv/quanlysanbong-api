package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.dto.MatchDto;
import com.example.doannmcnpm.model.MatchDetails;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.service.AuthenticationService;
import com.example.doannmcnpm.service.MatchDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchDetailsController {
    @Autowired
    MatchDetailsService matchDetailsService;

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/findMatch")
    public void  findMatch(@RequestBody MatchDto matchDto) {
        matchDetailsService.findMatch(matchDto);
    }

    @GetMapping("/list")
    public List<MatchDetails> listAll() {
        return matchDetailsService.listAll();
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<MatchDetails>> getMatchList(@PathVariable("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        List<MatchDetails> list= matchDetailsService.getListMatchForUsser(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/listDate")
    public List<MatchDetails> listDate()   {
        return matchDetailsService.listDate();
    }


    @GetMapping("/getMatchForDate")
    public List<MatchDetails> listMatchForDate(@RequestParam String startDate, @RequestParam String  endDate)   {
        return matchDetailsService.listMatchForDate(startDate, endDate);
    }

//    @PutMapping("/update/{matchId}/{startTime}/{endTime}")
//    public ResponseEntity<ApiResponse> updateMatch(@PathVariable("matchId") int matchId, @PathVariable("startTime") String
//                                                   startTime, @PathVariable("endTime") String endTime) {
//
//        if(matchDetailsService.findById(matchId)== null)    {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, " Match does not exits"), HttpStatus.NOT_FOUND);
//        }
//        matchDetailsService.updateMatch(matchId, startTime, endTime);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Match has been updated"), HttpStatus.OK);
//
//    }
    @DeleteMapping("/delete/{matchId}")
    public ResponseEntity<ApiResponse> deleteMatch(@PathVariable("matchId") int matchId)    {

        if(matchDetailsService.findById(matchId)== null)    {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, " Match does not exits"), HttpStatus.NOT_FOUND);
        }
        matchDetailsService.deleteMatch(matchId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Match has been deleted"), HttpStatus.OK);
    }


}
