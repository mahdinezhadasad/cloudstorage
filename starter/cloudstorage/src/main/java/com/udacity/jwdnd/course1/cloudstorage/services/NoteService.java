package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    @PostConstruct

    public void postConstruct(){


        System.out.println("This post construct for NoteService");
    }

    public Integer addOrEdit(Note note){

        if(note.getNoteId() == null){


            return noteMapper.addNote(note);

        }

        else{

            return noteMapper.updateNoteById(note);

        }
    }

    public Integer delete(Note note){


        return noteMapper.deleteNote(note);

    }

    public Integer update(Note note){

        return noteMapper.updateNote(note);

    }

    public List<Note> getAllNotes(Integer userId){

        return noteMapper.getAllNotes(userId);

    }
    public Note getNoteById(Integer id){


        return noteMapper.noteById(id);
    }


    public Note getNoteByTitle(String title){ return this.noteMapper.getNoteByTitle(title); }
}
