    package com.example.StockTrack.repository;

    import com.example.StockTrack.models.Product;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
        @Query("SELECT p FROM Product p WHERE p.barcode = :barcode")
        Product findByBarcode(@Param("barcode") String barcode);
    }
