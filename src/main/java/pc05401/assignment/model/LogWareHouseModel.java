package pc05401.assignment.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogWareHouseModel {

    private int logId;

    private Date date;

    private String description;

    private StaffModel staff;

    private IngredientModel ingredient;
}
