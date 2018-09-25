package udacity.storyteller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import udacity.storyteller.R;
import udacity.storyteller.UI.ReadStory.ReadStory;
import udacity.storyteller.UI.Writing.Writing;
import udacity.storyteller.model.Story;

public class MyStoriesAdapter extends RecyclerView.Adapter<MyStoriesAdapter.MyStoriesViewHolder>{
    private ArrayList<Story> stories;
    private Context context;

    public static class MyStoriesViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView category;
        private LinearLayout linearLayout;

        public MyStoriesViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.my_stories_text_view_title);
            category = itemView.findViewById(R.id.my_stories_text_view_category);
            linearLayout = itemView.findViewById(R.id.my_stories_linear_layout);
        }
    }

    public MyStoriesAdapter(ArrayList<Story> stories, Context context){
        this.stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public MyStoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_stories_item, parent, false);
        MyStoriesViewHolder myStoriesViewHolder = new MyStoriesViewHolder(view);
        return myStoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyStoriesViewHolder holder, int position) {
        final Story story = stories.get(position);

        holder.title.setText(story.getTitle());
        holder.category.setText(story.getCategory());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadStory.class);
                Gson gson = new Gson();
                String myStories = gson.toJson(story);
                intent.putExtra("myStories", myStories);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
