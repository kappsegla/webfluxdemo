
package se.iths.martin.hello.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Sample {

    private long calm;
    private long heading;
    private String msg;
    private String name;
    private String quality;
    private long stationID;
    private String trend;
    private String type;
    private String unit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;
    private String value;
    private Object waterLevelOffset;
    private Object waterLevelReference;

}
