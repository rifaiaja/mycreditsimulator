package com.wahyurds.bcadigital.credits.util;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.model.VehicleType;

public class InputParser {

    /**
     * Expected tokens: type, condition, year, loanAmount, tenor, dp
     * Accepts comma separated or whitespace separated.
     */
    public static ParsedInput parseLine(String line) {
        if (line == null || line.trim().isEmpty()) throw new IllegalArgumentException("Empty input line");
        String[] tokens = line.split("[,;\\t]+");
        if (tokens.length < 6) {
            // fallback: whitespace split
            tokens = line.trim().split("\\s+");
        }
        if (tokens.length < 6) throw new IllegalArgumentException("Invalid input format: " + line);

        VehicleType type = VehicleType.fromString(tokens[0]);
        Condition cond = Condition.fromString(tokens[1]);
        int year = Integer.parseInt(tokens[2].trim());
        long loanAmount = Long.parseLong(tokens[3].trim());
        int tenor = Integer.parseInt(tokens[4].trim());
        long dp = Long.parseLong(tokens[5].trim());

        return new ParsedInput(type, cond, year, loanAmount, tenor, dp);
    }

    public static class ParsedInput {
        public final VehicleType vehicleType;
        public final Condition vehicleCondition;
        public final int vehicleYear;
        public final long totalLoanAmount;
        public final int loanTenure;
        public final long downPayment;

        public ParsedInput(VehicleType vehicleType, Condition vehicleCondition, int vehicleYear, long totalLoanAmount, int loanTenure, long downPayment) {
            this.vehicleType = vehicleType;
            this.vehicleCondition = vehicleCondition;
            this.vehicleYear = vehicleYear;
            this.totalLoanAmount = totalLoanAmount;
            this.loanTenure = loanTenure;
            this.downPayment = downPayment;
        }
    }
}
