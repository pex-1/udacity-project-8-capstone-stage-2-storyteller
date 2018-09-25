package udacity.storyteller.UI.Stories;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import udacity.storyteller.Adapters.StoriesAdapter;
import udacity.storyteller.R;
import udacity.storyteller.Services.DatabaseService;
import udacity.storyteller.UI.Widget.AppWidgetService;
import udacity.storyteller.model.Story;
import udacity.storyteller.model.WidgetData;

public class Stories extends AppCompatActivity {
    private TextView textView;

    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Story> stories;

    private FirebaseJobDispatcher jobDispatcher;

    private String category;

    private static final String job_tag = "update_widget";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        category = getIntent().getStringExtra("category");
        textView = findViewById(R.id.stories_category_text_view);
        textView.setText(category);

        stories = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("stories/" + category);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        WidgetData widgetData;
        if(stories.size() > 0){
            widgetData = new WidgetData(stories.get(0).getCategory(), stories);

            String json = new Gson().toJson(widgetData);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("widgetData", json);
            editor.commit();
            editor.putString("category", widgetData.getCategory());
            editor.commit();

            AppWidgetService.updateWidget(this);

            jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
            startJob();
        }else{
            widgetData = new WidgetData(category, stories);

            String json = new Gson().toJson(widgetData);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("widgetData", json);
            editor.commit();
            editor.putString("category", widgetData.getCategory());
            editor.commit();

            AppWidgetService.updateWidget(this);

            jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
            startJob();
        }


        return true;
    }

    public void startJob() {

        JobTrigger.ExecutionWindowTrigger jobTrigger = Trigger.executionWindow(0, 20);
        Job job = jobDispatcher.newJobBuilder().
                setService(DatabaseService.class).
                setLifetime(Lifetime.FOREVER).      //for the lifetime of the app, job is available
                setRecurring(true).                 //needs to be repeated
                setTag(job_tag).
                setTrigger(Trigger.executionWindow(10, 60)).         //start between 10 and 60 seconds from now
                setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                setReplaceCurrent(false).
                setTrigger(jobTrigger).
                setConstraints(Constraint.ON_ANY_NETWORK).
                build();

        jobDispatcher.mustSchedule(job);
    }

    public void stopJob() {
        jobDispatcher.cancel(job_tag);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_widget, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stories.clear();

                for (DataSnapshot storySnapshot : dataSnapshot.getChildren()){
                    Story story = storySnapshot.getValue(Story.class);

                    stories.add(story);

                }

                setUpRecyclerView(stories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Stories.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(ArrayList<Story> stories) {

        recyclerView = findViewById(R.id.stories_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new StoriesAdapter(stories, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
