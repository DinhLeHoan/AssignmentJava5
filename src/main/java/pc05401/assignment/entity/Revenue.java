package pc05401.assignment.entity;

import java.util.Date;

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
@Table(name = "revenue")
public class Revenue {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenueId")
    private int revenueId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "total", nullable = false)
    private double total;
}
