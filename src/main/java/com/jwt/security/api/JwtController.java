package com.jwt.security.api;

import com.jwt.security.JwtRequestModel;
import com.jwt.security.JwtResponseModel;
import com.jwt.security.JwtUserDetailService;
import com.jwt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity createToken(@RequestBody JwtRequestModel jwtRequestModel) throws Exception{
        try{
            System.out.println(jwtRequestModel.getUsername() + " " + jwtRequestModel.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequestModel.getUsername(),
                            jwtRequestModel.getPassword())
            );
        }
        catch(DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtRequestModel.getUsername());
        System.out.println(userDetails.getUsername() + " " + userDetails.getPassword());
        final String token = tokenManager.generateJwt(userDetails);


        return ResponseEntity.ok(new JwtResponseModel(token));
    }


}
