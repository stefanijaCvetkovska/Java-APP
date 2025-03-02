package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    Page<Product> listAll(int pageNo, int pageSize);

    Page<Product> listAllByCategoryAndSpeciesAndNameLike(int pageNo, int pageSize, ProductCategory category, Species species, String name);

    Page<Product> listAllByCategory(int pageNo, int pageSize, ProductCategory category);

    Page<Product> listAllBySpecies(int pageNo, int pageSize, Species species);

    Product create(String name, Double price, ProductCategory category, Species species, String image);

    Product update(Long id, String name, Double price, ProductCategory category, Species species, String image);

    Product delete(Long id);
}
