package udacity.storyteller.model;

public class Story {
    private String author;
    private String title;
    private String text;
    private String userId;
    private float rating;
    private String category;
    private int rated;
    private String draftKey;

    public Story(String author, String title, String text, String userId, float rating, String category, int rated) {
        this.author = author;
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.rating = rating;
        this.category = category;
        this.rated = rated;
    }

    public Story(String author, String title, String text, String userId, float rating, String category) {
        this.author = author;
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.rating = rating;
        this.category = category;
    }

    public Story(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDraftKey() {
        return draftKey;
    }

    public void setDraftKey(String draftKey) {
        this.draftKey = draftKey;
    }
}
