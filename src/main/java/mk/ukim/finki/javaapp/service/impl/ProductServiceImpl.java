package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.model.exception.ProductNotFoundException;
import mk.ukim.finki.javaapp.repository.ProductRepository;
import mk.ukim.finki.javaapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Page<Product> listAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> listAllByCategoryAndSpeciesAndNameLike(int pageNo, int pageSize, ProductCategory category, Species species, String name) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        String nameLike = "%" + name + "%";
        if (category != null && species != null && name != null) {
            return this.productRepository.findAllByCategoryAndSpeciesAndNameLikeIgnoreCase(category, species, nameLike, pageable);
        } else if (category != null && species != null) {
            return this.productRepository.findAllByCategoryAndSpecies(category, species, pageable);
        } else if (category != null && name != null) {
            return this.productRepository.findAllByCategoryAndNameLikeIgnoreCase(category, nameLike, pageable);
        } else if (species != null && name != null) {
            return this.productRepository.findAllBySpeciesAndNameLikeIgnoreCase(species, nameLike, pageable);
        } else if (category != null) {
            return this.productRepository.findAllByCategory(category, pageable);
        } else if (species != null) {
            return this.productRepository.findAllBySpecies(species, pageable);
        } else if (name != null) {
            return this.productRepository.findAllByNameLikeIgnoreCase(nameLike, pageable);
        } else {
            return this.productRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Product> listAllByCategory(int pageNo, int pageSize, ProductCategory category) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.productRepository.findAllByCategory(category, pageable);
    }

    @Override
    public Page<Product> listAllBySpecies(int pageNo, int pageSize, Species species) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.productRepository.findAllBySpecies(species, pageable);
    }

    @Override
    public Product create(String name, Double price, ProductCategory category, Species species, String image) {
        Product product = new Product(name, price, category, species, image);
        return this.productRepository.save(product);
    }

    @Override
    public Product update(Long id, String name, Double price, ProductCategory category, Species species, String image) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setSpecies(species);
        product.setImage(image);
        return this.productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = this.productRepository.findById(id).get();
        this.productRepository.delete(product);
        return product;
    }
}
