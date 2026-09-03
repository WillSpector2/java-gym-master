package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable =
            new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.computeIfAbsent(day, key -> new TreeMap<>());

        List<TrainingSession> sessions =
                daySchedule.computeIfAbsent(time, key -> new ArrayList<>());

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();

        for (List<TrainingSession> sessions : daySchedule.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sessions);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> sessions = getTrainingSessionsForDay(day);

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