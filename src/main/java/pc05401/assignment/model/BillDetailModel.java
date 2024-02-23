package pc05401.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BillDetailModel {

	private int billDetailId;

	private BillModel bill;

	private ProductModel product;
}
