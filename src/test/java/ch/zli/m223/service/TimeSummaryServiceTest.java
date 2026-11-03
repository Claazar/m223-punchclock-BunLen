package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Entry;

public class TimeSummaryServiceTest {
    private final TimeSummaryService timeSummaryService = new TimeSummaryService();

    @Test
    public void calculatesDailySummariesAndSplitsEntriesAtMidnight() {
        Entry overnightEntry = new Entry();
        overnightEntry.setCheckIn(LocalDateTime.of(2024, 8, 20, 22, 30));
        overnightEntry.setCheckOut(LocalDateTime.of(2024, 8, 21, 1, 15));

        Entry sameDayEntry = new Entry();
        sameDayEntry.setCheckIn(LocalDateTime.of(2024, 8, 20, 8, 0));
        sameDayEntry.setCheckOut(LocalDateTime.of(2024, 8, 20, 16, 0));

        var summaries = timeSummaryService.calculateSummaryPerDay(List.of(overnightEntry, sameDayEntry));

        assertEquals(2, summaries.size());
        assertEquals("2024-08-20", summaries.get(0).getDate().toString());
        assertEquals("09:30:00", summaries.get(0).getDuration());
        assertEquals("2024-08-21", summaries.get(1).getDate().toString());
        assertEquals("01:15:00", summaries.get(1).getDuration());
    }

    @Test
    public void formatsDurationsWithMoreThanTwentyFourHours() {
        Entry firstEntry = new Entry();
        firstEntry.setCheckIn(LocalDateTime.of(2024, 8, 20, 0, 0));
        firstEntry.setCheckOut(LocalDateTime.of(2024, 8, 20, 14, 0));

        Entry secondEntry = new Entry();
        secondEntry.setCheckIn(LocalDateTime.of(2024, 8, 20, 10, 0));
        secondEntry.setCheckOut(LocalDateTime.of(2024, 8, 20, 23, 0));

        var summaries = timeSummaryService.calculateSummaryPerDay(List.of(firstEntry, secondEntry));

        assertEquals("27:00:00", summaries.get(0).getDuration());
    }
}
