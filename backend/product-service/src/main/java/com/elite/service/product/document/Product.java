package com.elite.service.product.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="Products")
public class Product {

    @Id
    private String productId;
    @Field(name="name")
    private String productName;
    @Field(name="description")
    private String productDescription;
    @Field(name="category")
    private String productCategory;
    @Field(name="price")
    private Double productPrice;
    @Field(name="stock")
    private Integer stock;

}
