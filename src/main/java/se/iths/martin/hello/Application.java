package se.iths.martin.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    ApplicationRunner init(DocReactiveRepository repository, ElasticSearch elasticSearch) {
//
//        elasticSearch.init();
//        elasticSearch.close();
//
//        Object[][] data = {
//                {"sea"},
//                {"creek"},
//                {"loaner"}
//        };
//
//        return args -> {
//            repository
//                    .deleteAll()
//                    .thenMany(
//                            Flux
//                                    .just(data)
//                                    .map(array -> {
//                                        return new Doc(null,"donald", LocalDateTime.now(),(String) array[0]);
//                                    })
//                                    .flatMap(repository::save)
//                    )
//                    .thenMany(repository.findAll())
//                    .subscribe(car -> System.out.println("saving " + car.toString()));
//        };
//    }

}
