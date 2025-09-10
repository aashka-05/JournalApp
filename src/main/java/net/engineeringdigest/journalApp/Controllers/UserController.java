package net.engineeringdigest.journalApp.Controllers;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserDetailsServiceImplementation;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserDetailsServiceImplementation userDetailsService;
    @Autowired
    UserService userservice;

    @PutMapping("/update")
    public ResponseEntity<String> updatePass(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User old = userservice.findByUsername(authentication.getName());
        try {
            if (old != null) {
                if (true) {
                    throw new RuntimeException("Forced exception for testing catch block");
                }
                old.setUsername((user.getUsername() != null && user.getUsername() != "") ? user.getUsername() : old.getUsername());
                old.setPassword((user.getPassword() != null && user.getPassword() != "") ? user.getPassword() : old.getPassword());
                userservice.addNewUser(old);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            //log.info("info of the error for username {}  :",old.getUsername(),e);
            log.warn("warning about the error:",e);
            log.trace("trace :",e);
            return new ResponseEntity<>("Exception while updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/view")
    public ResponseEntity<List<User>> viewAllUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userservice.viewAll(),HttpStatus.OK);
    }
    @DeleteMapping("/Delete")
    public ResponseEntity<User> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User delUser = userservice.deleteByUsername(authentication.getName());
        if(delUser!=null){
            return new ResponseEntity<>(delUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
