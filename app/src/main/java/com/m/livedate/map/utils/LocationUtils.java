package com.m.livedate.map.utils;

/**
 * createDate:2020/9/28
 *
 * @author:spc
 * @describe：
 */

import android.util.Log;

import com.baidu.platform.comapi.basestruct.Point;
import com.m.livedate.map.PointBean;
import com.vividsolutions.jts.math.Vector2D;

import java.io.InputStream;
import java.io.ObjectInputStream;

/*
 * 把获取到的真实地址转换为火星坐标
 * v1=30.63333949895673v2=120.72921526553831
 *  point=POINT(13439661.066082422 3563250.416219637)
 */
public class LocationUtils {


//    /**
//     * 经纬度转墨卡托
//     *
//     * @param LonLat 经纬度坐标
//     * @return
//     */
//    public static PointBean lonLatToMercator(PointBean LonLat) {
//        PointBean mercator = new PointBean();
//        double x = (LonLat.getX() * 20037508.342789 / 180);
//        double y = (Math.log(Math.tan((90 + LonLat.getY()) * Math.PI / 360)) / (Math.PI / 180));
//        y = (double) (y * 20037508.342789 / 180);
//        mercator.setX(x);
//        mercator.setY(y);
//        return mercator;
//    }
//
//    /**
//     * 墨卡托转经纬度
//     *
//     * @param mercator 墨卡托坐标
//     * @return
//     */
//    public static PointBean mercatorToLonLat(PointBean mercator) {
//        PointBean lonlat = new PointBean();
//        double x = (mercator.getX() / 20037508.342789 * 180);
//        double y = (mercator.getY() / 20037508.342789 * 180);
//        y = (double) (180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2));
//        lonlat.setX(x);
//        lonlat.setY(y);
//        return lonlat;
//    }


    /**
     * 经纬度转墨卡托投影
     */
    public static Vector2D lonLatToMercator(Vector2D lonLat) {
        double x = lonLat.getX() * 20037508.34 / 180;
        double y = Math.log(Math.tan((90 + lonLat.getY()) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34 / 180;

        Vector2D mercator = new Vector2D(x, y);
        Log.d("SSSSSSSSSss", "lonLatToMercator: "+mercator.toString());
        return mercator;
    }

    /**
     * 墨卡托投影转经纬度
     */
    public static Vector2D MercatorTolonLat(Vector2D mercator) {
        double x = mercator.getX() / 20037508.34 * 180;
        double y = mercator.getY() / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);

        Vector2D lonLat = new Vector2D(x, y);

        return lonLat;
    }
}