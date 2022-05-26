package com.quark.qkapidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import com.quark.api.QkApi;
import com.quark.api.util.QkCommonUtils;
import com.quark.api.util.QkEmptyUtils;
import com.quark.api.util.QkIntentUtil;
import com.quark.api.util.QkLogUtils;
import com.quark.api.util.QkToastUtils;
import com.quark.qkapidemo.databinding.ActivityTestBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class QkTestActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTestBinding binding;
    private QkApi qkApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test);
        initListeners();
        initApi();
    }

    public void initListeners(){
        binding.callApp.setOnClickListener(this);
        binding.toLogin.setOnClickListener(this);
        binding.applicationResult.setOnClickListener(this);
        binding.uploadDeviceInfo.setOnClickListener(this);
        binding.uploadContactList.setOnClickListener(this);

        binding.uploadImage.setOnClickListener(this);
        binding.uploadVideo.setOnClickListener(this);
        binding.uploadAudio.setOnClickListener(this);
        binding.uploadAppInstallInfo.setOnClickListener(this);
        binding.sendEvent.setOnClickListener(this);

        binding.saveData.setOnClickListener(this);
        binding.readData.setOnClickListener(this);
        binding.getGps.setOnClickListener(this);
        binding.goBack.setOnClickListener(this);
        binding.toAppPage.setOnClickListener(this);

        binding.getToken.setOnClickListener(this);
        binding.getStatusBarHeight.setOnClickListener(this);
        binding.getAppInfo.setOnClickListener(this);
    }

    // 生成QkApi请求必要的headers
    public Map<String,String> getHeaders(){
        Map<String,String> headers = new HashMap();
        // 测试用的token，请更换自己的
        String token = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.OH4_QAdoB-vUDI9nh2l3mHrCTL2HVyJ5mdhj0J7tFp2_1RVAjC18Hw.WJeXiJK3kJe_oVtK1oFIvg.n31ihVXwknTnZ24PbErEUHSVHXknVvsy0bNXpgTwzIWvhs6655mfIiP-CrIxEMu2Ibgxe-VI-ag6aSYeIQXVXMmKaaig2giyKgoeeY9ZeeqE8sbBtkhl0XmxPd3UtRkFVwD_QICTQNhzNRxKPQykWOpwr0rYFuwSot0TUzWC5Q0ItS1BejzBbDj8GzOkSK4S.jeT1w4nGQaWSUh4IPk6Sfw";
        headers.put("accessToken",token);
        headers.put("appInfoId","1000");
        headers.put("appVersion",QkCommonUtils.getVersion(this));
        headers.put("deviceId",QkCommonUtils.getDeviceId(this));
        headers.put("platform","1");
        headers.put("langType","zh-CN");
        return headers;
    }

    // 生成QkApi发送请求需要的后端接口地址，请参考QkApi.setUrls()方法对接口的说明
    public Map<String,String> getUrls(){
        Map<String,String> urls = new HashMap();
        // 设置上传人脸接口地址
        urls.put("api-face","quark-loan-component/app/member/indonesia/upload-living-base64-photo");
        // 设置上传设备信息接口地址
        urls.put("api-device","quark-loan-data/app/device/save");
        // 设置上传通讯录接口地址
        urls.put("api-contact","quark-loan-data/app/personalContact/save");
        // 设置上传图片信息接口地址
        urls.put("api-image","quark-loan-data/app/photoAlbum/upload");
        // 设置上传视频信息接口地址
        urls.put("api-video","quark-loan-data/app/video/save");
        // 设置上传音频信息接口地址
        urls.put("api-audio","quark-loan-data/app/audio/save");
        // 设置上传app安装记录接口地址
        urls.put("api-install","quark-loan-data/app/appInstalled/save");
        // 设置上传埋点信息接口地址
        urls.put("api-point","quark-loan-data/app/buriedPoint/save");
        return urls;
    }

    // 初始化webView，主要是注入js的回调方法
    public void initWebView(){
        // 注入上传各种信息的回调
        qkApi.exeJs("window.uploadResult = function(msg){ alert(msg); }");
        // 读取本地数据的回调
        qkApi.exeJs("window.readResult = function(msg){ alert('本地数据：'+msg); }");
        // 读取gps的回调
        qkApi.exeJs("window.setGps = function(msg){ alert('GPS信息：'+msg); }");
        // 读取token的回调
        qkApi.exeJs("window.setToken = function(msg){ alert('token：'+msg); }");
        // 读取状态栏高度的回调
        qkApi.exeJs("window.setStatusBarHeight = function(msg){ alert('状态栏高度：'+msg); }");
        // 读取app信息
        qkApi.exeJs("window.setAppInfo = function(msg){ alert('app信息：'+msg); }");
    }


    // 初始化api，先设置必要的数据，最后调用api的初始化函数init()
    public void initApi(){
        // 测试用的语言，请更换自己的
        String langType = "zh-CN";
        // 测试用的token，请更换自己的
        String token = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.OH4_QAdoB-vUDI9nh2l3mHrCTL2HVyJ5mdhj0J7tFp2_1RVAjC18Hw.WJeXiJK3kJe_oVtK1oFIvg.n31ihVXwknTnZ24PbErEUHSVHXknVvsy0bNXpgTwzIWvhs6655mfIiP-CrIxEMu2Ibgxe-VI-ag6aSYeIQXVXMmKaaig2giyKgoeeY9ZeeqE8sbBtkhl0XmxPd3UtRkFVwD_QICTQNhzNRxKPQykWOpwr0rYFuwSot0TUzWC5Q0ItS1BejzBbDj8GzOkSK4S.jeT1w4nGQaWSUh4IPk6Sfw";
        // 测试用的memberId，请更换自己的
        String memberId = "108360525430784";
        // 测试用的baseUrl，请更换自己的
        String baseUrl = "http://app.saas.bowenfin.com:88/";
        // 初始化QkApi
        qkApi = QkApi.getInstance().setWebView(binding.webView)
                .setContext(this)
                .setLangType(langType)
                .setToken(token)
                .setMemberId(memberId)
                .setBaseUrl(baseUrl)
                .setHeaders(getHeaders())
                .setUrls(getUrls())
                .setCallback(new QkApi.Callback() {
                    @Override
                    public void onRequestError(JSONObject object) {
                        QkToastUtils.show(QkCommonUtils.toJson(object));
                    }

                    @Override
                    public void onToLogin(String msg) {
                        QkIntentUtil.startSingleActivity(QkTestActivity.this, QkLoginActivity.class,null);
                    }

                    @Override
                    public void onApplyOver(String msg) {
                        try {
                            JSONObject msgObj = new JSONObject(msg);
                            if(msgObj.has("data")){
                                JSONObject data = msgObj.getJSONObject("data");
                                if(QkEmptyUtils.isNotEmpty(data)){
                                    if(data.has("status")){
                                        boolean isSuccess = data.getInt("status") == 0 ? false : true;
                                        QkToastUtils.show(isSuccess ? "申请借款成功" : "申请借款失败");
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onGoBack(String msg) {
                        QkToastUtils.show("监听到返回事件");
                    }

                    @Override
                    public void onToPage(String msg) {
                        QkIntentUtil.startSingleActivity(QkTestActivity.this, QkLoginActivity.class,null);
                    }

                }).init();

        // 初始化webView
        initWebView();
    }

    @Override
    public void onClick(View view) {

        // 暂时禁用，1秒后可以点击
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true),1000);

        // 默认消息
        String msg = "{}";
        switch (view.getId()){
            /**
             * 下面是模拟h5调原生方法，msg是前端传的json字符串，可以是JSON.stringify()的
             */
            case R.id.call_app:
                msg = "{" +
                        "\"action\":\"goBack\"," +
                        "\"data\":{}" +
                        "}";
                qkApi.callApp(msg);
                break;
            case R.id.to_login:
                qkApi.toLogin(msg);
                break;
            case R.id.application_result:
                msg = "{" +
                        "\"data\":{" +
                            "\"status\":1," +
                            "\"msg\":\"success\"" +
                            "}" +
                        "}";
                qkApi.applicationResult(msg);
                break;
            case R.id.upload_device_info:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadDeviceInfo(msg);
                break;
            case R.id.upload_contact_list:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadContactList(msg);
                break;
            case R.id.upload_image:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadImage(msg);
                break;
            case R.id.upload_video:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadVideo(msg);
                break;
            case R.id.upload_audio:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadAudio(msg);
                break;
            case R.id.upload_app_install_info:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"" +
                        "}";
                qkApi.uploadAppInstallInfo(msg);
                break;
            case R.id.send_event:
                msg = "{" +
                        "\"callBack\":\"uploadResult\"," +
                        "\"data\":{"+
                            "\"pointList\":[" +
                                "{" +
                                    "\"actionCode\":\"Login:Enter\"," +
                                    "\"actionTime\":1653557811298," +
                                    "\"pageCode\":\"Login\"" +
                                "}" +
                            "]"+
                        "}"+
                        "}";
                qkApi.sendEvent(msg);
                break;
            case R.id.save_data:
                msg = "{" +
                        "\"data\":{" +
                            "\"key\":\"pointList\"," +
                            "\"value\":\"[{},{}]\"" +
                        "}" +
                        "}";
                qkApi.saveData(msg);
                QkToastUtils.show("保存成功");
                break;
            case R.id.read_data:
                msg = "{" +
                        "\"callBack\":\"readResult\"," +
                        "\"data\":{" +
                            "\"key\":\"pointList\"" +
                        "}" +
                        "}";
                qkApi.readData(msg);
                break;
            case R.id.get_gps:
                msg = "{" +
                        "\"callBack\":\"setGps\"" +
                        "}";
                qkApi.getGps(msg);
                break;
            case R.id.go_back:
                qkApi.goBack(msg);
                break;
            case R.id.to_app_page:
                msg = "{" +
                        "\"data\":{" +
                            "\"pageName\":\"login\"" +
                        "}" +
                        "}";
                qkApi.toAppPage(msg);
                break;
            case R.id.get_token:
                msg = "{" +
                        "\"callBack\":\"setToken\"" +
                        "}";
                qkApi.getToken(msg);
                break;
            case R.id.get_status_bar_height:
                msg = "{" +
                        "\"callBack\":\"setStatusBarHeight\"" +
                        "}";
                qkApi.getStatusBarHeight(msg);
                break;
            case R.id.get_app_info:
                msg = "{" +
                        "\"callBack\":\"setAppInfo\"" +
                        "}";
                qkApi.getAppInfo(msg);
                break;
        }
    }
}