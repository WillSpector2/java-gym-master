package ru.yandex.practicum.gym;

import java.util.Comparator;

public class TimeOfDayComparator implements Comparator<TimeOfDay> {

    @Override
    public int compare(TimeOfDay time1, TimeOfDay time2) {
        int hoursComparison = Integer.compare(
                time1.getHours(),
                time2.getHours()
        );

        if (hoursComparison != 0) {
            return hoursComparison;
        }

        return Integer.compare(
                time1.getMinutes(),
                time2.getMinutes()
        );
    }
}