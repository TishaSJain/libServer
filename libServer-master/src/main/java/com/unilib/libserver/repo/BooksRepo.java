package com.unilib.libserver.repo;


import com.unilib.libserver.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepo extends JpaRepository<Books,Integer> {

}
