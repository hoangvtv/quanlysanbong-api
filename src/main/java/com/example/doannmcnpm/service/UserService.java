package com.example.doannmcnpm.service;



import com.example.doannmcnpm.dto.PasswordDto;
import com.example.doannmcnpm.dto.ResponseDto;
import com.example.doannmcnpm.dto.user.SignInDto;
import com.example.doannmcnpm.dto.user.SignInReponseDto;
import com.example.doannmcnpm.dto.user.SignupDto;
import com.example.doannmcnpm.exceptions.AuthenticationFailException;
import com.example.doannmcnpm.exceptions.CustomException;
import com.example.doannmcnpm.model.AuthenticationToken;
import com.example.doannmcnpm.model.Role;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.repository.RoleRepository;
import com.example.doannmcnpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        // check if user is already present
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            // we have an user
            throw new CustomException("User already present");
        }
        // hash the password

        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Role role= roleRepository.findById(signupDto.getRoleId());

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(),signupDto.getPhone(), encryptedpassword, role);

        userRepository.save(user);

        // save the user

        // create the token

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInReponseDto signIn(SignInDto signInDto) {
        // find user by email

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        // hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // compare the password in DB

        // if password match

        AuthenticationToken token = authenticationService.getToken(user);

        // retrive the token

        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInReponseDto("sucess", token.getToken());

        // return response
    }

    public List<User> listUser()    {
        return userRepository.findAll();
    }

    public User getUser(String token) {
        return authenticationService.getUser(token);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void updateUser(User user, int roleId) {
        Role role= roleRepository.findById(roleId);
        user.setRole(role);
        userRepository.save(user);
    }

    public void changePassword(User user, PasswordDto passwordDto) {
        try {
            if (!user.getPassword().equals(hashPassword(passwordDto.getPasswordOld()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String encryptedpassword = passwordDto.getPasswordNew();

        try {
            encryptedpassword = hashPassword(passwordDto.getPasswordNew());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setPassword(encryptedpassword);
        userRepository.save(user);

    }

//    public void deleteUser(User user) {
//        AuthenticationToken token= authenticationService.getToken(user);
//        authenticationService.deleteToken(token);
//        user.set
//        user.setRole(null);
//        userRepository.delete(user);
//    }
}
