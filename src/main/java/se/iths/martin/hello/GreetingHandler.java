//package se.iths.martin.hello;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//import se.iths.martin.hello.models.Doc;
//
//
//import java.net.URI;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//
//
////https://medium.com/@cheron.antoine/tuto-building-a-reactive-restful-api-with-spring-webflux-java-258fd4dbae41
//
//@Component
//@Slf4j
//public class GreetingHandler {
//
//    private final DocReactiveRepository repository;
//
//    public GreetingHandler(DocReactiveRepository carCrudRepository) {
//        this.repository = carCrudRepository;
//    }
//
//    public Mono<ServerResponse> hello(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//                .body(BodyInserters.fromValue("Hello, " + request.pathVariable("name")));
//    }
//
//    public Mono<ServerResponse> hellos(ServerRequest request) {
//        return ServerResponse.ok().contentType(APPLICATION_JSON).body(repository.findAll(), Doc.class);
//    }
//
//    public Mono<ServerResponse> post(ServerRequest serverRequest) {
////        Mono<Car> carMonoSaved = serverRequest.bodyToMono(Car.class).flatMap(repository::save);
////        return ServerResponse.created(URI.create("/hello/")).body(carMonoSaved, Car.class);
//
//        return serverRequest.bodyToMono(Doc.class)
//                .flatMap(repository::save)
//                .flatMap(car -> { log.info("Saved " + car.toString()); return Mono.just(car);})
//                .flatMap(c -> ServerResponse.created(URI.create("/hello/" + c.getId()))
//                        .contentType(APPLICATION_JSON).body(Mono.just(c), Doc.class));
//    }
//}