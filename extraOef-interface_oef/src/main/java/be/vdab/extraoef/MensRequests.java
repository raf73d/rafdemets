package be.vdab.extraoef;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface MensRequests {
    @GetExchange("{id}")
    Mens findById(@PathVariable  int id);
    @GetExchange
    AantalRecords telHetaantalRecords();
}
