package com.example.product_service.dtos.queries;

import com.example.product_service.dtos.QueryDto;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class ProductDtoQuery extends QueryDto {

    String name;

    String description;

    BigDecimal price;

    int stock;
}
