package udacity.storyteller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import udacity.storyteller.R;
import udacity.storyteller.UI.ReadStory.ReadStory;
import udacity.storyteller.model.Story;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder>{
    private ArrayList<Story> stories;
    private Context context;

    public class StoriesViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public RatingBar ratingBar;
        public LinearLayout linearLayout;

        public StoriesViewHolder(View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.stories_rating_bar);
            textView = itemView.findViewById(R.id.stories_text_view);
            ratingBar.setNumStars(5);
            linearLayout = itemView.findViewById(R.id.stories_linear_layout);
        }
    }

    public StoriesAdapter(ArrayList<Story> stories, Context context){
        this.stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_item, parent, false);
        StoriesViewHolder storiesViewHolder = new StoriesViewHolder(view);
        return storiesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesViewHolder holder, final int position) {
        final Story story = stories.get(position);

        holder.textView.setText(story.getTitle());
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating(story.getRating());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadStory.class);
                Gson gson = new Gson();
                String stories = gson.toJson(story);
                intent.putExtra("stories", stories);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
