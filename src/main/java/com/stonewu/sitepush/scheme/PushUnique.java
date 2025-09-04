package com.stonewu.sitepush.scheme;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;

import java.time.Instant;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
@GVK(kind = "PushUnique", group = "sitepush.halo.run",
        version = "v1alpha1", singular = "pushUnique", plural = "pushUniques")
@ToString
public class PushUnique extends AbstractExtension {

    @Schema(requiredMode = REQUIRED)
    private Instant lastPushTime;

    @Schema(requiredMode = REQUIRED)
    private String pushType;

    @Schema(requiredMode = REQUIRED)
    private String pushUniqueKey;

    @Schema(requiredMode = REQUIRED, description = "1: Push successful, 0: Push failed, -1: Skip push")
    private Integer pushStatus;

    public String getCacheKey() {
        return this.pushType + ":" + this.pushUniqueKey;
    }
}
