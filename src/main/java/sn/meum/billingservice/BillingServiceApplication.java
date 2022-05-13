package sn.meum.billingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import sn.meum.billingservice.dto.InvoiceRequestDTO;
import sn.meum.billingservice.openfeign.CustomerRestClient;
import sn.meum.billingservice.services.InvoiceService;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(InvoiceService invoiceService) {
        return args -> {
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(980000), "C01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(760000), "C01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(650000), "C02"));
        };
    }

}
