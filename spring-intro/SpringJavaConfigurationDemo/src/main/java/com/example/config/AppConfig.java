package com.example.config;

import com.example.Alien;
import com.example.Computer;
import com.example.Desktop;
import com.example.Laptop;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

@Configuration
// Spring scans the specified packages for classes annotated with @Component.
// - It registers that object in the Spring IoC container.
// - You can inject this bean into other classes using dependency injection.
@ComponentScan("com.example")
public class AppConfig {

//    @Bean(name = {"alien", "aln"})
//    // @Qualifier is an annotation used to specify which bean should be injected when multiple beans of the same
//    // type are available. Qualifier can override Primary annotation.
//
//    public Alien getAlien(Computer com){
//        Alien obj = new Alien();
//        obj.setAge(21);
//        obj.setCom(com);
//        return obj;
//    }
//
////    By default the name of this bean is the name of the method. If you want to change the name of the bean, use name
////    attribute inside the annotation with array of choices.
////    Once the name is configured, the default name does not work
//    @Bean(name = {"desktop", "desk"})
//    // @Scope("prototype")
//    public Desktop getDesktop() {
//        return new Desktop();
//    }
//
//    @Bean(name = {"laptop", "lap"})
//    @Primary
//    public Laptop getLaptop() {
//        return new Laptop();
//    }
}
