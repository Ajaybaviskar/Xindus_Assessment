package com.xindus.ecommerce.controller;

import com.xindus.ecommerce.dto.AuthenticationRequest;
import com.xindus.ecommerce.repo.UserRepository;
import com.xindus.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

// Create Auth Controller Here
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public static final String TOKEN_PREFIX="Beare";
    public  static  final String HEADER_STRING="Authorization";


    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public  void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                           HttpServletResponse response) throws IOException, JSONException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");

        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getName())
                    .put("role",optionalUser.get().getRoles())
                    .toString());
        }

        response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);



    }

}
