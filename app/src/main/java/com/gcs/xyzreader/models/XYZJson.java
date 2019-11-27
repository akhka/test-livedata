package com.gcs.xyzreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "xyz_table")
public class XYZJson implements Parcelable {

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

    public XYZJson(int id, String title, String author, String body, String thumb, String photo, String publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.publishedDate = publishedDate;
    }

    protected XYZJson(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        body = in.readString();
        thumb = in.readString();
        photo = in.readString();
        publishedDate = in.readString();
    }

    public static final Creator<XYZJson> CREATOR = new Creator<XYZJson>() {
        @Override
        public XYZJson createFromParcel(Parcel in) {
            return new XYZJson(in);
        }

        @Override
        public XYZJson[] newArray(int size) {
            return new XYZJson[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(body);
        dest.writeString(thumb);
        dest.writeString(photo);
        dest.writeString(publishedDate);
    }
}
