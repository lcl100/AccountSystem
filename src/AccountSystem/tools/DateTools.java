package AccountSystem.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTools {
    /**
     * 操作结果：日期格式化
     *
     * @param date   需要格式化的日期
     * @param format 日期格式
     * @return String 格式化的字符串
     */
    public String dateFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 操作结果：将日期字符串转成日期类型
     *
     * @param date       日期字符串
     * @param dateFormat 日期字符串的格式
     * @return Date 日期对象
     */
    public Date stringToDate(String date, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 操作结果：获取昨天的日期
     *
     * @return Date 日期对象
     */
    public Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 操作结果：获取前天的日期
     *
     * @return Date 日期对象
     */
    public Date getBeforeYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        return calendar.getTime();
    }

    /**
     * 操作结果：获取指定天数之前的日期
     *
     * @param number 指定的天数
     * @return Date 日期对象
     */
    public Date getBeforeNumbersDayDate(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -number);
        return calendar.getTime();
    }

    /**
     * 操作结果：获取上一个月今天的日期
     *
     * @return Date 日期对象
     */
    public Date getBeforeMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.MONDAY, -1);
        return calendar.getTime();
    }

    /**
     * 操作结果：获得指定日期周的第一天，周一
     *
     * @param date 日期对象
     * @return Date 指定日期周的开始日期
     */
    public Date getWeekDayStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 操作结果：获得指定日期周的最后一天，周日
     *
     * @param date 日期对象
     * @return Date 指定日期周的结束日期
     */
    public Date getWeekDayEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 操作结果：获取指定日期月的开始日期
     *
     * @param date 日期对象
     * @return Date 指定日期月的开始日期
     */
    public Date getMonthStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 操作结果：获取指定日期月的结束期
     *
     * @param date 日期对象
     * @return Date 指定日期月的结束日期
     */
    public Date getMonthEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 操作结果：指定日期季度的开始时间
     *
     * @param date 日期对象
     * @return Date 当前季度的开始时间
     */
    public Date getQuarterStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 6);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);
            dt = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 操作结果：指定日期季度的结束时间
     *
     * @param date 日期对象
     * @return Date 当前季度的结束时间
     */
    public Date getQuarterEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            dt = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 获取date所属年的所有季度列表及开始/结束时间 开始时间：date[0]，结束时间：date[1]
     *
     * @param date
     * @return
     */
    public List<Date[]> getYearQuarterList(Date date) {
        List<Date[]> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = new DateTools().getYearStartTime(date);
        Date endtm = new DateTools().getYearEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date st = getQuarterStartTime(calendar.getTime());
            Date et = getQuarterEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(Calendar.MONTH, 3);
        }
        return result;
    }

    /**
     * 操作结果：获取前/后半年的开始时间
     *
     * @param date 日期对象
     * @return 前/后半年的开始日期
     */
    public Date getHalfYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }

    /**
     * 操作结果：获取前/后半年的结束时间
     *
     * @param date 日期对象
     * @return Date 前/后半年的结束日期
     */
    public Date getHalfYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 操作结果：得到当前年的开始时间
     *
     * @param date 日期对象
     * @return Date 当前年的结束日期
     */
    public Date getYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            dt = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 操作结果：得到当前年的结束时间
     *
     * @param date 日期对象
     * @return Date 当前年的结束日期
     */
    public Date getYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            dt = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
}
