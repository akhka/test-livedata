package com.gcs.xyzreader.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gcs.xyzreader.models.XYZJson;

import java.util.List;

@Dao
public interface XYZDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(XYZJson json);

    @Update
    void update(XYZJson json);

    @Delete
    void delete(XYZJson json);

    @Query("DELETE FROM xyz_table")
    void deleteAll();

    @Query("SELECT * FROM xyz_table ORDER BY title")
    LiveData<List<XYZJson>> getAll();

    @Query("SELECT * FROM xyz_table WHERE id = :id")
    List<XYZJson> getById(int id);

}
