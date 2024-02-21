package pc05401.assignment.entity;

import java.util.Date;

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
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucherId")
    private int voucherId;

    @Column(name = "name", unique = true, nullable = false)
    @Nationalized
    private String name;

    @Column(name = "discount")
    private double discount;

    @Column(name = "percentDiscount")
    private double percentDiscount;

    @Column(name = "createDate", nullable = false)
    private Date createDate;

    @Column(name = "expiresAt", nullable = false)
    private Date expiresAt;
    
    @Column(name = "description")
    @Nationalized
    private String description;
    
}
