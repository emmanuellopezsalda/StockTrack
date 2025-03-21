package com.example.StockTrack.services;

import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    public Sale registerSale(List<SaleDetail> details) {
        Sale sale = new Sale();
        sale.setDateTime(LocalDateTime.now());
        sale.setDetails(details);

        BigDecimal total = BigDecimal.ZERO;
        for (SaleDetail detail : details) {
            detail.setSale(sale);
            total = total.add(detail.getSubTotal());
        }
        sale.setTotal(BigDecimal.valueOf(total.doubleValue()));

        return saleRepository.save(sale);
    }

    @Transactional
    public List<Sale> getSales(){
        List<Sale> sales = saleRepository.findAll();
        sales.forEach(sale -> Hibernate.initialize(sale.getDetails()));
        return sales;
    }
}
