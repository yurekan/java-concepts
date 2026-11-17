package com.example.SpringBootDemo.service;

import com.example.SpringBootDemo.respository.LaptopRepository;
import com.example.SpringBootDemo.model.Laptop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaptopService {

    @Autowired
    LaptopRepository laptopRepository;

    public void addLaptop(Laptop laptop){
        laptopRepository.save(laptop);
    }

    public boolean isGoodForProg(Laptop laptop){
        return true;
    }
}
