package org.epam.marketholidayfinder;

import lombok.extern.slf4j.Slf4j;
import org.epam.marketholidayfinder.model.StockPrice;
import org.epam.marketholidayfinder.reader.StockPriceFileReader;
import org.epam.marketholidayfinder.reader.impl.StockPriceCSVReader;
import org.epam.marketholidayfinder.service.StockPriceService;
import org.epam.marketholidayfinder.service.impl.StockPriceServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class Main {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final boolean FIRST_ROW_IS_HEADER = true;
    private static final String STOCK_PRICES_FILE_NAME = "EPAM.csv";
    private static final String STOCK_PRICES_FILE_SEPARATOR = ",";

    public static void main(String[] args) {

        try {
            // read the stock prices csv file
            StockPriceFileReader reader = new StockPriceCSVReader();
            List<StockPrice> stockPrices =
                    reader.getStockPrices(STOCK_PRICES_FILE_NAME, STOCK_PRICES_FILE_SEPARATOR, DATE_FORMAT, FIRST_ROW_IS_HEADER);

            // calculate the market holidays
            StockPriceService stockPriceService = new StockPriceServiceImpl();
            List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

            // printing results
            marketHolidays.forEach(holiday -> log.info("market holiday found: {}", holiday.toString()));
        } catch (Exception ex) {
            log.error("Error getting market holidays...", ex);
        }
    }
}