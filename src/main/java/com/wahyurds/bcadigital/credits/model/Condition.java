package com.wahyurds.bcadigital.credits.model;

public enum Condition {
    BARU, BEKAS;

    public static Condition fromString(String s) {
        if (s == null) throw new IllegalArgumentException("Kondisi kosong");
        String t = s.trim().toLowerCase();
        if (t.startsWith("b") && t.contains("aru") || t.equals("baru")) return BARU;
        if (t.startsWith("b") && t.contains("ekas") || t.equals("bekas") || t.equals("used")) return BEKAS;
        // fallback: check exact
        if ("baru".equals(t)) return BARU;
        if ("bekas".equals(t)) return BEKAS;
        throw new IllegalArgumentException("Kondisi tidak ditemukan: " + s);
    }
}
