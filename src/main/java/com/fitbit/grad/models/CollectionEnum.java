package com.fitbit.grad.models;

/**
 * Enumeration class setting MongoDB collection names
 *
 * @author nikos_mas, alex_kak
 */

public enum CollectionEnum {

    // Collections for all time saved data
    PROFILE("profile"),
    A_LIFETIME("a_lifetime"),
    A_FREQUENCE("a_frequence"),
    A_CALORIES("a_calories"),
    A_DISTANCE("a_distance"),
    A_FLOORS("a_floors"),
    A_STEPS("a_steps"),
    A_HEART("a_heart"),
    S_EFFICIENCY("s_efficiency"),
    S_MINUTES_TO_FALL_ASLEEP("s_minutesToFallAsleep"),
    S_MINUTES_AFTER_WAKE_UP("s_minutesAfterWakeUp"),
    S_MINUTES_AWAKE("s_minutesAwake"),
    S_MINUTES_ASLEEP("s_minutesAsleep"),
    S_TIME_IN_BED("s_timeInBed"),
    FILTERD_A_HEART("heartRateValues"),

    // Collections for monthly saved data
    A_STEPS_M("a_steps_monthly"),
    A_DISTANCE_M("a_distance_monthly"),
    A_FLOORS_M("a_floors_monthly"),
    A_CALORIES_M("a_calories_monthly"),
    S_EFFICIENCY_M("s_efficiency_monthly"),
    S_MINUTES_TO_FALL_ASLEEP_M("s_minutesToFallAsleep_monthly"),
    S_MINUTES_AFTER_WAKE_UP_M("s_minutesAfterWakeUp_monthly"),
    S_MINUTES_AWAKE_M("s_minutesAwake_monthly"),
    S_MINUTES_ASLEEP_M("s_minutesAsleep_monthly"),
    S_TIME_IN_BED_M("s_timeInBed_monthly"),

    // Collections for daily saved data
    A_FLOORS_D("a_floors_daily"),
    A_STEPS_D("a_steps_daily"),
    A_DISTANCE_D("a_distance_daily"),
    A_HEART_D("a_heart_daily");

    private String d;

    CollectionEnum(String d) {
        this.d = d;
    }

    public String d() {
        return d;
    }

}
