package com.wahyurds.bcadigital.credits.util;

import com.wahyurds.bcadigital.credits.model.Condition;
import com.wahyurds.bcadigital.credits.model.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void parseComma() {
        String line = "motor,baru,2022,15000000,2,5250000";
        InputParser.ParsedInput p = InputParser.parseLine(line);
        assertEquals(VehicleType.MOTOR, p.vehicleType);
        assertEquals(Condition.BARU, p.vehicleCondition);
        assertEquals(2022, p.vehicleYear);
    }

    @Test
    void parseWhitespace() {
        String line = "mobil bekas 2018 120000000 4 30000000";
        InputParser.ParsedInput p = InputParser.parseLine(line);
        assertEquals(VehicleType.MOBIL, p.vehicleType);
        assertEquals(Condition.BEKAS, p.vehicleCondition);
    }
}
