package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, List<TrainingSession>> sessionsByDay =
            new HashMap<>();

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable =
            new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        List<TrainingSession> daySessions =
                sessionsByDay.computeIfAbsent(day, key -> new ArrayList<>());

        int index = 0;
        while (index < daySessions.size()
                && daySessions.get(index).getTimeOfDay().compareTo(time) <= 0) {
            index++;
        }

        daySessions.add(index, trainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.computeIfAbsent(day, key -> new TreeMap<>());

        List<TrainingSession> sessions =
                daySchedule.computeIfAbsent(time, key -> new ArrayList<>());

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> sessions = sessionsByDay.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return sessions;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();

        for (List<TrainingSession> sessions : sessionsByDay.values()) {
            for (TrainingSession session : sessions) {
                Coach coach = session.getCoach();

                counters.put(
                        coach,
                        counters.getOrDefault(coach, 0) + 1
                );
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : counters.entrySet()) {
            result.add(
                    new CounterOfTrainings(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        result.sort(Comparator.reverseOrder());

        return result;
    }
}