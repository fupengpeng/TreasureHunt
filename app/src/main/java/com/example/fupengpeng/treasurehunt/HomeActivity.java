package com.example.fupengpeng.treasurehunt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.bmapView)
    MapView mBaiduMapView=null;
    @BindView(R.id.btn_location)
    Button mBtnLocation;
    @BindView(R.id.btn_sate)
    Button mBtnSate;
    @BindView(R.id.btn_fingerpost)
    Button mBtnFingerpost;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        //获取地图控件引用
        ButterKnife.bind(this);


        /*
        1.找到MapView
        2.获取操作地图的控制器
        3.卫星视图和普通视图的切换
         */
         mBaiduMap = mBaiduMapView.getMap();
        MapStatus mapStatus=new MapStatus.Builder()
                .overlook(0)//地图俯仰角度
                .zoom(15)//缩放级别
                .build();
        BaiduMapOptions options=new BaiduMapOptions()
                .zoomControlsEnabled(false)//是否
                .zoomGesturesEnabled(true);//
        mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        mLocationClient.start();

    }
    BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener=new BaiduMap.OnMapStatusChangeListener() {
        //滑动前
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }
        //滑动后
        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBaiduMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBaiduMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBaiduMapView.onPause();
    }

    @OnClick({R.id.btn_location,R.id.btn_sate,R.id.btn_fingerpost})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_location:
                /*
                1.开启定位图层
                2.初始化locationClient
                3.配置相关参数---通过locationClientOption
                4.设置监听，定位监听，
                5.开启定位
                 */
                mBaiduMap.setMyLocationEnabled(true);//打开定位
                Toast.makeText(this,"btn_location",Toast.LENGTH_LONG).show();



                break;
            case R.id.btn_sate:
                Toast.makeText(this,"btn_sate",Toast.LENGTH_LONG).show();
                //卫星地图
                if (mBaiduMap.getMapType()== BaiduMap.MAP_TYPE_SATELLITE){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

                    return;
                }else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }

                break;
            case R.id.btn_fingerpost:
                Toast.makeText(this,"btn_fingerpost",Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

}
