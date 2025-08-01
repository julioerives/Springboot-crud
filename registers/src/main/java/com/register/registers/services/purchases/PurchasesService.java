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
import org.springframework.transaction.annotation.Transactional;

import com.register.registers.dto.MultiplePurchasesRequestDTO;
import com.register.registers.dto.PurchaseRequestDTO;
import com.register.registers.dto.PurchasesResponseDTO;
import com.register.registers.entities.postgres.Product;
import com.register.registers.entities.postgres.Purchases;
import com.register.registers.entities.postgres.Users;
import com.register.registers.mappers.PurchaseMapper;
import com.register.registers.repositories.postgres.PurchasesRepository;
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
    private UserTokenService userTokenService;
    @Autowired
    private PurchaseMapper purchaseMapper;

    public Purchases addPurchases(PurchaseRequestDTO purchaseRequestDTO, HttpServletRequest request) {
        Users user = userTokenService.getCurrentUser(request);
        Product product = productService.getProducyById(purchaseRequestDTO.getProductId());
        Purchases purchase = new Purchases();
        purchase.setUser(user);
        purchase.setProduct(product);
        purchase.setQuantity(purchaseRequestDTO.getQuantity());
        purchase.setPrice(purchaseRequestDTO.getPrice());
        return purchase;
    }

    @Transactional
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
                    purchase.setPurchaseDate(item.getPurchaseDate());
                    purchase.setDescription(item.getDescription());
                    purchase.setProduct(productsMap.get(item.getProductId()));
                    purchase.setQuantity(item.getQuantity());
                    purchase.setPrice(item.getPrice());
                    return purchase;
                })
                .collect(Collectors.toList());

        return purchasesRepository.saveAll(purchasesList);
    }

    public Page<PurchasesResponseDTO> getPurchases(
            HttpServletRequest request,
            String sort,
            int page,
            int size,
            String name,
            String searchBy) {

        Long userId = userTokenService.getCurrentUserId(request);
        userService.findUserById(userId);

        Page<Purchases> purchases = purchasesRepository.findByFilters(
                name, searchBy, userId, PageRequest.of(page, size, getSort(sort)));

        return purchases.map(purchaseMapper::toDto);
    }

    private Sort getSort(String sortBy) {
        return switch (sortBy) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "purchaseDate");
            case "quantity" -> Sort.by(Sort.Direction.DESC, "quantity");
            case "price" -> Sort.by(Sort.Direction.DESC, "price");
            default -> Sort.by(Sort.Direction.DESC, "purchaseDate");
        };
    }
}
