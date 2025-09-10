package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    @Autowired
    UserService userservice;
    @GetMapping("/healthCHECK")
    public String checkHealth(){
        return "Application is running properly" ;
    }
    @PostMapping("/create-user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        try {
            userservice.addNewUser(user);
            return new ResponseEntity<>("User added succesfully!", HttpStatus.OK );
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }
}
