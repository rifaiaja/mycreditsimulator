package com.wahyurds.bcadigital.credits.service;

import com.wahyurds.bcadigital.credits.model.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterestCalculatorTest {

    @Test
    void baseRates() {
        double rMobil = InterestCalculator.annualRate(VehicleType.MOBIL, 2025); // 0 years age
        double rMotor = InterestCalculator.annualRate(VehicleType.MOTOR, 2025);
        assertEquals(0.08, rMobil, 1e-9);
        assertEquals(0.09, rMotor, 1e-9);
    }

    @Test
    void increasesWithAge() {
        double r = InterestCalculator.annualRate(VehicleType.MOBIL, 2021); // age ~4 in 2025
        // age 4 -> two chunks (2*0.005) = 0.01 => total 0.08 + 0.01 = 0.09
        assertTrue(r >= 0.09 - 1e-9);
    }
}
