package com.gcs.testuielements.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gcs.testuielements.models.XYZJson;

@Database(entities = XYZJson.class, version = 1, exportSchema = false)
public abstract class XYZDatabase extends RoomDatabase {

    private static XYZDatabase instance;

    public abstract XYZDao xyzDao();

    public static synchronized XYZDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    XYZDatabase.class, "xyz_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Here create initials when db first created
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
