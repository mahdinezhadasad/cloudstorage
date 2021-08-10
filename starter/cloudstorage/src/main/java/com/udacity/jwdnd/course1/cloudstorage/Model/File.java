package com.udacity.jwdnd.course1.cloudstorage.Model;


public class File {
    private String fileName;
    private String ContentType;
    private Integer fileID;
    private Long fileSize;
    private Integer userID;
    private byte[]  fileData;



    public String getName() {
        return fileName;
    }

    public void setName(String name) {
        this.fileName = name;
    }

    public String getType() {
        return ContentType;
    }

    public void setType(String type) {
        ContentType = ContentType;
    }

    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
