package udacity.storyteller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.storyteller.Data;
import udacity.storyteller.R;
import udacity.storyteller.UI.Fragments.Drafts;
import udacity.storyteller.UI.Stories.Stories;
import udacity.storyteller.model.Categories;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private ArrayList<Categories> categoriesList;
    private Context context;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private RelativeLayout relativeLayout;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image_view);
            textView = itemView.findViewById(R.id.category_text_view);
            relativeLayout = itemView.findViewById(R.id.category_relative_layout);
        }
    }

    public CategoryAdapter(ArrayList<Categories> categoriesList, Context context){
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return  categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        Categories category = categoriesList.get(position);

        holder.imageView.setImageResource(category.getPicture());
        holder.textView.setText(category.getCategory());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data category = new Data();

                Intent intent = new Intent(context, Stories.class);
                intent.putExtra("category", category.getCategories()[position]);
                context.startActivity(intent);
                //context.startActivity(new Intent(context, Drafts.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
