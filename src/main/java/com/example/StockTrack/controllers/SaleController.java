package com.example.StockTrack.controllers;

import com.example.StockTrack.models.Product;
import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.repository.ProductRepository;
import com.example.StockTrack.services.ProductService;
import com.example.StockTrack.services.SaleService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleController{
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
    private TextField inputCode;
    @FXML
    private Label totalSale;

    private ObservableList<Sale> listSales = FXCollections.observableArrayList();
    private Sale sale =  new Sale();

    @FXML
    public void initialize(){
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
        loadSales();
    }

    @FXML
    public void registerSale(){
        String code = inputCode.getText();
        Product product = productService.findByBarcode(code);
        sale.addProduct(product);
        totalSale.setText("Total: " + sale.getTotal());
        inputCode.clear();
    }

    @FXML
    public void loadSales(){
        List<Sale> sales  = saleService.getSales();
        System.out.println(sales);
        listSales.setAll(sales);
    }
}
