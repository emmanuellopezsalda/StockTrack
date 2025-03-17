package com.example.StockTrack.controllers;

import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.repository.ProductRepository;
import com.example.StockTrack.services.SaleService;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleController{
    @Autowired
    private SaleService saleService;

    @FXML
    private TableView<Sale> tableSales;
}
