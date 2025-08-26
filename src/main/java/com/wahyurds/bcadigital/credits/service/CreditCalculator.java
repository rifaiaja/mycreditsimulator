package com.wahyurds.bcadigital.credits.service;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.model.VehicleType;

public class CreditCalculator {

    public static final long MAX_LOAN = 1_000_000_000L;

    /**
     * validate input
     * @param condition
     * @param loanAmount
     * @param tenorYears
     * @param dpAmount
     */
    public static void validateInputs(Condition condition, long loanAmount, int tenorYears, long dpAmount) {
        if (loanAmount <= 0 || loanAmount > MAX_LOAN) {
            throw new IllegalArgumentException("Jumlah pinjaman harus >0 dan <= " + MAX_LOAN);
        }
        if (tenorYears < 1 || tenorYears > 6) {
            throw new IllegalArgumentException("Tenor harus antara 1 sampai 6 tahun");
        }
        double dpPercent = ((double) dpAmount) / loanAmount;
        if (condition == Condition.BARU) {
            if (dpPercent < 0.35) throw new IllegalArgumentException("DP untuk kendaraan baru minimal 35%");
        } else {
            if (dpPercent < 0.25) throw new IllegalArgumentException("DP untuk kendaraan bekas minimal 25%");
        }
    }

    /**
     * Flat interest calculation:
     * principal = loanAmount - dp
     * totalInterest = principal * annualRate * tenorYears
     * monthly = (principal + totalInterest) / (tenorYears * 12)
     */
    public static double calculateMonthlyInstallment(VehicleType type, int manufactureYear, long loanAmount, int tenorYears, long dpAmount) {
        double rate = InterestCalculator.annualRate(type, manufactureYear);
        long principal = loanAmount - dpAmount;
        if (principal <= 0) return 0.0;
        double totalInterest = principal * rate * tenorYears;
        return (principal + totalInterest) / (tenorYears * 12.0);
    }
}
