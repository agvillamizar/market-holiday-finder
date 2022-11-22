package org.epam.marketholidayfinder.service.impl;

import org.epam.marketholidayfinder.model.StockPrice;
import org.epam.marketholidayfinder.reader.StockPriceFileReader;
import org.epam.marketholidayfinder.reader.impl.StockPriceCSVReader;
import org.epam.marketholidayfinder.service.StockPriceService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockPriceServiceImplTest {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final boolean FIRST_ROW_IS_HEADER = true;
    private static final String STOCK_PRICES_FILE_SEPARATOR = ";";

    /**
     * Test provided sample
     */
    @Test
    void getMarketHolidays$1() {
        List<LocalDate> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(LocalDate.of(2022, 7, 4));
        expectedHolidays.add(LocalDate.of(2022, 7, 7));
        expectedHolidays.add(LocalDate.of(2022, 7, 8));

        StockPriceFileReader reader = new StockPriceCSVReader();
        List<StockPrice> stockPrices =
                reader.getStockPrices("stock_prices_sample.csv", STOCK_PRICES_FILE_SEPARATOR, DATE_FORMAT, FIRST_ROW_IS_HEADER);

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }

    /**
     * Test monday holidays (after weekend)
     */
    @Test
    void getMarketHolidays$2() {
        List<StockPrice> stockPrices = new ArrayList<>();
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 4)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 8)));

        List<LocalDate> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(LocalDate.of(2022, 11, 7));

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }

    /**
     * Test holidays in the middle of the week
     */
    @Test
    void getMarketHolidays$3() {
        List<StockPrice> stockPrices = new ArrayList<>();
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 4)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 8)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 14)));

        List<LocalDate> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(LocalDate.of(2022, 11, 7));
        expectedHolidays.add(LocalDate.of(2022, 11, 9));
        expectedHolidays.add(LocalDate.of(2022, 11, 10));
        expectedHolidays.add(LocalDate.of(2022, 11, 11));

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }

    /**
     * Test holidays when switching month
     */
    @Test
    void getMarketHolidays$4() {
        List<StockPrice> stockPrices = new ArrayList<>();
        stockPrices.add(new StockPrice(LocalDate.of(2022, 10, 27)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 10, 28)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 2)));

        List<LocalDate> expectedHolidays = new ArrayList<>();
        expectedHolidays.add(LocalDate.of(2022, 10, 31));
        expectedHolidays.add(LocalDate.of(2022, 11, 1));

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }

    /**
     * Test no holidays
     */
    @Test
    void getMarketHolidays$5() {
        List<StockPrice> stockPrices = new ArrayList<>();
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 4)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 5)));
        stockPrices.add(new StockPrice(LocalDate.of(2022, 11, 6)));

        List<LocalDate> expectedHolidays = new ArrayList<>();

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }

    /**
     * Test no file found
     */
    @Test
    void getMarketHolidays$6() {
        List<LocalDate> expectedHolidays = new ArrayList<>();

        StockPriceFileReader reader = new StockPriceCSVReader();
        List<StockPrice> stockPrices =
                reader.getStockPrices("stock_prices_sample_x.csv", STOCK_PRICES_FILE_SEPARATOR, DATE_FORMAT, FIRST_ROW_IS_HEADER);

        StockPriceService stockPriceService = new StockPriceServiceImpl();
        List<LocalDate> marketHolidays = stockPriceService.getMarketHolidays(stockPrices);

        assertEquals(expectedHolidays, marketHolidays);
    }
}