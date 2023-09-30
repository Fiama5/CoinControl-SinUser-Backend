package tup.CoinControlSinUserBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Funds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "predefined_fund_id")
    private PredefinedFund predefinedFund;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Funds() {
    }

    public Funds(Long id, double amount, PredefinedFund predefinedFund, User user) {
        this.id = id;
        this.amount = amount;
        this.predefinedFund = predefinedFund;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PredefinedFund getPredefinedFund() {
        return predefinedFund;
    }

    public void setPredefinedFund(PredefinedFund predefinedFund) {
        this.predefinedFund = predefinedFund;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   
}
