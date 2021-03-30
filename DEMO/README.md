# 智能FSU蓝牙SDK使用说明文档
## 1. 文档说明
  - 本文档用于定义蓝牙FSU版本，Android SDK调用说明，提供使用操作流程和说明。

  - SDK功能概述：SDK向开发人员提供蓝牙搜索功能、蓝牙FSU读写功能。

  - SDK调用返回方式：callback回调。

  - SDK返回数据格式：预定义对象传参。

## 2. 参数对象定义
- SDK信息
```
public class SdkInfo {
    private String jarVersion;/*JAR包版本号*/
    private String soVersion;/*SO库版本号*/
    private String releaseTime;/*SDK释放时间*/
}
```

## 3. 使用流程
### 1. 加载lib、so库文件

- 导入 com.rayo.android.blefsu.sdk.jar 文件
- 按需导入 jniLibs 中.SO库文件
![bleFsuSdk-loadlib](../screenshot/bleFsuSdk-loadlib.png)
- 依赖库
```
    implementation 'com.alibaba:fastjson:1.1.60.android'
    implementation 'com.luffykou:android-common-utils:1.1.+'
```
- 加载Demo工程按照Module导入

### 2. 注册蓝牙权限及服务
> 注意：在Android 5.0 及以上版本，获取蓝牙使用权限需要同时在AndroidManifest.xml文件和代码中动态获取蓝牙权限。详情请查阅Android权限管理相关文档。
> 注意：在Android 6.0 及以上版本，需要获取手机定位功能权限
```
在AndroidManifest.xml中加入

<uses-permission android:name="android.permission.ACCES_MOCK_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/>
<uses-permission android:name="android.permission.WRITE_SETTINGS"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
<uses-permission android:name="android.permission.BLUETOOTH_PRIVILEGED"/>

<service android:name="rayo.blekey.sdk.ble.BluetoothLeService"
    android:enabled="true"/>
```
### 3. 蓝牙搜索

- 蓝牙搜索回调接口

```
  private BleScanCallback mBleScanCallback = new BleScanCallback() {
          @Override
          public void openBluetooth() {

          }

          @Override
          public void findBle(BluetoothDevice bluetoothDevice,
            int rssi, byte[] scanRecord) {
              //发现蓝牙设备
          }

          @Override
          public void finishScan() {

          }
      };
```

- 蓝牙搜索

```
  /*蓝牙搜索类*/
  private BluetoothLeScan mBluetoothLeScan;
  /*搜索结果存储*/
  private HashMap<String, BluetoothDevice> mBluetoothDeviceHashMap;
  private boolean isScan;
  /*搜索蓝牙的设备名称，过滤蓝牙名称*/
  private String mBleName = "";

  /*蓝牙搜索初始化*/
  private void initBle() {
     mBluetoothLeScan = new BluetoothLeScan(BluetoothAdapter.getDefaultAdapter(), 0, mBleScanCallback);
     isScan = false;
     mBluetoothDeviceHashMap = new HashMap<>();
  }

  @Override
  protected void onPause() {
     super.onPause();
     if (isScan) mBluetoothLeScan.stopReceiver();
  }

  @Override
  protected void onResume() {
     super.onResume();
     if (isScan) mBluetoothLeScan.startReceiver();
  }

  /*开始搜索蓝牙设备*/
  mBluetoothLeScan.startReceiver();

  /*停止搜索蓝牙设备*/
  mBluetoothLeScan.stopReceiver();
```
### 4. 蓝牙FSU操作

- 蓝牙功能回调接口

```
private BleSimpleFsuSdkCallback mBleSimpleFsuSdkCallback = new BleSimpleFsuSdkCallback() {
        @Override
        public void init(ResultBean resultBean) {
          //SDK初始化返回结果
        }

        @Override
        public void connect(ResultBean resultBean) {
          //蓝牙连接返回结果
        }

        @Override
        public void rssi(int rssi) {
          //蓝牙rssi信号
        }

        @Override
        public void disConnect(ResultBean resultBean) {
          //断开蓝牙返回
        }

        @Override
        public void onlineOpen(ResultBean resultBean) {
          //蓝牙在线开门返回结果
        }

        @Override
        public void onReport(ResultBean resultBean) {
          //FSU状态变化返回
        }

        @Override
        public void readEvent(ResultBean resultBean) {

        }

        @Override
        public void setSystemCode(ResultBean resultBean) {

        }

        @Override
        public void setDeviceId(ResultBean resultBean) {

        }

        @Override
        public void reset(ResultBean resultBean) {

        }

        @Override
        public void getPpp(ResultBean resultBean) {

        }

        @Override
        public void getServer(ResultBean resultBean) {

        }

        @Override
        public void setServer(ResultBean resultBean) {

        }

        @Override
        public void getWireless(ResultBean resultBean) {

        }

        @Override
        public void setWireless(ResultBean resultBean) {

        }

        @Override
        public void setThresholdInfo(ResultBean resultBean) {

        }

        @Override
        public void getThresholdInfo(ResultBean resultBean) {

        }

        @Override
        public void setNetworkTransfer(ResultBean resultBean) {

        }

        @Override
        public void getSimInfo(ResultBean resultBean) {

        }

        @Override
        public void askRemoteOpen(ResultBean resultBean) {

        }

        @Override
        public void enableWarn(ResultBean resultBean) {

        }

        @Override
        public void setBleName(ResultBean resultBean) {

        }

        @Override
        public void setNbiotInfo(ResultBean resultBean) {

        }
    };

```
### 5.初始化蓝牙功能

```
/*蓝牙SDK类*/
private BleSimpleSimpleFsuSdk mBleSimpleFsuSdk;

mBleSimpleFsuSdk = new BleSimpleSimpleFsuSdk();

/*需要连接的蓝牙mac地址*/
mBleSimpleFsuSdk.init(this, mMac, "", mBleSimpleFsuSdkCallback);
```

- SDK蓝牙服务注销

```
@Override
protected void onDestroy() {
    super.onDestroy();
    mBleSimpleFsuSdk.destroy();
}
```
## 4. SDK功能
> 蓝牙SDK初始化

```
/**
* @param context
* @param mac 需要连接的蓝牙设备MAC地址
* @param secretKey 可不填
* @param bleSimpleFsuSdkCallback 蓝牙命令的回调
*/
void init(Context context, String mac, String secretKey, BleSimpleFsuSdkCallback bleSimpleFsuSdkCallback);
```
> 蓝牙SDK注销

```
/**
*
*/
void destroy();
```
> 蓝牙设备连接

```
/**
* @param sysCode 填默认值{0x36,0x36,0x36,0x36}
*/
void connect(byte sysCode[]);
```
> 蓝牙设备断开

```
/**
*
*/
void disconnect();
```
> 获取蓝牙钥匙基本信息

```
/**
* @param userId 开锁用户ID
* @param beginTime 蓝牙开锁的开始时间
* @param endTime 蓝牙开锁的结束时间
* @param id 锁号 0-4
*/
void openOnline(int userId, Date beginTime, Date endTime, int id);
```

## 6. 返回错误列表
```
#define SUCCESS 1 成功
#define LENGTH_ERROR -1 命令长度错误
#define CRC_ERROR -2 CRC校验错误
#define DECODE_ERROR -3 数据解密错误
#define DATA_FORMAT_ERROR -4 数据格式错误

#define GET_VERSION_ERROR -9 获取版本号错误
#define CONNECT_ERROR -10 连接错误
#define VERIFY_ERROR -11 验证错误
#define SET_FSU_TIME_ERROR -12 设置FSU时间错误
#define SET_FSU_ONLINE_OPEN_ERROR -13 设置蓝牙在线开门错误
```
