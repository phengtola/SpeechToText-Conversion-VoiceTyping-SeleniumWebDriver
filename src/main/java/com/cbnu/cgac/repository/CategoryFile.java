package com.cbnu.cgac.repository;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "category_files")
public class CategoryFile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , columnDefinition = "SERIAL")
    private int id;

    @Column(name="name")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "created_date", insertable=false,
            columnDefinition="TIMESTAMP default NOW()")
    private Date createdDate;

    @Column(name = "index_of")
    private int indexOf;

    public CategoryFile(int indexOf) {
        this.indexOf = indexOf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(int indexOf) {
        this.indexOf = indexOf;
    }
}
