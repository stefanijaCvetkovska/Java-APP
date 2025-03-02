package mk.ukim.finki.javaapp.model;

import lombok.*;
import jakarta.persistence.*;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private Species species;

    @Column(length = 2500)
    private String image;

    public Product(String name, Double price, ProductCategory category, Species species, String image){
        this.name = name;
        this.price = price;
        this.category = category;
        this.species = species;
        this.image = image;
    }
}
