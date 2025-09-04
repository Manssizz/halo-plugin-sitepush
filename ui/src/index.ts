import { definePlugin } from "@halo-dev/console-shared";
import { IconUpload } from "@halo-dev/components";
import { markRaw } from "vue";
import PushLog from "@/views/PushLog.vue";

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "ToolsRoot",
      route: {
        path: "site-push-log",
        name: "SitePushLog",
        component: PushLog,
        meta: {
          title: "Site Indexing Push",
          searchable: true,
          description: "View site indexing push logs",
          permissions: ["plugin:sitepush:view"],
          menu: {
            name: "Site Indexing Push",
            group: "tool",
            icon: markRaw(IconUpload),
            priority: 0,
          },
        },
      },
    },
  ],
  extensionPoints: {},
});
