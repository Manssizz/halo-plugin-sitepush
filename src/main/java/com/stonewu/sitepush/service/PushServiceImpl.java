package com.stonewu.sitepush.service;

import com.stonewu.sitepush.GlobalCache;
import com.stonewu.sitepush.scheme.PushUnique;
import com.stonewu.sitepush.strategy.PushStrategy;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.halo.app.extension.Metadata;
import run.halo.app.extension.ReactiveExtensionClient;

@Component
@Slf4j
@AllArgsConstructor
public class PushServiceImpl implements PushService {
    private final ReactiveExtensionClient client;

    private Map<String, PushStrategy> pushStrategyMap;

    @Override
    public boolean pushUseAllStrategy(String siteUrl, String slugKey, String... permalinks) {
        boolean allPush = true;
        for (Map.Entry<String, PushStrategy> entry : pushStrategyMap.entrySet()) {
            boolean push = push(siteUrl, entry.getValue(), slugKey, permalinks);
            allPush = allPush && push;
        }
        return allPush;
    }


    @Override
    public boolean push(String siteUrl, PushStrategy pushStrategy, String slugKey,
        String... permalinks) {
        String cacheKey = pushStrategy.getPushType() + ":" + slugKey;
        // Set default value to success
        boolean allSuccess = true;
        for (String permalink : permalinks) {
            // Not pushed before or pushed but failed
            if (isNeedPush(cacheKey)) {
                int status = pushStrategy.push(siteUrl, slugKey, permalink);
                allSuccess = allSuccess && status == 1;
                recordPushResult(pushStrategy.getPushType(), slugKey, cacheKey, status);
            }
        }
        return allSuccess;
    }

    public void recordPushResult(String pushType, String slugKey, String cacheKey, int status) {
        PushUnique pushUnique = new PushUnique();
        pushUnique.setLastPushTime(Instant.now());
        pushUnique.setPushType(pushType);
        pushUnique.setPushUniqueKey(slugKey);
        pushUnique.setPushStatus(status);
        GlobalCache.PUSH_CACHE.put(cacheKey, pushUnique);
        Optional<PushUnique> fetch =
            client.fetch(PushUnique.class, pushUnique.getCacheKey()).blockOptional();
        if (fetch.isPresent()) {
            pushUnique = fetch.get();
            client.update(pushUnique).subscribe();
        } else {
            Metadata metadata = new Metadata();
            metadata.setName(cacheKey);
            pushUnique.setMetadata(metadata);
            client.create(pushUnique).subscribe();
        }
    }

    public boolean isNeedPush(String cacheKey) {
        var pushUnique = GlobalCache.PUSH_CACHE.get(cacheKey);
        if (pushUnique == null) {
            return true;
        }
        Integer pushStatus = pushUnique.getPushStatus();
        return pushStatus == 0 || pushStatus == -1;
    }
}
