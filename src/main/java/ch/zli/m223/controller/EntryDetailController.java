package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import DTO.EntryDetailDto;
import ch.zli.m223.mapper.EntryMapper;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entry-details")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(
        name = "Entry Details",
        description = "Entries with their category and tags")
public class EntryDetailController {

    @Inject
    EntryService entryService;

    @Inject
    EntryMapper entryMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists entry details.", description = "Returns all entries with category and tags.")
    public List<EntryDetailDto> index() {
        return entryService.findAll().stream()
                .map(entryMapper::toDetailDto)
                .toList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets entry details.", description = "Returns the entry with the given ID, including category and tags.")
    public EntryDetailDto get(@PathParam("id") Long id) {
        Entry entry = entryService.findById(id);
        if (entry == null) {
            throw new NotFoundException("Entry with ID " + id + " was not found");
        }
        return entryMapper.toDetailDto(entry);
    }
}
