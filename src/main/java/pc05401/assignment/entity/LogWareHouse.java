package pc05401.assignment.entity;

import java.util.Date;

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
@Table(name = "logWareHouse")
public class LogWareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logId")
    private int logId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description")
    @Nationalized
    private String description;

    @ManyToOne
    @JoinColumn(name = "staffId", nullable = false)
    private Staff staff;
    
    @ManyToOne
    @JoinColumn(name = "ingredientId", nullable = false)
    private Ingredient ingredient;
}
