package udacity.storyteller;

public class Data {
    public int[] getPictures(){
        int[] pictures = {R.drawable.comedy, R.drawable.crime, R.drawable.drama, R.drawable.fantasy, R.drawable.science};
        return pictures;
    }

    public String[] getCategories(){
        String[] categories = {"Comedy", "Crime", "Drama", "Fantasy", "Science"};
        return  categories;
    }
}
