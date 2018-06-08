package org.sc.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {

    /**
     * 把double类型的变量保留指定位精度(科学计数)
     *
     * @param value
     * @param scale
     * @return
     */
    public static double round(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * 把Long类型的变量除以10000并保留指定位精度(不科学计数)
     *
     * @param value
     * @param scale
     * @return
     */
    public static String roundLong(Long value, int scale) {
        return new DecimalFormat("##0.00").format(round((double) value / 10000, scale));
    }

    /**
     * 其他单位换算成kg
     *
     * @param sellingTheUnit 单位量
     * @param unitName       包装单位
     * @return
     */
    public static Double getKg(String sellingTheUnit, String unitName) {
        // 判断传入的报价单位 有id 判断是哪个单位

        Double unit = MathUtils.round(Double.parseDouble(sellingTheUnit), 2);   // 售卖单位
        Double weightKg = 0.0;

        switch (unitName) {
            case "千克":
                weightKg = unit;
                break;
            case "只":
                weightKg = unit;
                break;
            case "打":
                weightKg = unit;
                break;
            case "箱":
                weightKg = unit;
                break;
            case "件":
                weightKg = unit;
                break;
            case "磅":
                weightKg = unit * 0.4536;
                break;
            case "盒":
                weightKg = unit;
                break;
            case "500克":
                weightKg = unit / 2;
                break;
            case "批":
                weightKg = unit;
                break;
            case "条":
                weightKg = unit;
                break;
            default:
                break;
        }

        return weightKg;
    }

}
