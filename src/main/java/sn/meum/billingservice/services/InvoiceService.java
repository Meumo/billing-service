package sn.meum.billingservice.services;

import sn.meum.billingservice.dto.InvoiceRequestDTO;
import sn.meum.billingservice.dto.InvoiceResponseDTO;
import sn.meum.billingservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String invoiceId);
    List<InvoiceResponseDTO> invoicesByCustomerId(String customerId);
    List<InvoiceResponseDTO> allInvoices();
}
