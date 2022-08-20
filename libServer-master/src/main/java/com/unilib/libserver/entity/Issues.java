package com.unilib.libserver.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issues")
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "bid")
    private Books books;


    private String dueDate;
    private String issueDate;
    private String returnDate;

}
