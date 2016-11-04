package com.example.model;

import android.text.TextUtils;

public class WvConfigModel {
    private String clientId;
    private String version;
    private PaymentDeviceModel paymentDevice;
    private String redirectUri;
    private String userEmail;
    private boolean account;
    private boolean demoMode;
    private String themeOverrideCssUrl;
    private String demoCardGroup;

    public static class Builder {

        private String clientId;
        private String version;
        private PaymentDeviceModel paymentDevice;
        private String redirectUri;
        private String email;
        private boolean accountExist;
        private boolean demoMode;
        private String themeOverrideCssUrl;
        private String demoCardGroup;

        public Builder accountExist(boolean value) {
            this.accountExist = value;
            return this;
        }

        public Builder demoMode(boolean value) {
            this.demoMode = value;
            return this;
        }

        public Builder demoCardGroup(String demoCardGroup) {
            if (!TextUtils.isEmpty(demoCardGroup)) {
                this.demoCardGroup = demoCardGroup;
            }
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder paymentDevice(PaymentDeviceModel paymentDevice) {
            this.paymentDevice = paymentDevice;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder setCSSUrl(String cssUrl) {
            this.themeOverrideCssUrl = cssUrl;
            return this;
        }

        public WvConfigModel build() {
            WvConfigModel wvConfig = new WvConfigModel();
            wvConfig.clientId = this.clientId;
            wvConfig.version = this.version;
            wvConfig.paymentDevice = this.paymentDevice;
            wvConfig.redirectUri = this.redirectUri;
            wvConfig.userEmail = this.email;
            wvConfig.account = this.accountExist;
            wvConfig.demoMode = this.demoMode;
            wvConfig.themeOverrideCssUrl = this.themeOverrideCssUrl;
            wvConfig.demoCardGroup = TextUtils.isEmpty(this.demoCardGroup) ? null : this.demoCardGroup;
            return wvConfig;
        }
    }

}