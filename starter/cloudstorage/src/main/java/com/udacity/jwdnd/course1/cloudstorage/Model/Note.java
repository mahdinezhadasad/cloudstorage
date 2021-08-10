package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Note {

    private String noteTitle;
    private Integer noteId;
    private String noteDescription;
    private Integer userId;

    public Note(String noteTitle, Integer noteId, String noteDescription, Integer userId) {
        this.noteTitle = noteTitle;
        this.noteId = noteId;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
