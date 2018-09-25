package udacity.storyteller.model;

import java.util.ArrayList;

public class WidgetData {
    private String category;
    private ArrayList<Story> titles;

    public WidgetData(String category, ArrayList<Story> titles) {
        this.category = category;
        this.titles = titles;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Story> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<Story> titles) {
        this.titles = titles;
    }
}
