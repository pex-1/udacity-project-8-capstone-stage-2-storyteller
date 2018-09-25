package udacity.storyteller.UI.ReadStory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import udacity.storyteller.R;
import udacity.storyteller.model.Story;

public class ReadStory extends AppCompatActivity {
    private TextView title;
    private TextView author;
    private TextView category;
    private TextView article;
    private RatingBar ratingBar;
    private Story story;

    private final String STORIES = "stories";
    private final String MY_STORIES = "myStories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);

        Gson gson = new Gson();
        if(getIntent().getStringExtra(STORIES) != null){
            story = gson.fromJson(getIntent().getStringExtra(STORIES), Story.class);
        }else{
            story = gson.fromJson(getIntent().getStringExtra(MY_STORIES), Story.class);
        }


        initialize(story);

    }



    private void initialize(Story story) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.reading_story_title);
        author = findViewById(R.id.reading_author_text_view);
        category = findViewById(R.id.reading_category_text_view);
        article = findViewById(R.id.reading_article);

        ratingBar = findViewById(R.id.reading_rating_bar);

        title.setText(story.getTitle());
        String author_string = getString(R.string.author_read_story) + story.getAuthor();
        String category_string = getString(R.string.category_read_story) + story.getCategory();
        author.setText(author_string);
        category.setText(category_string);
        article.setText(story.getText());
    }


}
