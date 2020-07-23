package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private String fileId;
    private String filename;
    private String contentType;
    private byte[] filedata;
    private String userid;

    public File(String fileId, String filename, String contentType, byte[] filedata, String userId) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.filedata = filedata;
        this.userid = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
