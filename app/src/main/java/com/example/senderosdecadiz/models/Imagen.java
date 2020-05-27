package com.example.senderosdecadiz.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "imagen", foreignKeys = @ForeignKey(
        entity = Sitio.class, parentColumns = "id", childColumns = "id_fk", onDelete = CASCADE))

public class Imagen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String path;
    private long id_fk;

    @Ignore
    public Imagen(String path, long id_fk){
        this.path = path;
        this.id_fk = id_fk;
    }

    public Imagen(final long id, String path, final long id_fk) {
        this.id = id;
        this.path = path;
        this.id_fk = id_fk;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId_fk() {
        return id_fk;
    }

    public void setId_fk(long id_fk) {
        this.id_fk = id_fk;
    }
}
