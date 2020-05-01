package se.iths.martin.hello.mappers;

import org.springframework.stereotype.Service;
import se.iths.martin.hello.models.Doc;
import se.iths.martin.hello.models.Sample;

//https://www.baeldung.com/java-performance-mapping-frameworks

@Service
public class ModelMapper {
    public Doc map(Sample sample) {
        return new Doc(null,sample.getUpdated(),sample.getStationID(),sample.getName(),sample.getHeading(),sample.getValue().split(" ")[1]);
    }
}
