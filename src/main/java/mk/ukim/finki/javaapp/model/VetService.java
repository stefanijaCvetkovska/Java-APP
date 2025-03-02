package mk.ukim.finki.javaapp.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vet_services")
public class VetService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;

    public VetService(){}
}
