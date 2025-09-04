import dayjs from "dayjs";
import "dayjs/locale/en-us";
import timezone from "dayjs/plugin/timezone";
import TimeAgo from "javascript-time-ago";
import en from "javascript-time-ago/locale/en";

dayjs.extend(timezone);

dayjs.locale("en-us");

TimeAgo.addDefaultLocale(en);

export function formatDatetime(
  date: string | number | Date | undefined | null
): string {
  if (!date) {
    return "";
  }
  return dayjs(date).format("YYYY-MM-DD HH:mm");
}

export function timeAgo(
  date: string | Date | number | undefined | null
): string {
  if (!date) {
    return "";
  }

  const currentDate = new Date();
  const inputDate = new Date(date);

  // 365 days * 24 hours * 60 minutes * 60 seconds * 1000 milliseconds
  const oneYearInMilliseconds = 365 * 24 * 60 * 60 * 1000;

  if (currentDate.getTime() - inputDate.getTime() > oneYearInMilliseconds) {
    return dayjs(date).format("YYYY-MM-DD");
  }

  const timeAgo = new TimeAgo("en");

  return timeAgo.format(new Date(date));
}
