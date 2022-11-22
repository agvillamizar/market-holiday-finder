package org.epam.marketholidayfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StockPrice {

    private LocalDate date;
}
