package com.epam.reactiveprogramming.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("sports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sport {

    private Integer id;
    private String title;

}
