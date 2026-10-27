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

import DTO.CategoryDto;
import ch.zli.m223.mapper.CategoryMapper;
import ch.zli.m223.model.Category;
import ch.zli.m223.service.CategoryService;

@Path("/categories")
@Tag(name = "Categories", description = "Handling of categories")
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @Inject
    CategoryMapper categoryMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Categories.", description = "Returns a list of all Categories.")
    public List<CategoryDto> index() {
        return categoryService.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new category.", description = "Creates a category and returns it.")
    public CategoryDto create(@Valid CategoryDto dto) {
       Category savedCategory = categoryService.createCategory(categoryMapper.toEntity(dto));
       return categoryMapper.toDto(savedCategory);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a category.", description = "Deletes the category with the given ID.")
    public boolean delete(@PathParam("id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a category.", description = "Updates the category with the given ID.")
    public CategoryDto update(@PathParam("id") Long id, @Valid CategoryDto dto) {
        Category updatedCategory = categoryService.updateCategory(id, categoryMapper.toEntity(dto));
        return categoryMapper.toDto(updatedCategory);
    }
}
