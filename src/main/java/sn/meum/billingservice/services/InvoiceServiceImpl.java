package sn.meum.billingservice.services;

import org.springframework.stereotype.Service;
import sn.meum.billingservice.dto.InvoiceRequestDTO;
import sn.meum.billingservice.dto.InvoiceResponseDTO;
import sn.meum.billingservice.entities.Customer;
import sn.meum.billingservice.entities.Invoice;
import sn.meum.billingservice.exceptions.CustomerNotFoundException;
import sn.meum.billingservice.mapper.InvoiceMapper;
import sn.meum.billingservice.openfeign.CustomerRestClient;
import sn.meum.billingservice.repository.InvoiceRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerRestClient customerRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
         /*
        Verification de l'integrité référentielle Invoice / Costumer
         */
        Customer customer=null;
        try{
             customer = customerRestClient.getCustomer(invoiceRequestDTO.getCustomerId());
        }catch (Exception e){
           throw new CustomerNotFoundException("Customer not found");
        }

        Invoice invoice = invoiceMapper.fromInvoiceRequestDTOToInvoice(invoiceRequestDTO);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());
        Invoice invoiceSaved = invoiceRepository.save(invoice);
        invoiceSaved.setCustomer(customer);
        return invoiceMapper.fromInvoiceToInvoiceResponseDTO(invoiceSaved);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
        invoice.setCustomer(customer);
        return invoiceMapper.fromInvoiceToInvoiceResponseDTO(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoicesByCustomerId(String customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        for (Invoice invoice : invoices) {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices
                .stream()
                .map(invoice ->
                        invoiceMapper.fromInvoiceToInvoiceResponseDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> allInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices) {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices
                .stream()
                .map(invoice -> invoiceMapper.fromInvoiceToInvoiceResponseDTO(invoice)).collect(Collectors.toList());
    }
}
