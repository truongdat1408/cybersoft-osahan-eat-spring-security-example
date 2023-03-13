package com.cybersoft.osahaneat.controller;

import com.cybersoft.osahaneat.payload.ResponseData;
import com.cybersoft.osahaneat.payload.SignInRequest;
import com.cybersoft.osahaneat.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest request){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication auth = authenticationManager.authenticate(token);

        //Tạo key
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String jwtKey = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Key: " + jwtKey);
        Gson gson = new Gson();
        String data = gson.toJson(auth);


        String jwtToken = jwtUtilsHelpers.generateToken(data);
        ResponseData responseData = new ResponseData();
        responseData.setData(jwtToken);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    //express security
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> signup() {
        return new ResponseEntity<>("Hello signup", HttpStatus.OK);
    }
}
