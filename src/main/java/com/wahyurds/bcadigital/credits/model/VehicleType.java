package com.wahyurds.bcadigital.credits.model;

public enum VehicleType {
    MOTOR, MOBIL;

    public static VehicleType fromString(String s) {
        if (s == null) throw new IllegalArgumentException("Jenis Kendaraan kosong");
        String t = s.trim().toLowerCase();
        if (t.startsWith("mot") || t.equals("motor")) return MOTOR;
        if (t.startsWith("mob") || t.equals("mobil") || t.equals("car")) return MOBIL;
        throw new IllegalArgumentException("Jenis Kendaraan tidak ditemukan: " + s);
    }
}
