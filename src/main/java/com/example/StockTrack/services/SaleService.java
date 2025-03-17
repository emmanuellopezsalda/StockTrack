package com.example.StockTrack.services;

import com.example.StockTrack.models.Sale;
import com.example.StockTrack.models.SaleDetail;
import com.example.StockTrack.repository.SaleRepository;
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

}
