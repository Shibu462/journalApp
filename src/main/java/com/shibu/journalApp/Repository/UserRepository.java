package com.shibu.journalApp.Repository;


import com.shibu.journalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
    User findByUserName(String username);

    void deleteByUserName(String username);
}
