package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args ) {
        /*
            ApplicationContext is the central Inversion of Control (IoC) container interface responsible for creating,
            configuring, assembling, and managing the lifecycle of application objects, known as beans.

            ClassPathXmlApplicationContext is a concrete implementation class of the ApplicationContext interface.
            You use it by instantiating ClassPathXmlApplicationContext with the path to your XML configuration file
            and assigning it to an ApplicationContext reference variable.

            Bean Scope defines how many objects (instances) Spring creates for a particular bean and how long those objects
            are managed by the Spring container.

            - Singleton: Spring creates only one instance of a particular bean per Spring IoC container and returns the same
              instance whenever that bean is requested.
            - Prototype: Whenever you request a bean from the Spring container, Spring creates a new object and returns it.
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        // Give me the object whose bean ID is alien. It gets the object created by IoC Container.
        // Alien alien = (Alien) context.getBean("alien");

        /*
            When alien bean is configured as Singleton, the object creation happens when the configuration xml is loaded,
            when the application starts. So following code get the same instance for every request.
         */
        // Alien alien1 = (Alien) context.getBean("alien1");
        // Alien alien2 = (Alien) context.getBean("alien2");

        /*
            If the alien bean is configured as Prototype, then the object creation happens only when the object request is
            made

            Alien alien1 =  (Alien) context.getBean("alien");
            alien1.age = 21;
            System.out.println("Alien1: " + alien1.age);
            Alien alien2 =  (Alien) context.getBean("alien");
            System.out.println("Alien2: " + alien2.age);
        */

        /*
            getBean method by default returns a type Object. In order to the object of specific type, we have to
            explicitly type cast it. To solve this ambiguity, we can pass the object type to the getBean method to
            get the object of specific type.
         */
//        Alien alien = (Alien)context.getBean("alien");
//        alien.code();

        Alien alien = context.getBean("alien",  Alien.class);
        alien.code();

        // You can pass just the class name without mentioning the bean id
        // The following will return the object of type Laptop, because we configured the laptop bean as primary bean.
        Computer com = context.getBean(Computer.class);
        com.compile();

        Desktop desktop = context.getBean(Desktop.class);
    }
}
