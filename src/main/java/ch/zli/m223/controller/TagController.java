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

import DTO.TagDto;
import ch.zli.m223.mapper.TagMapper;
import ch.zli.m223.model.Tag;
import ch.zli.m223.service.TagService;

@Path("/tags")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(name = "Tags", description = "Handling of tags")
public class TagController {

    @Inject
    TagService tagService;

    @Inject
    TagMapper tagMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists all tags.", description = "Returns all tags.")
    public List<TagDto> index() {
        return tagService.findAll().stream()
                .map(tagMapper::toDto)
                .toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a tag.", description = "Creates a tag and returns it.")
    public TagDto create(@Valid TagDto dto) {
        Tag savedTag = tagService.createTag(tagMapper.toEntity(dto));
        return tagMapper.toDto(savedTag);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a tag.", description = "Deletes the tag with the given ID.")
    public boolean delete(@PathParam("id") Long id) {
        return tagService.deleteTag(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a tag.", description = "Updates the tag with the given ID.")
    public TagDto update(@PathParam("id") Long id, @Valid TagDto dto) {
        Tag updatedTag = tagService.updateTag(id, tagMapper.toEntity(dto));
        return tagMapper.toDto(updatedTag);
    }
}
