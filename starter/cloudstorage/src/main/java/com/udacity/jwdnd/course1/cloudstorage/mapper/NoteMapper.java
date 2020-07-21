package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Note> getNotes();

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(int noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription) VALUES(#{noteTitle}, #{noteDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteId = #{noteId}")
    int update(int noteId, String title, String description);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int delete(int noteId);
}
