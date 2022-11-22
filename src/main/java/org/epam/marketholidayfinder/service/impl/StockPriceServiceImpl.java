package org.epam.marketholidayfinder.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.epam.marketholidayfinder.model.StockPrice;
import org.epam.marketholidayfinder.service.StockPriceService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StockPriceServiceImpl implements StockPriceService {

    @Override
    public List<LocalDate> getMarketHolidays(List<StockPrice> stockPrices) {
        log.debug("processing {} stock prices", stockPrices.size());

        List<LocalDate> holidays = new ArrayList<>();

        // iterating over stock prices
        for (int i = 0; i < stockPrices.size() - 1; i++) {
            LocalDate currentDate = stockPrices.get(i).getDate();
            LocalDate nextDay = stockPrices.get(i + 1).getDate();

            // getting the difference in days between stock prices dates
            long dayDiff = Period.between(currentDate, nextDay).getDays();

            log.debug("processing current date {}, next day {}, dayDiff {}", currentDate, nextDay, dayDiff);

            // if the difference is greater than 1, there is a weekend day / holiday
            for (int j = 1; j < dayDiff; j++) {
                // getting the weekend day / holiday
                LocalDate noStockDate = currentDate.plusDays(j);

                log.debug("checking if {} is a holiday", noStockDate);
                // checking if there is a holiday (no saturday nor sunday)
                if (DayOfWeek.SATURDAY != noStockDate.getDayOfWeek()
                        && DayOfWeek.SUNDAY != noStockDate.getDayOfWeek()) {
                    log.debug("holiday {} found", noStockDate);
                    holidays.add(noStockDate);
                }
            }
        }

        return holidays;
    }
}
