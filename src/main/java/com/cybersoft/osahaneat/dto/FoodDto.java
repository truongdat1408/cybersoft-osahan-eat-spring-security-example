package com.cybersoft.osahaneat.dto;

public class FoodDto {
    private String image;
    private String name;
    private String desc;

    public String getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
