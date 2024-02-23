
package pc05401.assignment.model;

import lombok.Data;
import pc05401.assignment.entity.Product;

@Data
public class ProductCountBill {
	private int id;
	private String name;
	private String image;
	private String note;
	private int count;

	public ProductCountBill(Product product, Long count) {
		this.id = product.getProductId();
		this.name = product.getName();
		this.note = product.getNote();
		this.image = product.getImage();
		this.count = count.intValue();
	}
}
