package com.example.StockTrack.models;

import jakarta.persistence.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private LongProperty idProperty = new SimpleLongProperty();

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleDetail> details;

    @Transient
    private ListProperty<SaleDetail> detailsProperty  = new SimpleListProperty<>();

    @Column(nullable = false)
    private BigDecimal total;

    @Transient
    private DoubleProperty totalProperty = new SimpleDoubleProperty();

    public Sale(){}

    @PostLoad
    public void initProperties() {
        this.idProperty.set(this.id);
        this.totalProperty.set(this.total.doubleValue());
    }

    public Sale(List<SaleDetail> details, BigDecimal total) {
        this.details = details;
        this.detailsProperty.set(FXCollections.observableArrayList(details));
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.idProperty.set(id);
    }

    public LongProperty idProperty(){
        return idProperty;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetail> details) {
        this.details = details;
        this.detailsProperty.set(FXCollections.observableArrayList(details));
    }

    public BigDecimal getTotal() {
        return total;
    }

    public DoubleProperty getTotalProperty(){return totalProperty;}

    public void setTotal(BigDecimal total) {
        this.total = total;
        this.totalProperty.set(total.doubleValue());
    }

    public void addProduct(Product product){
        SaleDetail detail = new SaleDetail(this, product, 1, product.getPrice());
        this.details.add(detail);
        this.total = this.total.add(product.getPrice());
    }
}
