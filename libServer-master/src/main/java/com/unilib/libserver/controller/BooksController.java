package com.unilib.libserver.controller;


import com.unilib.libserver.entity.ApiResponse;
import com.unilib.libserver.entity.Books;
import com.unilib.libserver.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class BooksController {
    @Autowired
    private BooksService booksService;
    @GetMapping("/books")
    public ApiResponse<Books> findAllIssues(){
        return booksService.getBooks();
    }
    @PostMapping("/books/add")
    public ApiResponse<Books> addBook(@RequestBody Books book){
        return booksService.addBook(book);
    }
}
