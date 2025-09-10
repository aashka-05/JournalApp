package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.JournalContent;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.JournalEntryServices;
import net.engineeringdigest.journalApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")

public class JournalController {

    @Autowired
    JournalEntryServices entryServices;
    @Autowired
    UserService userService;

    @GetMapping("/display")
    public ResponseEntity<?> viewByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User curr_user = userService.findByUsername(username);
        List<JournalContent> result = curr_user.getEntries();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/byID/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalContent> content = user.getEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!content.isEmpty()) {
            JournalContent j = entryServices.viewbyId(id).orElse(null);
            if (j != null) {
                return new ResponseEntity<>(entryServices.viewbyId(id), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody JournalContent data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            data.setDate(LocalDateTime.now());
            JournalContent entry = entryServices.add(data);
            user.getEntries().add(entry);
            userService.addUser(user);
            return new ResponseEntity<>("Data added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {

            throw new RuntimeException("exception while adding");
            //return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<JournalContent> modify(@PathVariable ObjectId Id, @RequestBody JournalContent new_data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalContent> content = user.getEntries().stream().filter(x -> x.getId().equals(Id)).collect(Collectors.toList());
        if (!content.isEmpty()) {
            JournalContent old_data = entryServices.viewbyId(Id).orElse(null);
            if (old_data != null) {
                System.out.println("ID before saving: " + old_data.getId());
                old_data.setTitle((new_data.getTitle() != null) && !(new_data.getTitle().isEmpty()) ? new_data.getTitle() : old_data.getTitle());
                old_data.setContent((new_data.getContent() != null) && !(new_data.getContent().isEmpty()) ? new_data.getContent() : old_data.getContent());
                entryServices.add(old_data);

                return new ResponseEntity<>(old_data, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId Id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        boolean removed = user.getEntries().removeIf(x -> x.getId().equals(Id));
        if (removed) {
            JournalContent data = entryServices.viewbyId(Id).orElse(null);
            if (data != null) {
                entryServices.del(Id);
                userService.addUser(user);
                return new ResponseEntity<>("DELETED SUCCESSFULLY!", HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}


