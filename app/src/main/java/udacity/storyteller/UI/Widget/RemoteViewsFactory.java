package udacity.storyteller.UI.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import udacity.storyteller.R;
import udacity.storyteller.model.WidgetData;

public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private WidgetData widgetData;

    public RemoteViewsFactory(Context context){
        this.context = context;
    }


    @Override
    public void onDataSetChanged() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);

        String val = preference.getString("widgetData", "null");

        Gson gson = new Gson();
        widgetData = gson.fromJson(val, WidgetData.class);
        //Log.e("Data: ", val);

    }


    @Override
    public int getCount() {
        return widgetData.getTitles().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.story_item_text_widget, widgetData.getTitles().get(i).getTitle());
        //Log.e("WIDGET DATA: ", widgetData.getTitles().get(i).getTitle());
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public void onCreate() {
    }
    @Override
    public void onDestroy() {
    }
}
