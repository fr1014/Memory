package com.fr.mediafile.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间:2020/3/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class Video implements Parcelable {
    private int id = 0;
    private String path = null;
    private String name = null;
    private String resolution = null; // 分辨率
    private long size = 0;
    private long date = 0;
    private long duration = 0;
    private String thumbPath = null; //缩略图地址

    public Video(int id, String path, String name, String resolution, long size, long date, long duration, String thumbPath) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.resolution = resolution;
        this.size = size;
        this.date = date;
        this.duration = duration;
        this.thumbPath = thumbPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeString(this.resolution);
        dest.writeLong(this.size);
        dest.writeLong(this.date);
        dest.writeLong(this.duration);
        dest.writeString(this.thumbPath);
    }

    protected Video(Parcel in) {
        this.id = in.readInt();
        this.path = in.readString();
        this.name = in.readString();
        this.resolution = in.readString();
        this.size = in.readLong();
        this.date = in.readLong();
        this.duration = in.readLong();
        this.thumbPath = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", resolution='" + resolution + '\'' +
                ", size=" + size +
                ", date=" + date +
                ", duration=" + duration +
                ", thumbPath='" + thumbPath + '\'' +
                '}';
    }
}
