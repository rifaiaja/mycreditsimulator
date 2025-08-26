package com.wahyurds.bcadigital.credits.app;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.service.CreditCalculator;
import com.wahyurds.bcadigital.credits.service.InterestCalculator;
import com.wahyurds.bcadigital.credits.util.InputParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
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

    private static void runFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int idx = 1;
            while ((line = br.readLine()) != null) {
                try {
                    processLine(line, idx++);
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
        System.out.println("Credit Simulator (interactive). Masukkan data:");
        System.out.print("Jenis Kendaraan (motor/mobil): ");
        String jenis = scanner.nextLine();
        System.out.print("Kondisi (baru/bekas): ");
        String kondisi = scanner.nextLine();
        System.out.print("Tahun Kendaraan (YYYY): ");
        String tahun = scanner.nextLine();
        System.out.print("Jumlah Pinjaman Total: ");
        String jumlah = scanner.nextLine();
        System.out.print("Tenor (tahun 1-6): ");
        String tenor = scanner.nextLine();
        System.out.print("Jumlah DP: ");
        String dp = scanner.nextLine();

        String combined = String.join(",", jenis, kondisi, tahun, jumlah, tenor, dp);
        processLine(combined, 1);
    }

    private static void processLine(String line, int idx) {
        InputParser.ParsedInput in = InputParser.parseLine(line);
        CreditCalculator.validateInputs(in.vehicleType, in.vehicleCondition == null ? Condition.BEKAS : in.vehicleCondition, in.vehicleYear, in.totalLoanAmount, in.loanTenure, in.downPayment);
        double rate = InterestCalculator.annualRate(in.vehicleType, in.vehicleYear);
        double monthly = CreditCalculator.calculateMonthlyInstallment(in.vehicleType, in.vehicleYear, in.totalLoanAmount, in.loanTenure, in.downPayment);
        System.out.println("=== Hasil " + idx + " ===");
        System.out.println("Tipe: " + in.vehicleType + ", Kondisi: " + in.vehicleCondition + ", Tahun: " + in.vehicleYear + ", DP: " + in.downPayment);
        System.out.printf("Suku Bunga: %.3f%%\n", rate * 100);
        System.out.printf("Cicilan perbulan: %.0f\n", monthly);
        System.out.println();
    }
}
