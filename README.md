Credit Simulator By Wahyu
=========================

Project Java (Java 1.8) console app to simulate monthly installments for vehicle loans.
Package: com.wahyurds.bcadigital.credits
Build tool: Maven
Test framework: JUnit 5

Build & Test
------------
# Build & run tests
mvn clean package

# Run JUnit tests only
mvn test

Run (interactive)
-----------------
# After mvn package a jar is created in target/
java -jar target/credit-simulator-1.0.0-jar-with-dependencies.jar

Run with file input
-------------------
# Provide file path as arg
java -jar target/credit-simulator-1.0.0-jar-with-dependencies.jar sample_inputs.txt

Sample input file format (comma separated per line):
Jenis,Kondisi,Tahun,JumlahPinjaman,Tenor,TandaJadi(DP)
Contoh:
motor,baru,2022,15000000,2,5250000

Notes
-----
- Vehicle types: motor or mobil (case-insensitive)
- Vehicle Condition: baru or bekas (case-insensitive)
- Vehicle Year: 4-digit (e.g., 2019)
- Total Loan Amount: numeric <= 1000000000
- Loan Tenure: integer 1..6 (years)
- Down Payment: numeric amount; DP % minimal: baru >=35%, bekas >=25%

