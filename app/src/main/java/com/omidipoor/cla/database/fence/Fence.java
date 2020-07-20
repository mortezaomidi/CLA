package com.omidipoor.cla.database.fence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fence")
public class Fence {

    public Fence() {
    }

    public Fence(long fenceId, String fenceTitle, String polygonGeometry, String criteriaObject) {
        this.fenceId = fenceId;
        this.fenceTitle = fenceTitle;
        this.polygonGeometry = polygonGeometry;
        this.criteriaObject = criteriaObject;
    }

    @PrimaryKey(autoGenerate = true)
    public long fenceId;

    @ColumnInfo(name = "fenceTitle")
    public  String fenceTitle;

    @ColumnInfo(name = "polygonGeometry")
    public String polygonGeometry;

    @ColumnInfo(name = "criteriaObject")
    public String criteriaObject;


    public long getFenceId() {
        return fenceId;
    }

    public void setFenceId(int fenceId) {
        this.fenceId = fenceId;
    }

    public String getFenceTitle() {
        return fenceTitle;
    }

    public void setFenceTitle(String fenceTitle) {
        this.fenceTitle = fenceTitle;
    }

    public String getPolygonGeometry() {
        return polygonGeometry;
    }

    public void setPolygonGeometry(String polygonGeometry) {
        this.polygonGeometry = polygonGeometry;
    }

    public String getCriteriaObject() {
        return criteriaObject;
    }

    public void setCriteriaObject(String criteriaObject) {
        this.criteriaObject = criteriaObject;
    }
}
