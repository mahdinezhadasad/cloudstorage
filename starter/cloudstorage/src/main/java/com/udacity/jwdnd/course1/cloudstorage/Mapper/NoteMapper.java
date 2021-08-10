package com.udacity.jwdnd.course1.cloudstorage.Mapper;


import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {


    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle},#{noteDescription},#{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addNote(Note note);


    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(Integer userId);


    @Delete("DELETE * FROM NOTES WHERE userid = {userId}")

    int deleteNote(Note note);

    @Update("UPDATE NOTES SET notetitle={#noteTitle},notedescription = {#noteDescription}")
    int updateNote(Note note);


    @Update("UPDATE NOTES SET notetitle = {#noteTitle},notedescription = {#noteDescription}" + "WHERE noteid = {# noteId}")
    int updateNoteById(Note note);


    @Select("SELECT * FROM NOTES WHERE noteid = {# noteId}")
    Note noteById(Note note);

    @Select("SELECT key FROM NOTES WHERE noteid = #{noteId}")

    String noteKeyById(Note note);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
    Note getNoteByTitle(String noteTitle);
}
