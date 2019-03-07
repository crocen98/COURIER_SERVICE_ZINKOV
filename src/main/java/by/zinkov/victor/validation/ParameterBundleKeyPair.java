package by.zinkov.victor.validation;

public class ParameterBundleKeyPair {

    private String parameterName;
    private String BundleKey;

    public ParameterBundleKeyPair(){}
    public ParameterBundleKeyPair(String parameterName, String bundleKey) {
        this.parameterName = parameterName;
        BundleKey = bundleKey;
    }

    public String getparameterName() {
        return parameterName;
    }

    public void setparameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getBundleKey() {
        return BundleKey;
    }

    public void setBundleKey(String bundleKey) {
        BundleKey = bundleKey;
    }
}
