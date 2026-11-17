package com.example.SpringBootDemo.respository;

import com.example.SpringBootDemo.model.Laptop;
import org.springframework.stereotype.Repository;

@Repository
public class LaptopRepository {
    public void save(Laptop laptop) {
        System.out.println("Saved to Database");
    }
}
