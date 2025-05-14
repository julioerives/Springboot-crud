package com.register.registers.services.purchases;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.register.registers.dto.MultiplePurchasesRequestDTO;
import com.register.registers.dto.PurchaseRequestDTO;
import com.register.registers.entities.Product;
import com.register.registers.entities.Purchases;
import com.register.registers.entities.Users;
import com.register.registers.repositories.PurchasesRepository;
import com.register.registers.services.products.ProductService;
import com.register.registers.services.users.UserService;
import com.register.registers.services.users.UserTokenService;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class PurchasesService {
    @Autowired
    private PurchasesRepository purchasesRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    UserTokenService userTokenService;

    public Purchases addPurchases(PurchaseRequestDTO purchaseRequestDTO,  HttpServletRequest request){
        Users user = userTokenService.getCurrentUser(request);
        Product product = productService.getProducyById(purchaseRequestDTO.getProductId());
        Purchases purchase = new Purchases();
        purchase.setUser(user);
        purchase.setProduct(product);
        purchase.setQuantity(purchaseRequestDTO.getQuantity());
        purchase.setPrice(purchaseRequestDTO.getPrice());
        return purchase;
    }

    public List<Purchases> addMultiplePurchase(MultiplePurchasesRequestDTO pDto, HttpServletRequest request) {
        Users user = userTokenService.getCurrentUser(request);
        Set<Long> productIds = pDto.getItems().stream()
        .map(PurchaseRequestDTO::getProductId)
        .collect(Collectors.toSet());
        Map<Long, Product> productsMap = productService.findAllByIdIn(productIds)
        .stream()
        .collect(Collectors.toMap(Product::getProductId, Function.identity()));
        List<Purchases> purchasesList = pDto.getItems().stream()
        .map(item -> {
            Purchases purchase = new Purchases();
            purchase.setUser(user);
            purchase.setDescription(item.getDescription());
            purchase.setProduct(productsMap.get(item.getProductId()));
            purchase.setQuantity(item.getQuantity());
            purchase.setPrice(item.getPrice());
            return purchase;
        })
        .collect(Collectors.toList());

    return purchasesRepository.saveAll(purchasesList);
    }
    public Page<Purchases> getPurchases(HttpServletRequest request, String sort, int page, int size, String name, String searchBy){
        System.out.println(name);
        System.out.println(sort);
        System.out.println(page);
        System.out.println(size);
        System.out.println(searchBy);;
        Long userId = userTokenService.getCurrentUserId(request);
        System.out.println(userId);
        userService.findUserById(userId);
        Page<Purchases> purchases = purchasesRepository.findByFilters(name, searchBy, userId, PageRequest.of(page, size, getSort(sort)));
        System.out.println(purchases.getContent());
        return purchases;
    }
    private Sort getSort(String sortBy) {
    return switch (sortBy) {
        case "oldest" -> Sort.by(Sort.Direction.DESC, "purchaseDate");
        case "quantity" -> Sort.by(Sort.Direction.DESC, "quantity");
        case "price" -> Sort.by(Sort.Direction.DESC, "price");
        default -> Sort.by(Sort.Direction.ASC, "purchaseDate");
    };
}
}
