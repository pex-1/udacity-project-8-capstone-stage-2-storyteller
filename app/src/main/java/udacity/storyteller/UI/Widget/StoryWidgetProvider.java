package udacity.storyteller.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import udacity.storyteller.R;
import udacity.storyteller.UI.Storyboard.Storyboard;
import udacity.storyteller.model.WidgetData;

/**
 * Implementation of App Widget functionality.
 */
public class StoryWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);

        String val = preference.getString("widgetData", "null");
        if(!val.equals("null")){
            Gson gson = new Gson();
            WidgetData widgetData = gson.fromJson(val, WidgetData.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, Storyboard.class),0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.story_title_name_widget, widgetData.getCategory());
            remoteViews.setOnClickPendingIntent(R.id.story_title_name_widget, pendingIntent);

            //list view init
            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            //bind remote adapter
            remoteViews.setRemoteAdapter(R.id.story_widget_list_view, intent);
            //update widget using widget manager
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.story_widget_list_view);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

