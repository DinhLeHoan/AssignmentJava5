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
    
    @Column(name = "active")
    private boolean active ;

    @Column(name = "note")
    @Nationalized
    private String note;
    
    @Column(name = "image", unique = true, nullable = false)
    @Nationalized
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false)
    private TagProduct tag;
    
}
