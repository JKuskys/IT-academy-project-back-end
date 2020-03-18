package com.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Table(name="application")
public class Application {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Vardas negali būti tuščias")
    @Size(max = 255, message = "Vardas negali būti ilgesnis nei 255 simboliai")
    @Column(name="full_name")
    private String name;

    @NotBlank(message = "Telefono numeris negali būti tuščias")
    @Size(max = 20, message = "Telefono numeris negali būti ilgesnis nei 20 simbolių")
    @Column(name="phone_number")
    private String phoneNumber;

    @NotBlank(message = "Mokymosi įstaiga negali būti tuščia")
    @Size(max = 255, message = "Mokymosi įstaigos pavadinimas negali būti ilgesnis nei 255 simboliai")
    @Column(name="education")
    private String education;

    @NotBlank(message = "Pomėgių laukas negali būti tuščias")
    @Size(max = 1500, message = "Pomėgių lauke negali būti daugiau nei 1500 simbolių")
    @Column(name="free_time")
    private String freeTime;

    @NotNull(message = "Ar reikalinga trišalė sutartis privalo būti pasirinkta")
    @Column(name="agreement")
    private boolean agreement;

    @Size(max = 1500, message = "Komentaras negali būti ilgesnis nei 1500 simboliai")
    @Column(name="comment")
    private String comment;

    @NotNull(message = "Ar tinka akademijos laikas privalo būti pasirinkta")
    @Column(name="academy_time")
    private boolean academyTime;

    @NotBlank(message = "Dalyvavimo akademijoje priežastis negali būti tuščia")
    @Size(max = 255, message = "Dalyvavimo akademijoje priežastis negali būti ilgesnė nei 1500 simboliai")
    @Column(name="reason")
    private String reason;

    @NotBlank(message = "Technologijų ir patirties laukas negali būti tuščias")
    @Size(max = 1500, message = "Technologijų ir patirties lauke negali būti daugiau nei 1500 simbolių")
    @Column(name="technologies")
    private String technologies;

    @NotBlank(message = "Iš kur sužinojai apie akademiją negali būti tuščias")
    @Size(max = 255, message = "Iš kur sužinojai apie akademiją negali būti ilgesnis nei 255 simboliai")
    @Column(name="source")
    private String source;

    @NotBlank(message = "Paraiškos data negali būti tuščia")
    @Column(name="application_date")
    private String applicationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}