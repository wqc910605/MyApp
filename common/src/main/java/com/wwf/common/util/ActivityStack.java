package com.wwf.common.util;

import android.app.Activity;


import java.util.ArrayList;

/**
 * activity 栈管理
 */
public class ActivityStack {

    public static ActivityStack getInstanse() {
        return ActivityStackInstance.INSTANCE;
    }

    public static class ActivityStackInstance {
        private static final ActivityStack INSTANCE = new ActivityStack();
    }

    private ArrayList<Activity> activityList = new ArrayList<>();

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (null != activity) {
            activityList.remove(activity);
        }
    }

    /**
     * 完全退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }

    }

    public Activity getActivityByClass(Class cs) {
        for (Activity ac : activityList) {
            if (cs.getName().equals(ac.getClass().getName())) {
                return ac;
            }
        }
        return null;
    }

    /**
     * 获取栈顶Activity的name
     */
    public String getTopActivityName() {
        if (activityList == null || activityList.size() == 0) {
            return null;
        }
        return activityList.get(activityList.size() - 1).getClass().getName();
    }

    /**
     * 获取正在运行的activity数目
     */
    public int getCount() {
        return activityList == null ? 0 : activityList.size();
    }

    /**
     * 弹出activity
     */
    public void popActivity(Activity activity) {
        if (null != activity) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 弹出activity
     */
    public void popActivity(Class cs) {
        Activity activity = getActivityByClass(cs);
        if (null != activity) {
            activity.finish();
        }
    }


    /**
     * 弹出activity到
     */
    public void popUntilActivity(Class cs) {
        boolean isTop = false;
        ArrayList<Activity> list = new ArrayList<>();
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity ac = activityList.get(i);
            if (cs.getName().equals(ac.getClass().getName())) {
                isTop = true;
            }
            if (!isTop) {
                list.add(ac);
            } else {
                break;
            }
        }
        activityList.removeAll(list);
        for (Activity activity : list) {
            popActivity(activity);
        }
    }
}
