package udacity.storyteller.UI.Writing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import udacity.storyteller.R;
import udacity.storyteller.UI.Storyboard.Storyboard;
import udacity.storyteller.model.Story;

public class Writing extends AppCompatActivity {
    private EditText title;
    private EditText article;
    private EditText author;
    private Button submit;
    private Button saveDraft;
    private Spinner dropdown;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String draftKey;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title.getText().toString());
        outState.putString("article", article.getText().toString());
        outState.putString("author", author.getText().toString());
        outState.putInt("dropdown", dropdown.getSelectedItemPosition());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        title = findViewById(R.id.writing_title);
        article = findViewById(R.id.writing_article);
        author = findViewById(R.id.writing_author);

        dropdown = findViewById(R.id.writing_spinner_category);
        String[] categories = new String[]{"Comedy", "Crime", "Drama", "Fantasy", "Science"};

        final String userID = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("stories");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        dropdown.setAdapter(adapter);

        restoreState(savedInstanceState);


        submit = findViewById(R.id.writing_button_submit);
        saveDraft = findViewById(R.id.writing_button_draft);


        Toast.makeText(this, dropdown.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().length() < 1){
                    Toast.makeText(Writing.this, R.string.add_title, Toast.LENGTH_SHORT).show();
                    //return;
                }else if(author.getText().length() < 1){
                    Toast.makeText(Writing.this, R.string.add_author_name, Toast.LENGTH_SHORT).show();
                }
                else{
                    saveStory(userID);
                }
            }
        });

        saveDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().length() < 1){
                    Toast.makeText(Writing.this, R.string.add_title, Toast.LENGTH_SHORT).show();
                    //return;
                }else if(author.getText().length() < 1){
                    Toast.makeText(Writing.this, R.string.add_author_name, Toast.LENGTH_SHORT).show();
                }
                else{
                    saveDraft(userID);
                }
            }
        });



    }

    private void saveDraft(String userId) {
        Story story = new Story(author.getText().toString(), title.getText().toString(), article.getText().toString(), userId, 5, dropdown.getSelectedItem().toString(), 1);
        story.setTitle(title.getText().toString());
        String uniqueKey = databaseReference.push().getKey();
        story.setDraftKey(uniqueKey);
        databaseReference = FirebaseDatabase.getInstance().getReference(userId + "/drafts/" + uniqueKey);
        databaseReference.setValue(story);
        Intent intent = new Intent(getApplicationContext(), Storyboard.class);
        Toast.makeText(Writing.this, R.string.draft_saved, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void saveStory(String userId) {
        Story story = new Story(author.getText().toString(), title.getText().toString(), article.getText().toString(), userId, 5, dropdown.getSelectedItem().toString(), 1);
        story.setTitle(title.getText().toString());
        String uniqueKey = databaseReference.push().getKey();
        databaseReference = FirebaseDatabase.getInstance().getReference(userId + "/myStories/" + uniqueKey);
        databaseReference.setValue(story);
        databaseReference = FirebaseDatabase.getInstance().getReference("stories/" + dropdown.getSelectedItem().toString() + "/" + uniqueKey);
        databaseReference.setValue(story);

        if(draftKey != null){
            databaseReference = FirebaseDatabase.getInstance().getReference(userId + "/drafts/" + draftKey);
            databaseReference.removeValue();
            Log.e("remove value: ", "reference: " + userId + "/drafts/" + draftKey);
        }
        Intent intent = new Intent(getApplicationContext(), Storyboard.class);
        Toast.makeText(Writing.this, R.string.story_saved, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void restoreState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            title.setText(savedInstanceState.getString("title"));
            article.setText(savedInstanceState.getString("article"));
            author.setText(savedInstanceState.getString("author"));
            dropdown.setSelection(savedInstanceState.getInt("dropdown"));
        }else if(getIntent().getStringExtra("drafts") != null){
            Gson gson = new Gson();
            Story story = gson.fromJson(getIntent().getStringExtra("drafts"), Story.class);
            title.setText(story.getTitle());
            article.setText(story.getText());
            author.setText(story.getAuthor());
            draftKey = story.getDraftKey();
        }
    }
}
