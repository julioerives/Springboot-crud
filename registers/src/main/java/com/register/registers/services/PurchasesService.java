package com.register.registers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.dto.PurchasesRequestDTO;
import com.register.registers.entities.Product;
import com.register.registers.entities.Purchases;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.repositories.PurchasesRepositoy;
@Service
public class PurchasesService {
    @Autowired
    private PurchasesRepositoy purchasesRepositoy;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    public Purchases newPurchase(PurchasesRequestDTO pDto) {
        Product product = productService.getProducyById(pDto.getProductId());
        Users user = userService.findUserById(pDto.getUserId());
        Purchases purchase = new Purchases();
        purchase.setProduct(product);
        purchase.setUser(user);
        purchase.setPrice(pDto.getPrice());
        purchase.setQuantity(pDto.getQuantity());
        return purchasesRepositoy.save(purchase);
    }
    public List<Purchases> getPurchases(Long userId){
        userService.findUserById(userId);
        return purchasesRepositoy.findByUserUserId(userId).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.NO_DATA_FOUND));
        
    }
}
