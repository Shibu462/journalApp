package com.shibu.journalApp.Repository;

import com.shibu.journalApp.Entity.JournalEntry;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String>{

}
