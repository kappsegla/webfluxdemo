package se.iths.martin.hello;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import se.iths.martin.hello.mappers.Mapper;
import se.iths.martin.hello.models.Sample;
import se.iths.martin.hello.models.SingleStation;
import se.iths.martin.hello.repositories.DocReactiveRepository;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Component
public class ScheduledTasks {


    @Value("${viva.uri:https://localhost:8080/}")
    private String vivaUri;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    final
    ObjectMapper mapper;
    final
    DocReactiveRepository reactiveRepository;

    private final Mapper modelMapper;


    public ScheduledTasks(ObjectMapper mapper, DocReactiveRepository repository, Mapper modelMapper) {
        this.mapper = mapper;
        this.reactiveRepository = repository;
        this.modelMapper = modelMapper;
    }

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        log.info("The time is now {}", LocalDateTime.now());
        WebClient client = WebClient.create();

        client.get()
                .uri(URI.create(vivaUri))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(this::doIt)
                .subscribe();
    }

    private void doIt(String json) {
        log.info(json);
        try {
            JsonNode jsonNode = mapper.readTree(json);
            var v = mapper.readTree(json).get("GetSingleStationResult");

            SingleStation singleStation = mapper.treeToValue(v, SingleStation.class);
            SaveToDatabase(singleStation);

            //System.out.println(singleStation.getSamples());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SaveToDatabase(SingleStation singleStation) {
        //Find wind sample, convert to storage format and save.
//
//        singleStation.getSamples().stream()
//                .filter(sample -> sample.getName().equals("Medelvind"))
//                .map(modelMapper::map)
//                .
        for (Sample sample : singleStation.getSamples()) {
            if ( sample.getName().equals("Medelvind") ) {
                reactiveRepository.save( modelMapper.map(sample) )
                        .subscribe(result -> log.info("Entity has been saved: {}", result));
            }
        }
    }
}

