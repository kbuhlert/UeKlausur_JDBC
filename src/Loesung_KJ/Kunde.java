package Loesung_KJ;

import java.sql.*;

public class Kunde {

    String Vorname;
    String Nachname;
    String Geschlecht;
    int Bonuspunkte;
    int KDNR;

    public Kunde(String vorname, String nachname, String geschlecht, int bonuspunkte) {
        Vorname = vorname;
        Nachname = nachname;
        Geschlecht = geschlecht;
        Bonuspunkte = bonuspunkte;
        int KDNR = 0;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public String getGeschlecht() {
        return Geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        Geschlecht = geschlecht;
    }

    public int getBonuspunkte() {
        return Bonuspunkte;
    }

    public void setBonuspunkte(int bonuspunkte) {
        Bonuspunkte = bonuspunkte;
    }

    public int getKDNR() {
        return KDNR;
    }

    public void setKDNR(int KDNR) {
        this.KDNR = KDNR;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "Vorname='" + Vorname + '\'' +
                ", Nachname='" + Nachname + '\'' +
                ", Geschlecht='" + Geschlecht + '\'' +
                ", Bonuspunkte=" + Bonuspunkte +
                '}';
    }
}
