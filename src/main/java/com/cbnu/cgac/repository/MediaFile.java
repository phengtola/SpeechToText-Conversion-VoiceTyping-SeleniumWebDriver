package com.cbnu.cgac.repository;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "media_files")
public class MediaFile implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

   @Column(name="title")
    private String title;

    @Column(name="google_docs_url")
    private String googleDocUrl;

   @Column(name = "speech")
    private String speech;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", insertable=false,
            columnDefinition="TIMESTAMP default NOW()")
    private Date createdDate;


    public MediaFile(String title, String googleDocUrl, String speech, String status, String createdBy) {
        this.title = title;
        this.googleDocUrl = googleDocUrl;
        this.speech = speech;
        this.status = status;
        this.createdBy = createdBy;
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

    public String getGoogleDocUrl() {
        return googleDocUrl;
    }

    public void setGoogleDocUrl(String googleDocUrl) {
        this.googleDocUrl = googleDocUrl;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
