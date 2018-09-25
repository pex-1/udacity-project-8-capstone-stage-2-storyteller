package udacity.storyteller.Services;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import udacity.storyteller.UI.Stories.Stories;
import udacity.storyteller.UI.Widget.AppWidgetService;
import udacity.storyteller.model.Story;
import udacity.storyteller.model.WidgetData;

public class DatabaseService extends JobService {
    private DatabaseReference databaseReference;
    private ArrayList<Story> stories;

    @Override
    public boolean onStartJob(final JobParameters job) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        String val = preference.getString("category", "null");

        if(!val.equals("null")){
            databaseReference = FirebaseDatabase.getInstance().getReference("stories/" + val);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    stories.clear();

                    for (DataSnapshot storySnapshot : dataSnapshot.getChildren()){
                        Story story = storySnapshot.getValue(Story.class);

                        stories.add(story);

                    }
                    if(stories.size() > 0){
                        WidgetData widgetData = new WidgetData(stories.get(0).getCategory(), stories);

                        String json = new Gson().toJson(widgetData);

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("widgetData", json);
                        editor.commit();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DatabaseService.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }

        AppWidgetService.updateWidget(this);
        Log.e("Start job", "updated!");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

}
