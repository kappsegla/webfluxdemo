//package se.iths.martin.hello;
//
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import se.iths.martin.hello.models.Doc;
//
//
//@RestController
//public class DocController {
//
//    private final DocReactiveRepository repository;
//
//    public DocController(DocReactiveRepository repository) {
//        this.repository = repository;
//    }
//
//    @PostMapping("/car")
//    Mono<Void> create(@RequestBody Doc car) {
//        return repository.save(car).then();
//    }
//
//    @GetMapping("/car")
//    Flux<Doc> list() {
//        return this.repository.findAll();
//    }
//
//    @GetMapping("/car/{id}")
//    Mono<Doc> findById(@PathVariable String id) {
//        return repository.findById(id);
//    }
//}