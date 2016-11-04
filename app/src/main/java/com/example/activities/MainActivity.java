package com.example.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.R;
import com.example.model.PaymentDeviceModel;
import com.example.model.WvConfigModel;
import com.example.utils.Constants;
import com.example.utils.Foreground;
import com.fitpay.android.api.ApiManager;
import com.fitpay.android.api.models.device.Device;
import com.fitpay.android.paymentdevice.DeviceService;
import com.fitpay.android.paymentdevice.callbacks.PaymentDeviceListener;
import com.fitpay.android.paymentdevice.constants.States;
import com.fitpay.android.paymentdevice.enums.Connection;
import com.fitpay.android.paymentdevice.events.PaymentDeviceOperationFailed;
import com.fitpay.android.utils.NotificationManager;
import com.fitpay.android.utils.RxBus;
import com.fitpay.android.webview.WebViewCommunicator;
import com.fitpay.android.webview.events.DeviceStatusMessage;
import com.fitpay.android.webview.impl.WebViewCommunicatorImpl;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static boolean appWasInBackground = false;

    private WebView webView;
    private WebViewCommunicator webViewCommunicator;

    private PaymentDeviceListener paymentDeviceListener;

    private boolean serviceConnected = false;
    private DeviceService deviceService;

    private boolean webViewHasBeenOpened = false;

    private Map<String, String> config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        webView = (WebView) findViewById(R.id.webView);
        webViewCommunicator = new WebViewCommunicatorImpl(this, webView.getId(), null);

        paymentDeviceListener = new WebViewOpeningPaymentDeviceListener();
        NotificationManager.getInstance().addListener(paymentDeviceListener);

        Foreground.get(this).addListener(foregroundListener);

        initViews();

        connectToService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (serviceConnected) {
            unbindService(deviceServiceConnection);
        }

        Foreground.get(this).removeListener(foregroundListener);
        NotificationManager.getInstance().removeListener(paymentDeviceListener);

        serviceConnected = false;
        deviceService = null;

        if (webViewCommunicator != null) {
            webViewCommunicator.setDeviceService(null);
            webViewCommunicator.close();
        }
    }

    //you must set config before using any API methods
    private void initConfig() {
        config = Constants.getConfig();
        ApiManager.init(config);
    }

    private void initViews() {
        webView.clearCache(true);
        webView.setScrollbarFadingEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setDatabaseEnabled(false);
        webView.addJavascriptInterface(webViewCommunicator, "Android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    private void connectToService() {
        /*
         * Client is responsible for configuring the device service and what type of connector is to be used
         */
        String paymentDeviceType = config.get(DeviceService.EXTRA_PAYMENT_SERVICE_TYPE);
        Intent deviceServiceIntent = new Intent(this, DeviceService.class);
        deviceServiceIntent.putExtra(DeviceService.EXTRA_PAYMENT_SERVICE_TYPE, paymentDeviceType);
        bindService(deviceServiceIntent, deviceServiceConnection, BIND_AUTO_CREATE);
    }

    private void openWebView(Device device) {
        if (device == null) {
            return;
        }

        PaymentDeviceModel paymentDeviceModel = new PaymentDeviceModel(device);

        WvConfigModel wvConfig = new WvConfigModel.Builder()
                .version("0.0.1")
                .paymentDevice(paymentDeviceModel)
                .clientId(config.get(ApiManager.PROPERTY_CLIENT_ID))
                .setCSSUrl(config.get("css_url"))
                .email("m@m.com") //pin 1379
                .accountExist(true)
                .demoMode(true)
                .demoCardGroup("default")
                .build();

        String configMessage = new Gson().toJson(wvConfig);
        byte[] bytesToEncode = configMessage.getBytes(StandardCharsets.UTF_8);
        String encodedWvConfig = Base64.encodeToString(bytesToEncode, Base64.URL_SAFE);
        String url = config.get("wv_url") + "?config=" + encodedWvConfig;
        webView.loadUrl(url);
    }

    private final ServiceConnection deviceServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceConnected = true;

            deviceService = ((DeviceService.LocalBinder) service).getService();
            webViewCommunicator.setDeviceService(deviceService);
            deviceService.connectToDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceConnected = false;
            deviceService = null;
            webViewCommunicator.setDeviceService(null);
        }
    };

    private final Foreground.Listener foregroundListener = new Foreground.Listener() {
        public void onBecameForeground() {
            if (appWasInBackground) {
                appWasInBackground = false;
                webViewCommunicator.logout();
            }
        }

        public void onBecameBackground() {
            appWasInBackground = true;
        }
    };

    private class WebViewOpeningPaymentDeviceListener extends PaymentDeviceListener {

        @Override
        public void onDeviceStateChanged(@Connection.State int state) {
            switch (state) {
                case States.INITIALIZED:
                    break;
                case States.CONNECTED:
                    RxBus.getInstance().post(new DeviceStatusMessage(getString(R.string.connected), DeviceStatusMessage.SUCCESS));
                    break;
                case States.DISCONNECTED:
                    RxBus.getInstance().post(new DeviceStatusMessage(getString(R.string.disconnected), DeviceStatusMessage.ERROR));
                    break;
                case States.CONNECTING:
                    RxBus.getInstance().post(new DeviceStatusMessage(getString(R.string.connecting), DeviceStatusMessage.PROGRESS));
                    break;
                case States.DISCONNECTING:
                    break;
            }
        }

        @Override
        public void onDeviceOperationFailed(PaymentDeviceOperationFailed paymentDeviceOperationFailed) {
            RxBus.getInstance().post(new DeviceStatusMessage(getString(R.string.disconnected), DeviceStatusMessage.ERROR));
        }

        @Override
        public void onDeviceInfoReceived(Device device) {
            if (!webViewHasBeenOpened) {
                webViewHasBeenOpened = true;
                openWebView(device);
            }
        }

        @Override
        public void onNFCStateReceived(boolean isEnabled, byte errorCode) {
        }

        @Override
        public void onNotificationReceived(byte[] data) {
        }

        @Override
        public void onApplicationControlReceived(byte[] data) {
        }
    }
}
