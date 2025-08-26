package com.wahyurds.bcadigital.credits.service;

import com.wahyurds.bcadigital.credits.model.VehicleType;

import java.time.Year;

public class InterestCalculator {

    /**
     * Base rates:
     *  - Mobil: 8% per annum
     *  - Motor: 9% per annum
     * Increase rule:
     *  - Every 2 full years of vehicle age -> +0.5% (0.005)
     *  - For remaining 1 year -> +0.1% (0.001)
     *
     * Returns annual rate as decimal (e.g., 0.08 for 8%).
     */
    public static double annualRate(VehicleType type, int manufactureYear) {
        int currentYear = Year.now().getValue();
        int age = currentYear - manufactureYear;
        if (age < 0) age = 0;

        double base = (type == VehicleType.MOBIL) ? 0.08 : 0.09;

        int twoYearChunks = age / 2;
        int leftoverYears = age % 2;
        double added = twoYearChunks * 0.005 + leftoverYears * 0.001;

        return base + added;
    }
}
