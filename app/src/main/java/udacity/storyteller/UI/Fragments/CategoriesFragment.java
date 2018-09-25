package udacity.storyteller.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import udacity.storyteller.Adapters.CategoryAdapter;
import udacity.storyteller.Data;
import udacity.storyteller.R;
import udacity.storyteller.model.Categories;

public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        ArrayList<Categories> categories = getCategoriesList();
        recyclerView = view.findViewById(R.id.categories_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CategoryAdapter(categories, getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /*
        TextView textView = view.findViewById(R.id.categories);
        textView.setText("working like a charm!");
        */
        return view;

    }

    public ArrayList<Categories> getCategoriesList(){
        ArrayList<Categories> categoriesList = new ArrayList<>();
        Data data = new Data();
        for(int i = 0; i<5; i++){
            Categories category = new Categories(data.getCategories()[i], data.getPictures()[i]);
            categoriesList.add(category);
        }

        return categoriesList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
