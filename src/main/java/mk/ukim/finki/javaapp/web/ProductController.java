package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String getProducts(HttpServletRequest request, Model model) {
        return findPaginatedProducts(1, null, null, null, model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginatedProducts(@PathVariable(value = "pageNo") int pageNum,
                                        @RequestParam(required = false) ProductCategory category,
                                        @RequestParam(required = false) Species species,
                                        @RequestParam(required = false) String name,
                                        Model model) {

        // dodadi HttpServletRequest request i request
//        String email = request.getRemoteUser();
//        User user = this.userRepository.findByEmail(email);
//        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getId());
//        model.addAttribute("productsInCart", shoppingCart.getProducts().size());

        int pageSize = 8;
        Page<Product> page;
        page = this.productService.listAllByCategoryAndSpeciesAndNameLike(pageNum, pageSize, category, species, name);
        List<Product> products = page.getContent();
        List categoriesList = Arrays.asList(ProductCategory.values());
        List speciesList = Arrays.asList(Species.values());

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("category", category);
        model.addAttribute("species", species);
        model.addAttribute("name", name);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("speciesList", speciesList);

        model.addAttribute("bodyContent", "products");
        return "master-template";
    }
}
