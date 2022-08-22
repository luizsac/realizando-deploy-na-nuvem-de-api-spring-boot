package one.dio.parking.entity.dto;

import lombok.Data;

@Data
public class CreateParkingDTO {

    private String license;
    private String state;
    private String model;
    private String color;

}
