package pc05401.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientModel {
   
    private int ingredientId;
    
    private String name;

    private String unit;


    private int amount;


    private int minAmount;

    private String note;
}
