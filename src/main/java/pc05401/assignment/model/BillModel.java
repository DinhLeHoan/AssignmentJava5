package pc05401.assignment.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillModel {
   
    private int billId;

    private Date date;

    private double total;

    private double totalWithVoucher;
    private String note;

    private String customerType;

    private Integer staffId;
    
    private Boolean isPaid;
}
