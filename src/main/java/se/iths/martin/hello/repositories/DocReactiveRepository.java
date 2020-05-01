package se.iths.martin.hello.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import se.iths.martin.hello.models.Doc;

public interface DocReactiveRepository extends ReactiveMongoRepository<Doc, String> {
}
