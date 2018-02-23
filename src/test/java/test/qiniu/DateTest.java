package test.qiniu;

import com.labelwall.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2018-02-02.
 */
public class DateTest {

    public static void main(String[] args) {
        /*Date beginTime = DateTimeUtil.strToDate("2018-02-02 18:30:00");
        Date endTime = DateTimeUtil.strToDate("2018-02-02 19:30:00");
        long millis = DateTimeUtil.dateInterval(beginTime, new Date());
        long s = millis/(1000*60*60);

        System.err.print(millis);*/

        String date = DateTimeUtil.changeDateFormat(DateTimeUtil.dateToStr(new Date()));
        System.err.print(date);
    }
}
