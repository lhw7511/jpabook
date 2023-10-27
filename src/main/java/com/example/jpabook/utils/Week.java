package com.example.jpabook.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Week {
    MONDAY("mon", 10),
    TUESDAY("tue", 20),
    WEDNESDAY("wed", 30),
    THURSDAY("thu", 40),
    FRIDAY("fri", 50),
    STURDAY("sat", 60),
    SUNDAY("sun", 70);

    private final String title;
    private final int value;


}
