package com.unilib.libserver.service;


import com.unilib.libserver.entity.ApiResponse;
import com.unilib.libserver.entity.Books;
import com.unilib.libserver.entity.Issues;
import com.unilib.libserver.repo.BooksRepo;
import com.unilib.libserver.repo.IssuesRepo;
import com.unilib.libserver.repo.UsersRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class IssuesService {
    @Autowired
    private IssuesRepo issuesRepo;
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private UsersRepo usersRepo;

    public ApiResponse<List<Issues>> getIssues(){
        ApiResponse<List<Issues>> response = new ApiResponse<List<Issues>>();
        response.data=issuesRepo.findAll();
        response.success=true;
        return response;
    }

    public ApiResponse<List<Issues>> getBooksBorrowed(int uid){
        ApiResponse<List<Issues>> response = new ApiResponse<>();
        List<Issues> data = issuesRepo.findAllByUid(uid);
        if (data==null){
            response.success=false;
            response.data=null;
            response.errorMessage="No books Issued!";
        }else {
            response.success=true;
            response.data=issuesRepo.findAllByUid(uid);
        }
        return response;
    }

    public ApiResponse<List<Issues>> getStudentsByBid(int bid){
        ApiResponse<List<Issues>> response = new ApiResponse<>();
        response.success=true;
        response.data=issuesRepo.findAllByBid(bid);
        return response;
    }

    public ApiResponse<String> returnBook(int id){
        ApiResponse<String> response = new ApiResponse<String>();
        Optional<Issues> data=issuesRepo.findById(id);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String returnDate = date.format(formatter);
        data.get().setReturnDate(returnDate);
        Optional<Books> bookToBeReturned=booksRepo.findById(data.get().getBooks().bid);
        bookToBeReturned.get().setNoOfCopies(bookToBeReturned.get().getNoOfCopies()+1);

        if (booksRepo.save(bookToBeReturned.get())!=null && issuesRepo.save(data.get())!=null){
            response.success=true;
            response.data="Book has been successfully returned!";
        }else {
            response.success=true;
            response.data="Something went wrong please contact admin!";
        }
        return response;
    }

    public ApiResponse<String> issueBook(int uid,int bid){
        ApiResponse<String> response = new ApiResponse<String>();
        Issues issue=new Issues();
        Optional<Books> bookToBeIssued=booksRepo.findById(bid);
        bookToBeIssued.get().setNoOfCopies(bookToBeIssued.get().getNoOfCopies()-1);
        issue.setBooks(bookToBeIssued.get());
        issue.setUser(usersRepo.findById(uid).get());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String IssueDate = date.format(formatter);
        issue.setIssueDate(IssueDate);
        LocalDate localDate= LocalDate.parse(IssueDate,formatter);;
        String dueDate = localDate.plusDays(10).format(formatter);
        issue.setDueDate(dueDate);
        if (issuesRepo.save(issue)!=null)
        {
            response.success=true;
            response.data="Success! Book Has Been Issued :)";
        }
        else {
            response.success=false;
            response.data="Something Went Wrong!";
            response.errorMessage="Something Went Wrong!";
        }

        return response;
    }
}
