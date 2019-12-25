package xyz.crowxx.dcxtcomplete.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsNumberUtil {
    public static Boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher((CharSequence) str);
        boolean result = matcher.matches();
        if (result) {
            return true;
        } else {
            return false;
        }
    }
}
