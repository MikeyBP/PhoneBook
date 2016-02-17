package com.bluespurs.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bluespurs.phonebook.models.helpers.PersistenceHelper;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	PersistenceHelper.getHibernateSession();
        SpringApplication.run(Application.class, args);
    }

}
