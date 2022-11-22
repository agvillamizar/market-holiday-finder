package org.epam.marketholidayfinder.reader;

import org.epam.marketholidayfinder.model.StockPrice;

import java.util.List;

public interface StockPriceFileReader {

    List<StockPrice> getStockPrices(String fileName, String separator, String dateFormat, boolean firstRowHeader);
}
