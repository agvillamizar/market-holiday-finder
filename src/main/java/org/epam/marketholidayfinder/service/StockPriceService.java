package org.epam.marketholidayfinder.service;

import org.epam.marketholidayfinder.model.StockPrice;

import java.time.LocalDate;
import java.util.List;

public interface StockPriceService {

    List<LocalDate> getMarketHolidays(List<StockPrice> stockPrices);
}
