package com.wahyurds.bcadigital.credits.service;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.model.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCalculatorTest {

    @Test
    void dpValidationBaru() {
        long loan = 100_000_000L;
        long dpOk = 35_000_000L;
        long dpBad = 34_000_000L;
        // should not throw
        CreditCalculator.validateInputs(Condition.BARU, loan, 3, dpOk);
        assertThrows(IllegalArgumentException.class, () ->
                CreditCalculator.validateInputs(Condition.BARU,  loan, 3, dpBad));
    }

    @Test
    void monthlyCalculation() {
        long loan = 10_000_000L;
        long dp = 4_000_000L;
        int tenor = 2;
        double monthly = CreditCalculator.calculateMonthlyInstallment(VehicleType.MOTOR, 2023, loan, tenor, dp);
        assertTrue(monthly > 0);
    }
}
