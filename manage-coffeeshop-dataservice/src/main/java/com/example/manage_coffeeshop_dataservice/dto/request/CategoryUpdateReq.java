package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class CategoryUpdateReq {
    private String categoryName;
    private String categoryDescription;
}
