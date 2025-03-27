package com.elite.service.product.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

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
    @Field(name="brand")
    private String brandName;
    @Field(name="sku")
    private String sku;
    @CreatedDate
    @Field(name="createdAt")
    private Instant createdAt;
    @LastModifiedDate
    @Field(name="updatedAt")
    private Instant updatedAt;

}
