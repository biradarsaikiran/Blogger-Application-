package com.blog.Controller;

import com.blog.Entity.Role;
import com.blog.Entity.User;
import com.blog.Payload.JWTAuthResponse;
import com.blog.Payload.LoginDto;
import com.blog.Payload.SignUpDto;
import com.blog.Repository.RoleRepository;
import com.blog.Repository.UserRepository;
import com.blog.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    /*  http://localhost:8080/api/auth/signUp */
    @PostMapping("/signUp")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {

        if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already exists ..Please enter new ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already exists ..Please enter new ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // create the new User to register
        User user = new User();

        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        //here Collections.singleton does it converts Roles to Set.

        userRepository.save(user);


        return new ResponseEntity<>("user registered successfully..you can Login ", HttpStatus.OK);
    }
    // http://localhost:8080/api/auth/signIn

    @PostMapping("signIn")
    public ResponseEntity<JWTAuthResponse> singInUser(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = tokenProvider.generateToken(authenticate);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
}




