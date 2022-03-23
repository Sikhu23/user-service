package com.userservice.Repository;

import com.userservice.Model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends MongoRepository<User,String> {

    public User findByemail(String email);



}
