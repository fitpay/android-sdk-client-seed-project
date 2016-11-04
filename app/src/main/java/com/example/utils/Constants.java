package com.example.utils;

import com.fitpay.android.api.ApiManager;
import com.fitpay.android.paymentdevice.DeviceService;
import com.fitpay.android.paymentdevice.impl.mock.MockPaymentDeviceConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vlad on 04.11.2016.
 */

public class Constants {
    public static final String WV_URL = "https://webapp.fit-pay.com";
    public static final String CSS_URL = "https://fitpaycss.github.io/example.css";

    public static final String API_URL = "https://api.fit-pay.com";
    public static final String AUTH_URL = "https://auth.fit-pay.com";
    public static final String CLIENT_ID = "fp_webapp_pJkVp2Rl";
    public static final String REDIRECT_URL = "https://webapp.fit-pay.com";

    public static Map<String, String> getConfig() {
        Map<String, String> config = new HashMap<>();

        config.put("wv_url", WV_URL);
        config.put("css_url", CSS_URL);

        config.put(ApiManager.PROPERTY_API_BASE_URL, API_URL);
        config.put(ApiManager.PROPERTY_AUTH_BASE_URL, AUTH_URL);
        config.put(ApiManager.PROPERTY_CLIENT_ID, CLIENT_ID);
        config.put(ApiManager.PROPERTY_REDIRECT_URI, REDIRECT_URL);

        config.put(DeviceService.EXTRA_PAYMENT_SERVICE_TYPE, MockPaymentDeviceConnector.class.getName());

        return config;
    }
}
