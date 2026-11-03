package DTO;

import java.time.LocalDate;

public class TimeSummaryDto {
    private LocalDate date;
    private String duration;

    public TimeSummaryDto(LocalDate date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
