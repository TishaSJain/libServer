package com.unilib.libserver.service;


import com.unilib.libserver.entity.ApiResponse;
import com.unilib.libserver.entity.Books;
import com.unilib.libserver.repo.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    @Autowired
    private BooksRepo booksRepo;

    public ApiResponse<Books> getBooks(){
        ApiResponse response = new ApiResponse();
        List<Books> books = booksRepo.findAll();
        if (books==null){
            response.success=false;
            response.errorMessage="No Books Found";
            response.data=null;
        }else {
            response.success=true;
            response.data=books;
        }
        return response;
    }

    public ApiResponse<Books> addBook(Books book){
        ApiResponse response = new ApiResponse();
        if (book.getBid()!=0){
            Optional<Books> bookData = booksRepo.findById(book.bid);
            response.success=true;
            bookData.get().setAuthor(book.author);
            bookData.get().setBname(book.bname);
            bookData.get().setPublisher(book.publisher);
            bookData.get().setNoOfCopies(book.noOfCopies);
            bookData.get().setImage(book.image);
            response.data=booksRepo.save(bookData.get());
        }else {
            response.success=true;
            response.data=booksRepo.save(book);
        }

        return response;
    }
}
