package sn.meum.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.meum.billingservice.entities.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,String> {

    List<Invoice> findByCustomerId(String id);
}
