package com.unilib.libserver.controller;


import com.unilib.libserver.entity.ApiResponse;
import com.unilib.libserver.entity.Issues;
import com.unilib.libserver.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class IssuesController {
    @Autowired
    private IssuesService issuesService;
    @GetMapping("/issues")
    public ApiResponse<List<Issues>> findAllIssues(){
        return issuesService.getIssues();
    }
    @PostMapping("/issues/add")
    public ApiResponse<String> issuesBooks(@RequestParam(value = "uid",required = true) Integer uid, @RequestParam(value = "bid") Integer bid){
        return issuesService.issueBook(uid,bid);
    }

    @GetMapping("/issues/user")
    public ApiResponse<List<Issues>> findIssuesById(@RequestParam(value = "uid") int uid){
        return issuesService.getBooksBorrowed(uid);
    }
    @GetMapping("/issues/book")
    public ApiResponse<List<Issues>> findIssuesByBId(@RequestParam(value = "bid") int bid){
        return issuesService.getStudentsByBid(bid);
    }
    @PostMapping("/issues/return")
    public ApiResponse<String> returnBook(@RequestParam(value = "id") int id){
        return issuesService.returnBook(id);
    }

}
