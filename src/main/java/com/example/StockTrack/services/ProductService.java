package com.example.StockTrack.services;

import com.example.StockTrack.models.Product;
import com.example.StockTrack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findByBarcode(String barcode){
        return productRepository.findByBarcode(barcode);
    }
}
