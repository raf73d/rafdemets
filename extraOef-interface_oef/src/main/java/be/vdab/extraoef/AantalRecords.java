package be.vdab.extraoef;


import com.fasterxml.jackson.annotation.JsonProperty;

public record AantalRecords(@JsonProperty("total_records") int totalRecords) {
}
