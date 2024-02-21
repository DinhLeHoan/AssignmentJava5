package pc05401.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    private int productId;
	
    private String name;

    private double price;

    private String note;

    private String image;

    private TagProductModel tag;
    
}
