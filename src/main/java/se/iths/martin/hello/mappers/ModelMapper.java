package se.iths.martin.hello.mappers;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import se.iths.martin.hello.models.Doc;
import se.iths.martin.hello.models.Sample;
import se.iths.martin.hello.records.Measure;

//https://www.baeldung.com/java-performance-mapping-frameworks

@Service
public class ModelMapper {
    public Doc map(Sample sample) {
        return new Doc(null,sample.getUpdated(),sample.getStationID(),sample.getName(),sample.getHeading(),sample.getValue().split(" ")[1]);
    }

    public Flux<Measure> map(Flux<Doc> docFlux){
        return docFlux.map(doc -> new Measure(doc.getUpdated(),doc.getValue()));
    }
}
