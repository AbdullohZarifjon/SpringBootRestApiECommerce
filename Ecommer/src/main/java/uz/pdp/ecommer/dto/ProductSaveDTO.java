package uz.pdp.ecommer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDTO {
    private Integer productId;
    private String name;
    private Integer price;
    private Integer categoryId;
    private Integer attachmentId;
}
