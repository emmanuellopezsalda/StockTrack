package com.example.StockTrack.services;

import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    public Sale registerSale(List<SaleDetail> details){
        BigDecimal total = details.stream()
                .map(SaleDetail::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Sale sale = new Sale(details, total);
        return saleRepository.save(sale);
    }

    @Transactional
    public List<Sale> getSales(){
        List<Sale> sales = saleRepository.findAll();
        sales.forEach(sale -> Hibernate.initialize(sale.getDetails()));
        return sales;
    }
}
