package sn.meum.billingservice.mapper;

import org.mapstruct.Mapper;
import sn.meum.billingservice.dto.InvoiceRequestDTO;
import sn.meum.billingservice.dto.InvoiceResponseDTO;
import sn.meum.billingservice.entities.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice fromInvoiceRequestDTOToInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO fromInvoiceToInvoiceResponseDTO(Invoice invoice);
}
