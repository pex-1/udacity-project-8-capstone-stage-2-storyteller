package udacity.storyteller.UI.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class AppWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, StoryWidgetProvider.class));
        StoryWidgetProvider.updateAppWidgets(context, appWidgetManager, widgetIds);
    }


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new udacity.storyteller.UI.Widget.RemoteViewsFactory(getApplicationContext());
    }
}