package com.swerve.backend.subject.util;

public class Utility {
    public static Double roundToTwoDecimals(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
