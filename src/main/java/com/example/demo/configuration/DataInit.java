package com.example.demo.configuration;

import com.example.demo.domain.model.*;
import com.example.demo.domain.repository.CategoryRepo;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.domain.repository.StockRepo;
import com.example.demo.domain.repository.UserRepo;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
public class DataInit {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final StockRepo stockRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public DataInit(ProductRepo productRepo, CategoryRepo categoryRepo, StockRepo stockRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.stockRepo = stockRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @PostConstruct  //po utworzeniu beana zostanie wywołana ta metoda
    public void init(){
        ProductCategory category = new ProductCategory();
        category.setType("typA");
        category.setName("kategoria1");
        category=categoryRepo.save(category);


        Product product = new Product();
        product.setProductName("produkt1");
        product.setProductType(ProductType.NEW);
        product.setProductCategory(category);
        product.setPrice(100.00);
        Stock stock = new Stock();
        stock.setQuantity(10);
        stock= stockRepo.save(stock);
        product.setStock(stock);
        product=productRepo.save(product);

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));  // TODO: 21.03.2021  jak dziala haslowanie, gdzie jest zapisany hassh/haslo jawne, jak jest wykorzystywany:   public PasswordEncoder passwordEncoder()  return new BCryptPasswordEncoder();
        userRepo.save(user);

    }
}
