package pc05401.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class VoucherDetailModel {

    private int voucherDetailId;

    private Integer billID;

    private Voucher voucher;

}
