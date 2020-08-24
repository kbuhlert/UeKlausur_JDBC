package Loesung_KJ;

import java.sql.*;

public class Rechnung {
    String Datum;
    Double Gesamtbetrag;
    int KDNR;

    public Rechnung(String datum, Double gesamtbetrag, int KDNR) {
        Datum = datum;
        Gesamtbetrag = gesamtbetrag;
        this.KDNR = KDNR;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }


    public Double getGesamtbetrag() {
        return Gesamtbetrag;
    }

    public void setGesamtbetrag(Double gesamtbetrag) {
        Gesamtbetrag = gesamtbetrag;
    }

    public int getKDNR() {
        return KDNR;
    }

    public void setKDNR(int KDNR) {
        this.KDNR = KDNR;
    }

    @Override
    public String toString() {
        return "Rechnung{" +
                "Datum='" + Datum + '\'' +
                ", Gesamtbetrag=" + Gesamtbetrag +
                '}';
    }
}
