package com.example.doannmcnpm.service;


import com.example.doannmcnpm.exceptions.AuthenticationFailException;
import com.example.doannmcnpm.exceptions.CustomException;
import com.example.doannmcnpm.model.AuthenticationToken;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }


    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)) {
            throw new CustomException("User does not exist");
        }
        // authenticationToken is not null
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException {
        // null check
        if(Objects.isNull(token)) {
            // throw an exception
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }
    public void deleteToken(AuthenticationToken token)   {
        tokenRepository.delete(token);
    }
}

