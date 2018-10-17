package com.example.entity;

import com.common.entity.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by oguzhanonder - 1.10.2018
 */

@Entity
@Where(clause = "entity_state=1")
public class Book extends BaseEntity{

    @Column
    private String title;

    @Column
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
