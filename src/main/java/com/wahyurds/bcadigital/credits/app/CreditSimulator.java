package com.wahyurds.bcadigital.credits.app;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.model.VehicleType;
import com.wahyurds.bcadigital.credits.service.CreditCalculator;
import com.wahyurds.bcadigital.credits.util.InputParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class CreditSimulator {

    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                runFromFile(args[0]);
            } else {
                runInteractive();
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    /**
     * This fucntion for running data from file
     * @param filePath
     */
    private static void runFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int idx = 0;
            while ((line = br.readLine()) != null) {
                try {
                    idx++;
                    processLine(line);
                } catch (Exception e) {
                    System.out.println("Line " + idx + " skipped. Reason: " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to read file: " + ex.getMessage());
        }
    }

    private static void runInteractive() throws Exception {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Credit Simulator (interactive).");
        System.out.println("###Masukkan data###");
        System.out.print("Jenis Kendaraan (motor/mobil): ");
        String vehicleType = scanner.nextLine();
        System.out.print("Kondisi (baru/bekas): ");
        String vehicleCondition = scanner.nextLine();
        System.out.print("Tahun Kendaraan (YYYY): ");
        String vehicleYear = scanner.nextLine();
        System.out.print("Jumlah Pinjaman Total: ");
        String totalLoanAmount = scanner.nextLine();
        System.out.print("Tenor (tahun 1-6): ");
        String loanTenure = scanner.nextLine();
        System.out.print("Jumlah DP: ");
        String downPayment = scanner.nextLine();

        String combined = String.join(",", vehicleType, vehicleCondition, vehicleYear, totalLoanAmount, loanTenure, downPayment);
        //processLine(combined, 1);
        BigDecimal financed = new BigDecimal(totalLoanAmount).subtract(new BigDecimal(downPayment));
        if (financed.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah pembiayaan (total - DP) harus > 0");
        }
        BigDecimal baseAnnualRate = (vehicleType.equalsIgnoreCase(VehicleType.MOBIL.toString())) ? new BigDecimal("8.0") : new BigDecimal("9.0");
        CreditCalculator.validateInputs(vehicleCondition == null ? Condition.BEKAS : Condition.fromString(vehicleCondition), Long.valueOf(totalLoanAmount), Integer.valueOf(loanTenure), Long.valueOf(downPayment));
        System.out.println("Tipe Kendaraan: " + vehicleType + ", Kondisi: " + vehicleCondition + ", Tenor: " + loanTenure + ", Tahun: " + vehicleYear + ", Tanda Jadi: " + downPayment);
        simulateAndPrintSchedule(financed, baseAnnualRate, Integer.parseInt(loanTenure));
    }

    private static void processLine(String line) {
        InputParser.ParsedInput in = InputParser.parseLine(line);
        BigDecimal financed = new BigDecimal(in.totalLoanAmount).subtract(new BigDecimal(in.downPayment));
        if (financed.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah pembiayaan (total - DP) harus > 0");
        }
        BigDecimal baseAnnualRate = (in.vehicleType.toString().equalsIgnoreCase(VehicleType.MOBIL.toString())) ? new BigDecimal("8.0") : new BigDecimal("9.0");
        CreditCalculator.validateInputs(in.vehicleCondition == null ? Condition.BEKAS : Condition.fromString(in.vehicleCondition.toString()), in.totalLoanAmount, in.loanTenure, in.downPayment);
        System.out.println("\n==========================");
        System.out.println("Tipe Kendaraan: " + in.vehicleType + ", Kondisi: " + in.vehicleCondition + ", Tenor: " + in.loanTenure +  ", Tahun: " + in.vehicleYear + ", Tanda Jadi: " + in.downPayment);
        simulateAndPrintSchedule(financed, baseAnnualRate, in.loanTenure);
    }

    /**
     * This function for calculate schedule loan
     * @param financed
     * @param baseAnnualRate
     * @param tenorYears
     */
    private static void simulateAndPrintSchedule(BigDecimal financed, BigDecimal baseAnnualRate, int tenorYears) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);

        BigDecimal remainingPrincipal = financed.setScale(2, RoundingMode.HALF_UP);
        int remainingMonths = tenorYears * 12;

        for (int year = 1; year <= tenorYears; year++) {
            BigDecimal annualRatePercent = computeAnnualRate(baseAnnualRate, year);
            BigDecimal monthlyRate = annualRatePercent.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
                    .divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);

            // calculate monthly installments based on remaining principal and remaining months
            BigDecimal monthlyPayment = calcMonthlyPayment(remainingPrincipal, monthlyRate, remainingMonths)
                    .setScale(0, RoundingMode.HALF_UP);

            int monthsThisYear = Math.min(12, remainingMonths);

            // Simulate monthly payments to reduce the remaining principal.
            for (int m = 0; m < monthsThisYear; m++) {
                BigDecimal interest = remainingPrincipal.multiply(monthlyRate).setScale(10, RoundingMode.HALF_UP);
                BigDecimal principalPayment = monthlyPayment.subtract(interest).setScale(10, RoundingMode.HALF_UP);
                remainingPrincipal = remainingPrincipal.subtract(principalPayment).setScale(10, RoundingMode.HALF_UP);
                if (remainingPrincipal.compareTo(BigDecimal.ZERO) < 0) remainingPrincipal = BigDecimal.ZERO;
                remainingMonths--;
            }
            System.out.println(String.format("Tahun %d : %s /bln, Suku Bunga Tahunan: %s%%",
                    year,
                    nf.format(monthlyPayment),
                    annualRatePercent.setScale(2, RoundingMode.HALF_UP).toPlainString()
            ));
        }
        System.out.println("\nSisa pokok akhir: " + formatCurrency(remainingPrincipal.setScale(0, RoundingMode.HALF_UP)));
    }

    /**
     * This function for calculate annual rate
     * @param base
     * @param year
     * @return
     */
    private static BigDecimal computeAnnualRate(BigDecimal base, int year) {
        if (year <= 1) return base;
        BigDecimal rate = base;
        // apply increments per year starting k = 2..year
        for (int k = 2; k <= year; k++) {
            if (k % 2 == 0) { // tahun genap → +0.1%
                rate = rate.add(new BigDecimal("0.1"));
            } else { // tahun ganjil (>1) → +0.5%
                rate = rate.add(new BigDecimal("0.5"));
            }
        }
        return rate;
    }

    /**
     * This function for calculate monthly payment
     * @param principal
     * @param monthlyRate
     * @param months
     * @return
     */
    private static BigDecimal calcMonthlyPayment(BigDecimal principal, BigDecimal monthlyRate, int months) {
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(new BigDecimal(months), 10, RoundingMode.HALF_UP);
        }
        BigDecimal onePlus = (BigDecimal.ONE.add(monthlyRate)).pow(months);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(onePlus);
        BigDecimal denominator = onePlus.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 10, RoundingMode.HALF_UP);
    }

    /**
     * This function for format currency
     * @param amount
     * @return
     */
    private static String formatCurrency(BigDecimal amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(amount);
    }
}
