package mk.ukim.finki.javaapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointments")
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User owner;

    private String petName;

    private LocalDate date;
    private String time;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    private VetService service;

    private String reason;

    private LocalDateTime createdAt;


    public Appointment(User owner,
                       String petName,
                       LocalDate date,
                       String time,
                       BookingStatus status,
                       VetService service,
                       String reason){
        this.owner = owner;
        this.petName = petName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.service = service;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }
}
