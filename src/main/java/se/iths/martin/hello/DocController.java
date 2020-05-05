package se.iths.martin.hello;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.iths.martin.hello.mappers.ModelMapper;
import se.iths.martin.hello.models.Doc;
import se.iths.martin.hello.records.Measure;
import se.iths.martin.hello.repositories.DocReactiveRepository;


@RestController
public class DocController {

    private final DocReactiveRepository repository;

    private final ModelMapper modelMapper;

    public DocController(DocReactiveRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
//
//    @PostMapping("/sample")
//    Mono<Void> create(@RequestBody Doc car) {
//        return repository.save(car).then();
//    }

    @GetMapping("/samples")
    Flux<Doc> list() {
        return this.repository.findAll();
    }

    @GetMapping("/samples/{id}")
    Mono<Doc> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("/measures")
    Flux<Measure> getMeasures() {
        var list = repository.findAll();
        var m = new Measure(null,"");

        return modelMapper.map(list);
    }
}