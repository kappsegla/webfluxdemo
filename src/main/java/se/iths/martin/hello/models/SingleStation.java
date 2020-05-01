
package se.iths.martin.hello.models;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class SingleStation {
    private float id;
    private String name;
    List<Sample> samples;
    private String felmeddelande = null;
}
