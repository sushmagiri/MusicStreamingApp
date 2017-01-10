package com.example.user.music;

import java.io.Serializable;


public class Item implements Serializable {
    public String filename;
    public String length,url;

    public Item(String filename,String length,String url) {
        this.filename = filename;
this.url=url;
        this.length= length;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
