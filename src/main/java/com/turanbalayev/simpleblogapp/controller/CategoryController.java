package com.turanbalayev.simpleblogapp.controller;


import com.turanbalayev.simpleblogapp.payload.CategoryDto;
import com.turanbalayev.simpleblogapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "CRUD Operations for Category Resource")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Operation(
            summary = "Create a category",
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "The request succeeded, and a new category was created as a result. "
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<CategoryDto>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Read a single category",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The request succeeded. The category has been fetched and transmitted in the message body."
            )
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Read all categories",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The request succeeded. The categories have been fetched and transmitted in the message body."
            )
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @Operation(
            summary = "Update a category",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The updated category describing the result of the action is transmitted in the message body."

            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete a category",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The category deleted successfully."

            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){

        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
