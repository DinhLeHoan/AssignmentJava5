package pc05401.assignment.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredientId")
    private int ingredientId;
    
    @Column(name = "name", unique = true, nullable = false)
    @Nationalized
    private String name;
    
    @Column(name = "unit", nullable = false)
    @Nationalized
    private String unit;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "minAmount", nullable = false)
    private int minAmount;

    @Column(name = "note")
    private String note;
}
