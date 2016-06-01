package com.example.administrator.myjournal.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CurrentTime {

    public static long getTime() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH) + 1;//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        long time = 10000*year + 100*month +day;
        return time;
    }

}
