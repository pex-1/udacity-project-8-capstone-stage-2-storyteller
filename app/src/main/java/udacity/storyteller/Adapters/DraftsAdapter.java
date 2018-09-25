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
import udacity.storyteller.UI.Writing.Writing;
import udacity.storyteller.model.Story;

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.DraftsViewHolder> {
    private ArrayList<Story> stories;
    private Context context;


    public static class DraftsViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView category;
        private LinearLayout linearLayout;

        public DraftsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.drafts_text_view_title);
            category = itemView.findViewById(R.id.drafts_text_view_category);
            linearLayout = itemView.findViewById(R.id.drafts_linear_layout);
        }
    }

    public DraftsAdapter(ArrayList<Story> stories, Context context){
        this.stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public DraftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drafts_item, parent, false);
        DraftsViewHolder draftsViewHolder = new DraftsViewHolder(view);
        return draftsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DraftsViewHolder holder, final int position) {
        Story story = stories.get(position);

        holder.title.setText(story.getTitle());
        holder.category.setText(story.getCategory());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Writing.class);
                Gson gson = new Gson();
                String drafts = gson.toJson(stories.get(position));
                intent.putExtra("drafts", drafts);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
