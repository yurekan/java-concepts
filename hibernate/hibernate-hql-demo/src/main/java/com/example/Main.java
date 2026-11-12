package com.example;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Laptop.class);
        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        // select * from laptop where ram=32
        // from Laptop where ram=32

        String brand = "Asus";

        Query query = session.createQuery("select model, brand from Laptop where brand like ?1");
        query.setParameter(1, brand);
        List<Object[]> laptops = query.getResultList();

        laptops.forEach(laptop -> {
            System.out.println("Model : " + laptop[0] + " Brand : " + laptop[1]);
        });

//        Laptop laptop = session.find(Laptop.class, 1);

        System.out.println(laptops);

        session.close();
        sf.close();
    }
}
