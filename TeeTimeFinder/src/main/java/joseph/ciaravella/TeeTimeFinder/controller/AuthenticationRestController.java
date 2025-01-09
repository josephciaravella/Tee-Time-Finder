package joseph.ciaravella.TeeTimeFinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import joseph.ciaravella.TeeTimeFinder.dto.Authentication.AuthenticationCO;
import joseph.ciaravella.TeeTimeFinder.dto.Authentication.AuthenticationDTO;
import joseph.ciaravella.TeeTimeFinder.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {
    
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationCO authenticationCO) {
        try {
            AuthenticationDTO authenticationDTO = authenticationService.login(authenticationCO);
            return ResponseEntity.ok().body(authenticationDTO);   
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader String token) {
        try {
            authenticationService.logout(token);
            return ResponseEntity.ok().body("Logged out successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
