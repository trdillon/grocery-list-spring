package com.itsdits.grocerylist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCreateDto {

    private int groceryId;
    private int UserId;
    private int quantity;
    private int price;
    private String notes;
}
