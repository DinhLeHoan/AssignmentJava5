package pc05401.assignment.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {


    private int voucherId;

    private String name;

    private double discount;

    private double percentDiscount;

    private Date createDate;

    private Date expiresAt;

    private String description;
    
}
