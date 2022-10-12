package com.MinNiCup.lfmpbackend.utils;

import java.util.Random;

/**
 * @author tzih
 * &#064;date  2022.09.12
 */
public class RandomUtil {

    /**
     *
     * @author tzih
     * &#064;date  9/12/22
     * @param length 长度
     * @return java.lang.String
     **/
    public static String getRandomUtil(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

}
