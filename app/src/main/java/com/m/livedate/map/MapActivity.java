package com.m.livedate.map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.m.livedate.R;
import com.m.livedate.map.utils.BitmapUtil;
import com.m.livedate.map.utils.LocationUtils;
import com.m.livedate.map.utils.MapUtil;
import com.m.livedate.utils.ToastUtils;
import com.vividsolutions.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap mBaiDuMap;
    private LocationClient mLocation;
    private boolean isFirst = true;
    double v1 = 30.63333949895673;
    double v2 = 120.72921526553831;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapView = findViewById(R.id.bmapView);
        mapView.removeViewAt(1);
        mBaiDuMap = mapView.getMap();
        mBaiDuMap.setMyLocationEnabled(true);
        mLocation = new LocationClient(this);

        BitmapUtil.init();
        MapUtil.getInstance().init(mapView);
        LatLng point = new LatLng(34.50000, 121.43333);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_markb);


        //构建折线点坐标
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        for (int i = 0; i < points.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("key", i);
            MapUtil.getInstance().addOverlay(points.get(i), bitmap, bundle);
        }


        //设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(0xAAFF0000)
                .points(points);
        Overlay mPolyline = mBaiDuMap.addOverlay(mOverlayOptions);


        mBaiDuMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                ToastUtils.showShortToast("key=" + bundle.get("key"));
                return true;
            }
        });
/**
 * 设置中心点
 */
        //经纬度(纬度，经度) 我们这里设置深圳世界之窗的位置
        LatLng latlng = new LatLng(30.342613, 120.125284);
        MapStatusUpdate mapStatusUpdate_circle = MapStatusUpdateFactory.newLatLng(latlng);
        mBaiDuMap.setMapStatus(mapStatusUpdate_circle);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(60000);
        // 可选，设置地址信息
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(false);

        //设置locationClientOption
        mLocation.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocation.registerLocationListener(myLocationListener);

        mLocation.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        mLocation.stop();
        mBaiDuMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    public void toM(View view) {
        Vector2D vector2D = LocationUtils.lonLatToMercator(new Vector2D(v1, v2));
        Log.d("SSSSSSSSSSSS", "onViewClicked: "+vector2D.toString());
    }

    /**
     * 经度:longitude
     * 纬度：latitude
     */

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("SSSSSSSSSSS", "onReceiveLocation: getLongitude=" + location.getLongitude());
            Log.e("SSSSSSSSSSS", "onReceiveLocation: getLatitude=" + location.getLatitude());
            //mapView 销毁后不在处理新接收的位置
            if (mapView == null) {
                return;
            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiDuMap.setMyLocationData(locData);
            /**
             * 设置中心点
             */
            if (isFirst) {
                double v1 = location.getLatitude();
                double v2 = location.getLongitude();
                LatLng latlng = new LatLng(v1, v2);
                MapStatusUpdate mapStatusUpdate_circle = MapStatusUpdateFactory.newLatLng(latlng);
                mBaiDuMap.setMapStatus(mapStatusUpdate_circle);
                isFirst = false;
            }

        }
    }
}