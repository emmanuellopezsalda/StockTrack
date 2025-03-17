package com.example.StockTrack.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

public class DatabaseConfig {
    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/stocktrack?createDatabaseIfNotExist=true")
                .username("root")
                .password("")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setShowSql(true);
            adapter.setGenerateDdl(true);
            return adapter;
    }
}
