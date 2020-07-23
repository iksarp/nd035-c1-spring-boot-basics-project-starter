package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(String userid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid} AND noteId = #{noteId}")
    Note getNote(String userid, String noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE userid = #{userid} AND noteId = #{noteId}")
    int update(String userid, String noteId, String title, String description);

    @Delete("DELETE FROM NOTES WHERE userid = #{userid} AND noteId = #{noteId}")
    int delete(String userid, String noteId);
}
