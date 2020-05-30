package se.iths.martin.hello;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
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
import java.util.UUID;

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

    //baeldung.com/java-mqtt-client
    String publisherId = UUID.randomUUID().toString();
    private final static String TOPIC = "/test";
    IMqttClient publisher;

    public ScheduledTasks(ObjectMapper mapper, DocReactiveRepository repository, Mapper modelMapper) {
        this.mapper = mapper;
        this.reactiveRepository = repository;
        this.modelMapper = modelMapper;
        try {
            publisher = new MqttClient("tcp://192.168.1.109:1883", publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect(options);

            //Subscribe
            publisher.subscribe(TOPIC,(topic, msg)->
            {
                byte[] payload = msg.getPayload();
                String p = new String(payload);
                log.info("Recieved message: "
                 + p);

            });



        } catch (MqttException e) {
            log.debug(e.getMessage());
        }



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

        //Send message to mqtt server
        MqttMessage msg = new MqttMessage("This is a message".getBytes());
        msg.setQos(2);
        msg.setRetained(true);
        try {
            publisher.publish(TOPIC,msg);
        } catch (MqttException e) {
            log.debug(e.getMessage());
        }
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
            if (sample.getName().equals("Medelvind")) {
                reactiveRepository.save(modelMapper.map(sample))
                        .subscribe(result -> log.info("Entity has been saved: {}", result));
            }
        }
    }
}

