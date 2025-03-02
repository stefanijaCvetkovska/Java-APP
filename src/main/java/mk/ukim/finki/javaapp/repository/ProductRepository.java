package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(ProductCategory category, Pageable pageable);
    Page<Product> findAllBySpecies(Species species, Pageable pageable);
    Page<Product> findAllByNameLikeIgnoreCase(String name, Pageable pageable);

    Page<Product> findAllByCategoryAndSpecies(ProductCategory category, Species species, Pageable pageable);
    Page<Product> findAllByCategoryAndNameLikeIgnoreCase(ProductCategory category, String name, Pageable pageable);
    Page<Product> findAllBySpeciesAndNameLikeIgnoreCase(Species species, String name, Pageable pageable);

    Page<Product> findAllByCategoryAndSpeciesAndNameLikeIgnoreCase(ProductCategory category, Species species, String name, Pageable pageable);
}
