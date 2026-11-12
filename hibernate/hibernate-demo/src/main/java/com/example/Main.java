package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Student student = new Student();
//        student.setId(103);
//        student.setName("Sushil");
//        student.setTech("SAP");

//        Student s2 = new Student();
//        s2.setId(104);
//        s2.setName("Priya");
//        s2.setTech("FastAPI");

        Laptop l1 = new Laptop();
        l1.setLid(1);
        l1.setBrand("Asus");
        l1.setModel("ROG");
        l1.setRam(32);

        Laptop l2 = new Laptop();
        l2.setLid(2);
        l2.setBrand("Asus");
        l2.setModel("TUF Gaming");
        l2.setRam(16);

        Laptop l3 = new Laptop();
        l3.setLid(3);
        l3.setBrand("Dell");
        l3.setModel("XPS");
        l3.setRam(32);

        Alien alien1 = new Alien();
        alien1.setAid(201);
        alien1.setAname("Purush");
        alien1.setAge(20);

        Alien alien2 = new Alien();
        alien2.setAid(202);
        alien2.setAname("Naveen");
        alien2.setAge(34);

        Alien alien3 = new Alien();
        alien3.setAid(203);
        alien3.setAname("Yurekan");
        alien3.setAge(24);

        alien1.setLaptops(List.of(l1, l3));
        alien2.setLaptops(List.of(l2, l3));
        alien3.setLaptops(List.of(l1));

        l1.setAliens(List.of(alien1, alien3));
        l2.setAliens(List.of(alien2));
        l3.setAliens(List.of(alien1, alien2));

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Alien.class);
        cfg.addAnnotatedClass(Laptop.class);
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        // Fetch a record from the table
//        s2 = session.find(Student.class, 102);

        // Create a record in the table
//        Transaction transaction = session.beginTransaction();
//
//        session.persist(student);
//        transaction.commit();

        // Update the record if found or create the record
//        Transaction tx = session.beginTransaction();
//        s2 = session.find(Student.class, 104);
//        s2.setTech("Flask");
//        session.merge(s2);
//
//        tx.commit();

        Transaction tx = session.beginTransaction();
        session.persist(alien1);
        session.persist(alien2);
        session.persist(alien3);
        session.persist(l1);
        session.persist(l2);
        session.persist(l3);
        tx.commit();

        session.close();
        sf.close();

        System.out.println(alien2);
    }
}
