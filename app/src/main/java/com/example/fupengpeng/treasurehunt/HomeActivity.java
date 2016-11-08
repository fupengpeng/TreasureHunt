package com.example.fupengpeng.treasurehunt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity implements View.OnClickListener {

//    @BindView(R.id.bmapView)
    MapView mBaiduMapView=null;
    @BindView(R.id.btn_location)
    Button mBtnLocation;
    @BindView(R.id.btn_sate)
    Button mBtnSate;
    @BindView(R.id.btn_fingerpost)
    Button mBtnFingerpost;
    @BindView(R.id.rl_home)
    RelativeLayout mRelativeLayout;

    BaiduMap mBaiduMap;
    LatLng mMyLocation;

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

//        MapStatus mapStatus=new MapStatus.Builder()
//                .overlook(0)//地图俯仰角度
//                .zoom(15)//缩放级别
//                .build();
        BaiduMapOptions options=new BaiduMapOptions()
                .zoomControlsEnabled(false)//是否
                .zoomGesturesEnabled(true);//
        //动态代码添加地图
        mBaiduMapView=new MapView(this,options);//创建一个MapView
        mRelativeLayout.addView(mBaiduMapView,0);//将MapView添加到mRelativeLayout
        mBaiduMap = mBaiduMapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);

        mBaiduMap.setOnMarkerClickListener(MarkerLinstener);

    }


    BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener=new BaiduMap.OnMapStatusChangeListener() {
        /**
         * 地图状态变化开始
         * @param mapStatus
         */
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        /**
         * 地图状态变化
         * @param mapStatus
         */
        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        /**
         * 地图状态变化完成
         * @param mapStatus
         */
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
                mBaiduMap.setMyLocationEnabled(true);//1.打开定位
//                Toast.makeText(this,"btn_location",Toast.LENGTH_LONG).show();
                mLocationClient = new LocationClient(getApplicationContext());     //2.声明LocationClient类
                LocationClientOption option=new LocationClientOption();//3.声明option， 对地图进行一些设置

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
                mLocationClient.setLocOption(option);//更改设置
                mLocationClient.registerLocationListener( locationListener );    //4.注册监听函数
                mLocationClient.start();//5.开始定位
                //有些机型定位不成功，再次定位
                mLocationClient.requestLocation();
                Toast.makeText(this,"定位成功",Toast.LENGTH_LONG).show();
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


    public LocationClient mLocationClient = null;
    private BDLocationListener locationListener=new BDLocationListener() {//定位监听器
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation==null){
                mLocationClient.requestLocation();//请求定位
                return;
            }
            double lat=bdLocation.getLatitude();//获取纬度
            double lng=bdLocation.getLongitude();//获取经度
//            Toast.makeText(HomeActivity.this,"lat=="+lat+"      lng=="+lng,Toast.LENGTH_LONG).show();

            /*
            定位后，添加定位标志，移动到当前设备位置
                1.获取定位信息
                2.给地图添加定位获取到的信息
             */

            MyLocationData myLocationData=new MyLocationData.Builder()//1.设置定位信息
                    .latitude(lat)//纬度
                    .longitude(lng)//经度
                    .accuracy(100f)//定位精度大小（范围）
                    .build();
            mBaiduMap.setMyLocationData(myLocationData);//2.给地图添加定位获取到的信息

            /*
            定位后，地图跟随定位移动至定位城市
                1.设备定位位置（经纬度）
                2.移动至定位位置，设置监听，移动时，地图状态发生变化
                3.地图当前位置移动至定位位置
                4.地图更新
             */
            mMyLocation =new LatLng(lat,lng);//1.当前位置(设备定位位置)
            moveToMyLocation();
            addMarker(mMyLocation);
        }
    };
    private void moveToMyLocation(){
        MapStatus mapStatus=new MapStatus.Builder()
                .target(mMyLocation)//移动至当前位置（设备定位位置）
                .rotate(0)//摆正地图
//                .zoom(13)//地图比例尺
                .build();//提交地图设置
        MapStatusUpdate update= MapStatusUpdateFactory.newMapStatus(mapStatus);//更新地图所需参数
        mBaiduMap.animateMapStatus(update);//地图更新
    }





    private void addMarker(LatLng latLng){//在地图上添加标注物（传入经纬度）
        /*
        添加标注物
            1.需要一个标注物
            2.在地图上确定添加标注物的位置（经纬度）
            3.确定所要添加标注物样式（图标等信息）
            4.在地图上添加标注物
         */
        BitmapDescriptor dot= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        MarkerOptions options=new MarkerOptions();//1.// 需要一个OverlayOptions抽象类对象(标注物)，MarkerOptions是OverlayOptions的实现类
        options.position(latLng);//2.标注物位置
        options.icon(dot);//3.标注物图标、样式等信息的添加
        mBaiduMap.addOverlay(options);//4.添加标注物
    }

    private BaiduMap.OnMarkerClickListener MarkerLinstener= new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            /*
            点击展示一个信息窗口：文本、图片等
                1.创建一个Infowindow
                2.给Infowindow设置展示内容
                3.在地图上点击展示
             */
            BitmapDescriptor dot_click= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);//设置点击展示的图片
            InfoWindow infoWindow=new InfoWindow(dot_click,
                    marker.getPosition(), 0,
                    new InfoWindow.OnInfoWindowClickListener() {//点击展示图片，并弹跳出一个窗口，给窗口添加点击事件
                @Override
                public void onInfoWindowClick() {
                    mBaiduMap.hideInfoWindow();//隐藏弹跳出的窗口 
                }
            });
            mBaiduMap.showInfoWindow(infoWindow);//3.在地图上展示Infowindow

            return false;
        }
    };


//    private void initLocation(){
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//    }

}
