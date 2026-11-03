package ch.zli.m223.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import DTO.TimeSummaryDto;
import ch.zli.m223.model.Entry;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeSummaryService {
    public List<TimeSummaryDto> calculateSummaryPerDay(List<Entry> entries) {
        Map<LocalDate, Long> secondsPerDay = new TreeMap<>();

        for (Entry entry : entries) {
            LocalDateTime cursor = entry.getCheckIn();
            LocalDateTime checkOut = entry.getCheckOut();
            while (cursor.isBefore(checkOut)) {
                LocalDateTime endOfDay = cursor.toLocalDate().plusDays(1).atStartOfDay();
                LocalDateTime segmentEnd = checkOut.isBefore(endOfDay) ? checkOut : endOfDay;
                long seconds = Duration.between(cursor, segmentEnd).getSeconds();
                secondsPerDay.merge(cursor.toLocalDate(), seconds, Long::sum);
                cursor = segmentEnd;
            }
        }

        return secondsPerDay.entrySet().stream()
                .map(day -> new TimeSummaryDto(day.getKey(), formatDuration(day.getValue())))
                .toList();
    }

    private String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = totalSeconds % 3600 / 60;
        long seconds = totalSeconds % 60;
        return "%02d:%02d:%02d".formatted(hours, minutes, seconds);
    }
}
