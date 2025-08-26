BCA DIGITAL Credit Simulator by wahyu
=========================

Project Java (Java 1.8) console app to simulate monthly installments for vehicle loans.
Package: com.wahyurds.bcadigital.credits
Build tool: Maven
Test framework: JUnit 5

# Welcome

Run JUnit tests only
-
mvn test

Build & run tests
-
mvn clean package

How to running this app?
---
Before running and try, make sure already create package, you can run from IntelliJ Idea.
File - Run - Edit Configurations - in command line type (clean package) or mvn clean package
![Logo](images\intellij.jpg)
And make sure file with name credit-simulator-1.0.0-shaded.jar created.
![Logo](images\explorer.jpg)
---
There are 2 ways :
1. Running from bat file (windows)
2. Running from bat file (windows) using parameter sample_inputs.txt

![Logo](images\cmd.jpg)

Go to folder bin and type this script : 
1. .\credit_simulator
2. .\credit_simulator sample_inputs.txt 

![Logo](images\cmd2.jpg)

Please choose 1-3 option and follow the instructions

Sample input file format sample_inputs.txt (comma separated per line):
-
- Jenis,Kondisi,Tahun,JumlahPinjaman,Tenor,TandaJadi(DP)
- motor,baru,2022,15000000,2,5250000

Notes
-----
- Vehicle types: motor or mobil (case-insensitive)
- Vehicle Condition: baru or bekas (case-insensitive)
- Vehicle Year: 4-digit (e.g., 2019)
- Total Loan Amount: numeric <= 1000000000
- Loan Tenure: integer 1..6 (years)
- Down Payment: numeric amount; DP % minimal: baru >=35%, bekas >=25%

