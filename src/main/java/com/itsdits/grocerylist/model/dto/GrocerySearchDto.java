package com.itsdits.grocerylist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrocerySearchDto {

    private String name;
    private String group;
    private String subGroup;
}
