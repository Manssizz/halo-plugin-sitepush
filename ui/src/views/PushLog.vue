<script setup lang="ts">
import { clearAllLogApi, getLogListApi } from "@/api/pushlog/pushlog";
import { ref } from "vue";
import {
  Dialog,
  Toast,
  VButton,
  VCard,
  VEmpty,
  VLoading,
  VPageHeader,
  VPagination,
  VSpace,
} from "@halo-dev/components";
import { useQuery } from "@tanstack/vue-query";
import PushLogListItem from "@/components/PushLogListItem.vue";
import { IconUpload } from "@halo-dev/components";
import { useRouteQuery } from "@vueuse/router";

const page = useRouteQuery("page", 1, { transform: Number });
const size = useRouteQuery("size", 20, { transform: Number });

const { data, isLoading, isFetching, refetch } = useQuery({
  queryKey: ["push-logs", page, size],
  queryFn: async () => {
    const { data } = await getLogListApi({
      page: page.value,
      size: size.value,
    });
    return data;
  },
  refetchInterval(data) {
    const hasDeletingData = data?.items.some(
      (item) => !!item.metadata.deletionTimestamp
    );

    return hasDeletingData ? 1000 : false;
  },
});

function handleClear() {
  Dialog.warning({
    title: "Clear Records",
    description: "Are you sure you want to clear all push records? This operation cannot be undone.",
    async onConfirm() {
      await clearAllLogApi();

      refetch();

      Toast.success("Cleared successfully");
    },
  });
}
</script>

<template>
  <VPageHeader title="Push Logs">
    <template #icon>
      <IconUpload class="mr-2 self-center" />
    </template>
    <template #actions>
      <VButton v-permission="['plugin:sitepush:manage']" type="danger" @click="handleClear()"> Clear </VButton>
    </template>
  </VPageHeader>
  <div class="m-0 md:m-4">
    <VCard :body-class="['!p-0']">
      <VLoading v-if="isLoading" />

      <Transition v-else-if="!data?.items.length" appear name="fade">
        <VEmpty
          message="There are no push records yet, please ensure the search engine information is configured correctly"
          title="No Push Records"
        >
          <template #actions>
            <VSpace>
              <VButton :loading="isFetching" @click="refetch()"> Refresh </VButton>
              <VButton
                v-permission="['plugin:sitepush:manage']"
                type="secondary"
                @click="$router.push('/plugins/PluginSitePush?tab=basic')"
              >
                Go to Configuration
              </VButton>
            </VSpace>
          </template>
        </VEmpty>
      </Transition>

      <Transition v-else appear name="fade">
        <ul
          class="box-border h-full w-full divide-y divide-gray-100"
          role="list"
        >
          <li v-for="pushLog in data.items" :key="pushLog.metadata.name">
            <PushLogListItem :push-log="pushLog" :is-select="false" />
          </li>
        </ul>
      </Transition>

      <template #footer>
        <VPagination
          v-model:page="page"
          v-model:size="size"
          :page-label="$t('core.components.pagination.page_label')"
          :size-label="$t('core.components.pagination.size_label')"
          :total-label="
            $t('core.components.pagination.total_label', { total: data?.total })
          "
          :total="data?.total"
          :size-options="[10, 20, 30, 50, 100]"
        />
      </template>
    </VCard>
  </div>
</template>
