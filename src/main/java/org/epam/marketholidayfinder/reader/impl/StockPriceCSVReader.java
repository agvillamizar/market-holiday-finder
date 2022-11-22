package org.epam.marketholidayfinder.reader.impl;

import lombok.extern.slf4j.Slf4j;
import org.epam.marketholidayfinder.model.StockPrice;
import org.epam.marketholidayfinder.reader.StockPriceFileReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StockPriceCSVReader implements StockPriceFileReader {

    @Override
    public List<StockPrice> getStockPrices(String fileName, String separator, String dateFormat, boolean firstRowHeader) {
        log.info("reading stock prices file {}", fileName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        List<StockPrice> stockPrices = new ArrayList<>();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream != null) {

            try (InputStreamReader streamReader =
                         new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                if (firstRowHeader) {
                    log.debug("skipping headers first row");
                    reader.readLine();
                }

                String fileRow;
                while ((fileRow = reader.readLine()) != null) {
                    String[] fileColumns = fileRow.split(separator);

                    StockPrice stockPrice = new StockPrice(LocalDate.parse(fileColumns[0], formatter));
                    stockPrices.add(stockPrice);
                }
            } catch (Exception e) {
                log.error("error reading stock prices from file {}", fileName, e);
            }

        } else {
            log.error("stock prices file {} not found", fileName);
        }

        log.info("stock prices file {} loaded with {} lines", fileName, stockPrices.size());

        return stockPrices;
    }
}

