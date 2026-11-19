package com.substring.easybuy.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;

    //@NotNull -> does not allow - null, allow - blank(size = 0 string)
    //@NotBlank -> does not allow both null and blank(size 0 string)
    //@Positive -> spring allows values which are >0
    //@Negative -> spring allows values which are <0
    //@Size -> for string values
    //@Min, @Max - for numeric values
    // All from jakarta.validation.constraints
    // require dependency - spring-boot-starter-validation
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "shortDesc is required")
    @Size(max = 500, message = "shortDesc must be at most 500 characters")
    private String shortDesc;

    @NotBlank(message = "longDesc is required")
    private String longDesc;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private Double price;

    @Min(value = 0, message = "discount must be greater than or equal to 0")
    @Max(value = 100, message = "discount must be less than or equal to 100")
    private Integer discount;
    private Boolean live;
    private List<String> productImages;
    private List<CategoryDto> categories;
    private List<ReviewDto> reviews;
}
