package com.example.StockTrack.controllers;

import com.example.StockTrack.models.Product;
import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.models.helpersFXML.SaleDetailItem; // Importa la clase externa
import com.example.StockTrack.services.ProductService;
import com.example.StockTrack.services.SaleService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SaleController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @FXML
    private TableView<Sale> tableSales;
    @FXML
    private TableColumn<Sale, Long> colId;
    @FXML
    private TableColumn<Sale, Double> colTotal;
    @FXML
    private TableColumn<Sale, LocalDateTime> colDate;
    @FXML
    private TableColumn<Sale, String> colProducts;
    @FXML
    private TableView<SaleDetailItem> tableCurrentSale;
    @FXML
    private TableColumn<SaleDetailItem, String> colName;
    @FXML
    private TableColumn<SaleDetailItem, BigDecimal> colPrice;
    @FXML
    private TableColumn<SaleDetailItem, Integer> colQuantity;
    @FXML
    private TableColumn<SaleDetailItem, Button> colActions;
    @FXML
    private TextField inputCode;
    @FXML
    private Label totalSale;

    private ObservableList<Sale> listSales = FXCollections.observableArrayList();
    private ObservableList<SaleDetailItem> currentSaleItems = FXCollections.observableArrayList();
    private Map<Long, SaleDetailItem> productMap = new HashMap<>();
    private BigDecimal currentTotal = BigDecimal.ZERO;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colProducts.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getDetails().stream()
                        .map(saleDetail -> saleDetail.getProduct().getName())
                        .reduce((p1, p2) -> p1 + ", " + p2)
                        .orElse("Sin productos")
        ));
        colTotal.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty().asObject());
        colDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateTime()));
        tableSales.setItems(listSales);

        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        colActions.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRemoveButton()));
        tableCurrentSale.setItems(currentSaleItems);

        loadSales();
        totalSale.setText("$0.00");
    }

    @FXML
    public void registerSale() {
        String code = inputCode.getText().trim();
        if (code.isEmpty()) {
            return;
        }
        Product product = productService.findByBarcode(code);

        if (product == null) {
            showAlert("Product not found", "No product found with barcode: " + code);
            return;
        }
        if (productMap.containsKey(product.getId())) {
            SaleDetailItem item = productMap.get(product.getId());
            item.incrementQuantity();
        } else {
            SaleDetailItem newItem = new SaleDetailItem(product, this::handleItemRemoval);
            currentSaleItems.add(newItem);
            productMap.put(product.getId(), newItem);
        }
        updateTotal();
        inputCode.clear();
        inputCode.requestFocus();
    }

    private void handleItemRemoval(SaleDetailItem item) {
        currentSaleItems.remove(item);
        productMap.remove(item.getProduct().getId());
        updateTotal();
    }

    private void updateTotal() {
        currentTotal = BigDecimal.ZERO;
        for (SaleDetailItem item : currentSaleItems) {
            currentTotal = currentTotal.add(
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        totalSale.setText(String.format("$%.2f", currentTotal));
    }

    @FXML
    public void finalizeSale() {
        if (currentSaleItems.isEmpty()) {
            showAlert("Empty Cart", "Please add products before finalizing the sale.");
            return;
        }
        List<SaleDetail> details = new ArrayList<>();
        for (SaleDetailItem item : currentSaleItems) {
            details.add(item.toSaleDetail());
        }
        try {
            Sale sale = saleService.registerSale(details);
            showAlert("Sale Completed", "Sale #" + sale.getId() + " has been successfully registered.",
                    Alert.AlertType.INFORMATION);

            currentSaleItems.clear();
            productMap.clear();
            updateTotal();
            loadSales();
        } catch (Exception e) {
            showAlert("Error", "Failed to register sale: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void loadSales() {
        List<Sale> sales = saleService.getSales();
        listSales.setAll(sales);
    }

    private void showAlert(String title, String message) {
        showAlert(title, message, Alert.AlertType.WARNING);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}