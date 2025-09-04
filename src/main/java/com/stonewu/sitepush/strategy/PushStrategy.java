package com.stonewu.sitepush.strategy;

public interface PushStrategy {
    String getPushType();

    /**
     * @param siteUrl Site URL, without /, e.g.: https://www.stonewu.com
     * @param key Key used for caching already pushed keys
     * @param pageLinks Absolute access path of the page, starting with /, e.g.: /post/new-page
     * @return Return -1 for no push performed, 1 for push successful, 0 for push failed
     */
    int push(String siteUrl, String key, String... pageLinks);

}
