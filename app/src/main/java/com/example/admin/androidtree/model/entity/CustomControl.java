package com.example.admin.androidtree.model.entity;

/**
 * @author Diana
 * @date 2017/7/8
 */

public class CustomControl {
    private String title;
    private String icon;
    private String description;

    public CustomControl(String title) {
        this.title = title;
    }

    public CustomControl(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public CustomControl(String title, String icon, String description) {
        this.title = title;
        this.icon = icon;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
