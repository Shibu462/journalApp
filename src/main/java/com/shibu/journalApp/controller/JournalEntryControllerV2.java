package com.shibu.journalApp.controller;

import com.shibu.journalApp.Entity.JournalEntry;
import com.shibu.journalApp.Entity.User;
import com.shibu.journalApp.Service.JournalEntryService;
import com.shibu.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// Marks this class as a REST controller.
// Combines @Controller + @ResponseBody.
// Every method returns JSON directly.
@RestController

// Base URL for all APIs in this controller.
// All endpoints will start with: /journal
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getALlJournalsOfUser() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        User user = userService.findByUserName(userName);

        List<JournalEntry> all = user.getJournalEntries();

        if(all!=null&&!all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }





    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();

        if(collect!=null){
            Optional<JournalEntry> journalEntry= Optional.ofNullable(journalEntryService.getJournalEntryById(id));

            if(journalEntry.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<JournalEntry>( HttpStatus.NOT_FOUND);

    }



    @PostMapping
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();


        try{
            journalEntryService.saveJournalEntry(myEntry,userName);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }

    }



    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        boolean removed=false;
        if(journalEntryService.getJournalEntryById(id)!=null){
             removed=journalEntryService.deleteJournalById(id,userName);
        }

        if(removed){
            return new ResponseEntity<JournalEntry>(HttpStatus.OK);
        }
       return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable String id,
                                          @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();

        if(collect!=null){
            Optional<JournalEntry> journalEntry= Optional.ofNullable(journalEntryService.getJournalEntryById(id));

            if(journalEntry.isPresent()){
                JournalEntry oldEntry=journalEntry.get();
                oldEntry.setHeading((newEntry.getHeading()!=null&&newEntry.getHeading()!="")?newEntry.getHeading(): oldEntry.getHeading());
                oldEntry.setContent((newEntry.getContent()!=null&&newEntry.getContent()!="")? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveJournalEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }





        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
