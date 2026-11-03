package ch.zli.m223.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.service.EntryService;

@Path("/statistics")
@Tag(name = "Statistics", description = "Statistics for recorded time")
public class StatisticsController {
    @Inject
    EntryService entryService;

    @GET
    @Path("/time-summaries")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Summarizes recorded time per day.")
    public Map<String, String> getTimeSummaries() {
        Map<String, String> summaries = new LinkedHashMap<>();
        entryService.getTimeSummaries().forEach(summary ->
                summaries.put(summary.getDate().toString(), summary.getDuration()));
        return summaries;
    }
}
