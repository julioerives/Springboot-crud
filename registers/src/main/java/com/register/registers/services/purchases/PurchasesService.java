package com.register.registers.services.purchases;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.dto.MultiplePurchasesRequestDTO;
import com.register.registers.dto.PurchaseRequestDTO;
import com.register.registers.entities.Product;
import com.register.registers.entities.Purchases;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.repositories.PurchasesRepositoy;
import com.register.registers.services.products.ProductService;
import com.register.registers.services.users.UserService;
import com.register.registers.services.users.UserTokenService;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class PurchasesService {
    @Autowired
    private PurchasesRepositoy purchasesRepository;
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
            purchase.setProduct(productsMap.get(item.getProductId()));
            purchase.setQuantity(item.getQuantity());
            purchase.setPrice(item.getPrice());
            return purchase;
        })
        .collect(Collectors.toList());

    return purchasesRepository.saveAll(purchasesList);
    }
    public List<Purchases> getPurchases(HttpServletRequest request){
        Long userId = userTokenService.getCurrentUserId(request);
        userService.findUserById(userId);
        List<Purchases> purchases = purchasesRepository.findByUserUserId(userId).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.NO_DATA_FOUND));
        return purchases;
    }
}
