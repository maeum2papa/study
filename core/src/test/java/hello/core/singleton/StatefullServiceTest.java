package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefullServiceTest {

    @Test
    void statefullServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefullService statefullService1 = ac.getBean(StatefullService.class);
        StatefullService statefullService2 = ac.getBean(StatefullService.class);

        int userAPrice = statefullService1.order("userA", 10000);
        int userBPrice = statefullService2.order("userB", 20000);

//        int price = statefullService1.getPrice();
//        int price2 = statefullService2.getPrice();

        System.out.println("userAPrice = " + userAPrice);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }

    static class TestConfig{

        @Bean
        public StatefullService statefullService(){
            return new StatefullService();
        }
    }
}