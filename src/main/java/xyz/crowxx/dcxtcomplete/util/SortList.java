package xyz.crowxx.dcxtcomplete.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.lang.reflect.Method;
import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortList<T> implements Comparator<T> {

    //需要比较的对象属性字段名称
    private String  propertyName;
    //是否是升序排序
    private boolean isAsc;

    /**
     * 需要的是：根据类中的字段对对象进行排序
     *
     * @return
     */

    @Override
    public int compare(T b1, T b2) {

        Class<?> clz = b1.getClass();
        Method method = getPropertyMethod(clz, propertyName);
        try {

            Object objectOne = method.invoke(b1);

            Object objectTwo = method.invoke(b2);

            if (objectOne == null || objectTwo == null) {
                return 0;
            }

            Comparable value1 = (Comparable) objectOne;

            Comparable value2 = (Comparable) objectTwo;

            if (isAsc) {
                return value1.compareTo(value2);
            } else {
                return value2.compareTo(value1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 获取类名
    public static Method getPropertyMethod(Class clz, String propertyName) {
        Method method = null;
        try {
            method = clz.getMethod("get" + firstUpperCase(propertyName));
        } catch (Exception e) {
            System.out.println("获取类名发生错误！");
        }
        return method;
    }

    /**
     * 首字母大写方法
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}