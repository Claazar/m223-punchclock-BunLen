import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ch.zli.m223.service.TimeSummaryService;
import ch.zli.m223.model.Entry;

import java.time.LocalDateTime;
import java.util.List;

public class TimeSummaryServiceTest {
    @Test 
    void returnsEmptyListWhenThereAreNoEntries() {
        TimeSummaryService service = new TimeSummaryService();
        List<Entry> entries = List.of();

        var result = service.calculateSummaryPerDay(entries);

        assertTrue(result.isEmpty());
    }

    @Test
    void timesAddedTogetherOnSameDay() {
        TimeSummaryService service = new TimeSummaryService();

        Entry entry1 = new Entry();
        entry1.setCheckIn(LocalDateTime.parse("2020-05-13T06:30"));
        entry1.setCheckOut(LocalDateTime.parse("2020-05-13T11:30"));

        Entry entry2 = new Entry();
        entry2.setCheckIn(LocalDateTime.parse("2020-05-13T12:30"));
        entry2.setCheckOut(LocalDateTime.parse("2020-05-13T16:20"));

        List<Entry> entries = List.of(entry1, entry2);

        var result = service.calculateSummaryPerDay(entries);

        assertEquals("08:50:00", result.get(0).getDuration());
        
    }
}
