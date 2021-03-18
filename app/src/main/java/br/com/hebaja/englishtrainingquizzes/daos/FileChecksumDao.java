package br.com.hebaja.englishtrainingquizzes.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.firebase.EtqMessagingService;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;

@Dao
public interface FileChecksumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(FileChecksum fileChecksum);

    @Update
    void update(FileChecksum fileChecksum);

    @Delete
    void delete(FileChecksum fileChecksum);

    @Query("select * from FileChecksum where fileName like :fileName")
    FileChecksum findByFileName(String fileName);

    @Query("delete from FileChecksum where fileName in (:list)")
    void deleteList(List<String> list);
}
