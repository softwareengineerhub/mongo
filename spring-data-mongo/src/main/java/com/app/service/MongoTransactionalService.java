package com.app.service;

import com.app.model.Person;
import com.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MongoTransactionalService {

    @Autowired
    private PersonRepository personRepository;

    public void save(Person p1, Person p2){
        personRepository.save(p1);
        personRepository.save(p2);
    }

    public void saveWithError(Person p1, Person p2){
        personRepository.save(p1);
        personRepository.save(p2);
        String s = null;
        s.toString();
    }

    public void saveWithError2(Person p1, Person p2){
        personRepository.save(p1);
        String s = null;
        s.toString();
        personRepository.save(p2);
    }

}
