package com.gcs.testuielements.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "xyz_table")
public class XYZJson {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "author")
    @SerializedName("author")
    @Expose
    private String author;
    @ColumnInfo(name = "body")
    @SerializedName("body")
    @Expose
    private String body;
    @ColumnInfo(name = "thumb")
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    @Expose
    private String photo;
    @ColumnInfo(name = "published_date")
    @SerializedName("published_date")
    @Expose
    private String publishedDate;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
