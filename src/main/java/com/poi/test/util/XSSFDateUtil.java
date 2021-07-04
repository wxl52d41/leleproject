//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.poi.test.util;

import java.util.Calendar;
import org.apache.poi.ss.usermodel.DateUtil;

public class XSSFDateUtil extends DateUtil {
    public XSSFDateUtil() {
    }

    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }
}
