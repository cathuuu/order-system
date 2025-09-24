package com.example.product_service.dtos.queries;

import com.example.product_service.dtos.QueryDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class CategoryDtoQuery extends QueryDto {
    String name;
    String description;
}
