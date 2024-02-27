package pc05401.assignment.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
	
    @Column(name = "name", unique = true, nullable = false)
    @Nationalized
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "note")
    @Nationalized
    private String note;
    
    @Column(name = "image", unique = true, nullable = false)
    @Nationalized
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false)
    private TagProduct tag;

    public Product() {
    	
    }
    
	public Product(int productId, String name, double price, String note, String image, TagProduct tag) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.note = note;
		this.image = image;
		this.tag = tag;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public TagProduct getTag() {
		return tag;
	}

	public void setTag(TagProduct tag) {
		this.tag = tag;
	}
    
    
}
