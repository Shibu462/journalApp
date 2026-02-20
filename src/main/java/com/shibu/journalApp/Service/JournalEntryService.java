package com.shibu.journalApp.Service;

import com.shibu.journalApp.Entity.JournalEntry;
import com.shibu.journalApp.Entity.User;
import com.shibu.journalApp.Repository.JournalEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String userName) {
        try{
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            User user=userService.findByUserName(userName);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("An error occurred while saving the entry.",e);
        }

    }

    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAllJournals(){
        return journalEntryRepository.findAll();
    }




    public JournalEntry getJournalEntryById(String id){
        return journalEntryRepository.findById(id).orElse(null);
    }




    @Transactional
    public boolean deleteJournalById(String id, String userName){

        boolean removed=false;
        try{
            User user=userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        }
        catch(Exception e){

            throw new RuntimeException("An error occurred while deleting the entry",e);
        }
        return removed;

    }





}
