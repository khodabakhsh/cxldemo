/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 *
 * @author Administrator
 */
public class TimeUtil {
    public static String getTimeDescription(java.sql.Timestamp time) throws Exception{
        long tt = System.currentTimeMillis() - time.getTime();  //离现在的时间间隔
        long t = tt / (3600*1000);
        if (t > 24){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeS = df.format(time);
            df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            tt = df.parse(df.format(new java.util.Date())).getTime() - df.parse(timeS.substring(0, 10)).getTime();
            long d = tt / (24*3600*1000);
            if (d >= 3){
                return timeS;
            }
            else if (d == 2){
                return "前天";
            }
            else{
                return "昨天";
            }
        }
        else{
            if (t > 0){
                return Long.toString(t)+"小时前";
            }
            else{
                t = tt / (60*1000);
                if (t > 0){
                    return Long.toString(t)+"分钟前";
                }
                else{
                    return "刚刚";
                }
            }
        }
    }

}
