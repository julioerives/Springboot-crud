package com.register.registers.dto;

import com.register.registers.model.projection.MostBoughtProductProjection;
import com.register.registers.model.projection.PriceStatsProjection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStatsDTO {
    PriceStatsProjection priceStatsProjection;
    MostBoughtProductProjection mostBoughtProductProjection;
}
