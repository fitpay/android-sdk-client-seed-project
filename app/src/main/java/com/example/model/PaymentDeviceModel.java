package com.example.model;

import com.fitpay.android.api.models.device.Device;

public class PaymentDeviceModel {

    private String deviceType;
    private String manufacturerName;
    private String deviceName;
    private String firmwareRevision;
    private String osName;
    private String serialNumber;
    private String modelNumber;
    private String hardwareRevision;
    private String softwareRevision;
    private String systemId;
    private String licenseKey;
    private String bdAddress;
    private String secureElementId;

    public PaymentDeviceModel() {

    }

    public PaymentDeviceModel(Device device) {
        deviceType = device.getDeviceType();
        manufacturerName = device.getManufacturerName();
        deviceName = device.getDeviceName();
        firmwareRevision = device.getFirmwareRevision();
        osName = device.getOsName();
        serialNumber = device.getSerialNumber();
        modelNumber = device.getModelNumber();
        hardwareRevision = device.getHardwareRevision();
        softwareRevision = device.getSoftwareRevision();
        systemId = device.getSystemId();
        licenseKey = device.getLicenseKey();
        bdAddress = device.getBdAddress();
        secureElementId = device.getSecureElementId();
    }


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFirmwareRevision() {
        return firmwareRevision;
    }

    public void setFirmwareRevision(String firmwareRevision) {
        this.firmwareRevision = firmwareRevision;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getBdAddress() {
        return bdAddress;
    }

    public void setBdAddress(String bdAddress) {
        this.bdAddress = bdAddress;
    }

    public String getHardwareRevision() {
        return hardwareRevision;
    }

    public void setHardwareRevision(String hardwareRevision) {
        this.hardwareRevision = hardwareRevision;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSecureElementId() {
        return secureElementId;
    }

    public void setSecureElementId(String secureElementId) {
        this.secureElementId = secureElementId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSoftwareRevision() {
        return softwareRevision;
    }

    public void setSoftwareRevision(String softwareRevision) {
        this.softwareRevision = softwareRevision;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDeviceModel)) return false;

        PaymentDeviceModel that = (PaymentDeviceModel) o;

        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null)
            return false;
        if (manufacturerName != null ? !manufacturerName.equals(that.manufacturerName) : that.manufacturerName != null)
            return false;
        if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null)
            return false;
        if (firmwareRevision != null ? !firmwareRevision.equals(that.firmwareRevision) : that.firmwareRevision != null)
            return false;
        if (osName != null ? !osName.equals(that.osName) : that.osName != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null)
            return false;
        if (modelNumber != null ? !modelNumber.equals(that.modelNumber) : that.modelNumber != null)
            return false;
        if (hardwareRevision != null ? !hardwareRevision.equals(that.hardwareRevision) : that.hardwareRevision != null)
            return false;
        if (softwareRevision != null ? !softwareRevision.equals(that.softwareRevision) : that.softwareRevision != null)
            return false;
        if (systemId != null ? !systemId.equals(that.systemId) : that.systemId != null)
            return false;
        if (licenseKey != null ? !licenseKey.equals(that.licenseKey) : that.licenseKey != null)
            return false;
        if (bdAddress != null ? !bdAddress.equals(that.bdAddress) : that.bdAddress != null)
            return false;
        return secureElementId != null ? secureElementId.equals(that.secureElementId) : that.secureElementId == null;

    }

    @Override
    public int hashCode() {
        int result = deviceType != null ? deviceType.hashCode() : 0;
        result = 31 * result + (manufacturerName != null ? manufacturerName.hashCode() : 0);
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (firmwareRevision != null ? firmwareRevision.hashCode() : 0);
        result = 31 * result + (osName != null ? osName.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (modelNumber != null ? modelNumber.hashCode() : 0);
        result = 31 * result + (hardwareRevision != null ? hardwareRevision.hashCode() : 0);
        result = 31 * result + (softwareRevision != null ? softwareRevision.hashCode() : 0);
        result = 31 * result + (systemId != null ? systemId.hashCode() : 0);
        result = 31 * result + (licenseKey != null ? licenseKey.hashCode() : 0);
        result = 31 * result + (bdAddress != null ? bdAddress.hashCode() : 0);
        result = 31 * result + (secureElementId != null ? secureElementId.hashCode() : 0);
        return result;
    }
}
