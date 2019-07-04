package thefirstchange.example.com.communicationtext.util;


import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        if(activities==null){
            activities = new ArrayList<>();
        }
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static Context getLastActivity(){
        return activities.get(activities.size()-1);
    }

    public static void finishAll(){
        for (Activity activity:activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
       // System.exit(0);
    }

    public static void finishAllExcept(Activity a){
        for (Activity activity:activities){
            if (a!=activity&&!activity.isFinishing()){
                activity.finish();
            }
        }
        activities=new ArrayList<>();

        System.exit(0);
    }


}
