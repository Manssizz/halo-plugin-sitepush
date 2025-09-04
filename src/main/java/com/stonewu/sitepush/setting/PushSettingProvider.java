package com.stonewu.sitepush.setting;

import reactor.netty.transport.ProxyProvider;

/**
 * @author Erzbir
 * @Date 2023/10/17
 */
public interface PushSettingProvider {
    String getConfigMapName();

    String getGroup();

    Boolean isEnable();

    /**
     * @return Whether to enable tag verification
     */
    Boolean isTagVerificationEnable();

    /**
     * @return The setting class
     */
    Class<?> getSettingClass();

    /**
     * @return Site verification code
     */
    String getSiteVerification();

    /**
     * @return Access token
     */
    String getAccess();

    /**
     * @return Meta tag for HTML tag verification
     */
    String getSiteVerificationMeta();

    Boolean isUseProxy();

    ProxyProvider.Proxy getProxyType();

    String getProxyAddress();

    Integer getProxyPort();

    Boolean proxyAuthEnable();

    String getProxyUsername();

    String getProxyPassword();
}
