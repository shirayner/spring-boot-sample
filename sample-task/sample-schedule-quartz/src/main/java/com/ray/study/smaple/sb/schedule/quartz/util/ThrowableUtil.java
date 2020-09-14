package com.ray.study.smaple.sb.schedule.quartz.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * description
 *
 * @author r.shi 2020/09/11 16:13
 */
public class ThrowableUtil {

    /**
     * get StackTrace
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
