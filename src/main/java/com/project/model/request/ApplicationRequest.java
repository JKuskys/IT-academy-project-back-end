package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @NotBlank(message = "Telefono numeris negali būti tuščias")
    @Size(max = 20, message = "Telefono numeris negali būti ilgesnis nei 20 simbolių")
    private String phoneNumber;

    @NotBlank(message = "Mokymosi įstaiga negali būti tuščia")
    @Size(max = 255, message = "Mokymosi įstaigos pavadinimas negali būti ilgesnis nei 255 simboliai")
    private String education;

    @NotBlank(message = "Pomėgių laukas negali būti tuščias")
    @Size(max = 1500, message = "Pomėgių lauke negali būti daugiau nei 1500 simbolių")
    private String hobbies;

    @NotNull(message = "Ar reikalinga trišalė sutartis privalo būti pasirinkta")
    private boolean isAgreementNeeded;

    @Size(max = 1500, message = "Komentaras negali būti ilgesnis nei 1500 simboliai")
    private String comment;

    @NotNull(message = "Ar tinka akademijos laikas privalo būti pasirinkta")
    private boolean isAcademyTimeSuitable;

    @NotBlank(message = "Dalyvavimo akademijoje priežastis negali būti tuščia")
    @Size(max = 1500, message = "Dalyvavimo akademijoje priežastis negali būti ilgesnė nei 1500 simboliai")
    private String reason;

    @NotBlank(message = "Technologijų ir patirties laukas negali būti tuščias")
    @Size(max = 1500, message = "Technologijų ir patirties lauke negali būti daugiau nei 1500 simbolių")
    private String technologies;

    @NotBlank(message = "Iš kur sužinojai apie akademiją negali būti tuščias")
    @Size(max = 255, message = "Iš kur sužinojai apie akademiją negali būti ilgesnis nei 255 simboliai")
    private String source;

    private UserRequest user;
}
