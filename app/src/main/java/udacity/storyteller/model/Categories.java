package udacity.storyteller.model;

import udacity.storyteller.R;

public class Categories {
    private String category;
    private int picture;

    public Categories(String category, int picture) {
        this.category = category;
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
