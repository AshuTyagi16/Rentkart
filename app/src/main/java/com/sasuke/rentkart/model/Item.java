package com.sasuke.rentkart.model;

/**
 * Created by abc on 4/1/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_description")
    @Expose
    private String itemDescription;
    @SerializedName("item_thumbnail")
    @Expose
    private String itemThumbnail;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemThumbnail() {
        return itemThumbnail;
    }

    public void setItemThumbnail(String itemThumbnail) {
        this.itemThumbnail = itemThumbnail;
    }

}