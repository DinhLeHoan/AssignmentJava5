package pc05401.assignment.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId")
    private int billId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "totalWithVoucher", nullable = false)
    private double totalWithVoucher;
    
    @Column(name = "note")
    @Nationalized
    private String note;
    
    @Column(name = "customerType", nullable = false)
    @Nationalized
    private String customerType;

    @ManyToOne
    @JoinColumn(name = "staffId", nullable = false)
    private Staff staff;
    
    @Column(name = "isPaid", nullable = false)
    private Boolean isPaid;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VoucherDetail> voucherDetails = new HashSet<>();
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BillDetail> billDetails = new HashSet<>();

}
