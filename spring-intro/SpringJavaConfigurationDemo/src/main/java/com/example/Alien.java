package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Alien {

    private int age;

    // @Autowired annotation tells spring to look for the bean using dependency injection.
    // this is called field injection, because we are injecting directly into the class property
    // @Autowired
    // @Qualifier annotation tells spring to look for specific bean in IoC Container
    // using Annotation-based Configuration, the class name becomes the default bean name
    // @Qualifier("desktop")
    private Computer com;

    public Alien() {
        System.out.println("Alien Object Created");
    }

    public Alien(int age, Computer com) {
        System.out.println("Para Constructor Alien called");
        this.age = age;
        this.com = com;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public Computer getCom() {
        return com;
    }

    @Autowired
    @Qualifier("laptop")
    public void setCom(Computer com) {
        this.com = com;
    }

    public void code(){
        System.out.println("Coding...");
        com.compile();
    }
}
