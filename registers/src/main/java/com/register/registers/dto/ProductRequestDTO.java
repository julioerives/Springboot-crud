package com.register.registers.dto;

public class ProductRequestDTO {
        private Long userId;
        private Long typeProductId;
        private String name;
        public Long getUserId() {
            return userId;
        }
    
        public void setUserId(Long userId) {
            this.userId = userId;
        }
    
        public Long getTypeProductId() {
            return typeProductId;
        }
    
        public void setTypeProductId(Long typeProductId) {
            this.typeProductId = typeProductId;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
}
