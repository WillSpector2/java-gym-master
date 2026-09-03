package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession trainingSession = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(trainingSession);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(trainingSession, mondaySessions.get(0));

        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group(
                "Акробатика для взрослых",
                Age.ADULT,
                90
        );

        TrainingSession thursdayAdultTrainingSession =
                new TrainingSession(
                        groupAdult,
                        coach,
                        DayOfWeek.THURSDAY,
                        new TimeOfDay(20, 0)
                );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group(
                "Акробатика для детей",
                Age.CHILD,
                60
        );

        TrainingSession mondayChildTrainingSession =
                new TrainingSession(
                        groupChild,
                        coach,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        TrainingSession thursdayChildTrainingSession =
                new TrainingSession(
                        groupChild,
                        coach,
                        DayOfWeek.THURSDAY,
                        new TimeOfDay(13, 0)
                );

        TrainingSession saturdayChildTrainingSession =
                new TrainingSession(
                        groupChild,
                        coach,
                        DayOfWeek.SATURDAY,
                        new TimeOfDay(10, 0)
                );

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertEquals(1, mondaySessions.size());

        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(
                thursdayChildTrainingSession,
                thursdaySessions.get(0)
        );
        Assertions.assertEquals(
                thursdayAdultTrainingSession,
                thursdaySessions.get(1)
        );

        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group(
                "Акробатика для детей",
                Age.CHILD,
                60
        );

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        TrainingSession trainingSession =
                new TrainingSession(
                        group,
                        coach,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        timetable.addNewTrainingSession(trainingSession);

        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(14, 0)
                );

        Assertions.assertEquals(1, sessionsAt13.size());
        Assertions.assertEquals(trainingSession, sessionsAt13.get(0));

        Assertions.assertTrue(sessionsAt14.isEmpty());
    }
    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(11, 0)
                )
        );

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(coach, result.get(0).getCoach());
        Assertions.assertEquals(2, result.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesSeveralCoaches() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachTwo = new Coach("Петров", "Иван", "Александрович");

        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachOne,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachOne,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(11, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachOne,
                        DayOfWeek.WEDNESDAY,
                        new TimeOfDay(12, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachTwo,
                        DayOfWeek.THURSDAY,
                        new TimeOfDay(13, 0)
                )
        );

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        // Тренер с большим количеством тренировок должен быть первым
        Assertions.assertEquals(coachOne, result.get(0).getCoach());
        Assertions.assertEquals(3, result.get(0).getCount());

        Assertions.assertEquals(coachTwo, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesSortedDescending() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachTwo = new Coach("Петров", "Иван", "Александрович");
        Coach coachThree = new Coach("Сидоров", "Алексей", "Игоревич");

        Group group = new Group("Акробатика", Age.CHILD, 60);

        // coachOne — 2 тренировки
        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachOne,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachOne,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(10, 0)
                )
        );

        // coachTwo — 4 тренировки
        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachTwo,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(11, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachTwo,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(11, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachTwo,
                        DayOfWeek.WEDNESDAY,
                        new TimeOfDay(11, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachTwo,
                        DayOfWeek.THURSDAY,
                        new TimeOfDay(11, 0)
                )
        );

        // coachThree — 1 тренировка
        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coachThree,
                        DayOfWeek.FRIDAY,
                        new TimeOfDay(12, 0)
                )
        );

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        // Порядок: 4 → 2 → 1
        Assertions.assertEquals(coachTwo, result.get(0).getCoach());
        Assertions.assertEquals(4, result.get(0).getCount());

        Assertions.assertEquals(coachOne, result.get(1).getCoach());
        Assertions.assertEquals(2, result.get(1).getCount());

        Assertions.assertEquals(coachThree, result.get(2).getCoach());
        Assertions.assertEquals(1, result.get(2).getCount());
    }
}