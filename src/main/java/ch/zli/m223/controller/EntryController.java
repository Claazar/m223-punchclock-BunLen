package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import DTO.EntryDto;
import ch.zli.m223.mapper.EntryMapper;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @Inject
    EntryMapper entryMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<EntryDto> index() {
        return entryService.findAll().stream()
                .map(entryMapper::toDto)
                .toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public EntryDto create(@Valid EntryDto dto) {
       Entry savedEntry = entryService.createEntry(
               entryMapper.toEntity(dto),
               dto.getCategoryId(),
               dto.getTagIds());
       return entryMapper.toDto(savedEntry);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes an entry.", description = "Deletes a certain entry.")
    public boolean delete(@PathParam("id") Long id) {
        return entryService.deleteEntry(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates a certain entry")
    public EntryDto update(@PathParam("id") Long id, @Valid EntryDto dto) {
        Entry updatedEntry = entryService.updateEntry(
                id,
                entryMapper.toEntity(dto),
                dto.getCategoryId(),
                dto.getTagIds());
        return entryMapper.toDto(updatedEntry);
    }
}
