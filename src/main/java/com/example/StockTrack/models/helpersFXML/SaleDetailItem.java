package com.example.StockTrack.models.helpersFXML;

import com.example.StockTrack.models.Product;
import com.example.StockTrack.models.SaleDetail;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class SaleDetailItem {
    private Product product;
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty(1);
    private Button removeButton;
    private BigDecimal subtotal;
    private Consumer<SaleDetailItem> onRemoveAction;

    public SaleDetailItem(Product product, Consumer<SaleDetailItem> onRemoveAction) {
        this.product = product;
        this.subtotal = product.getPrice();
        this.onRemoveAction = onRemoveAction;
        this.removeButton = new Button("Remove");
        this.removeButton.setOnAction(event -> removeItem());
    }

    private void removeItem() {
        if (onRemoveAction != null) {
            onRemoveAction.accept(this);
        }
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return product.getName();
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity.set(quantity.get() + 1);
        subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity.get()));
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public SaleDetail toSaleDetail() {
        return new SaleDetail(null, product, quantity.get(),
                product.getPrice().multiply(BigDecimal.valueOf(quantity.get())));
    }
}