package Loesung_KJ;

import java.sql.*;

public class Kunde {

    String Vorname;
    String Nachname;
    String Geschlecht;
    int Bonuspunkte;

    public Kunde(String vorname, String nachname, String geschlecht, int bonuspunkte) {
        Vorname = vorname;
        Nachname = nachname;
        Geschlecht = geschlecht;
        Bonuspunkte = bonuspunkte;
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

    //FRAGE!!!: wie bekomme ich Kundennr aus Tabelle kunden?
    public int getKDNR(){
        int KDNR = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:UeKlausur");
            Statement stmt = con.createStatement();
            //hier wäre die Abfrage mit den bekannten Kundenparametern evtl nicht eindeutig (Namen, etc. könnten mehrfach vorkommen)
            ResultSet rs = stmt.executeQuery("SELECT KDNR FROM kunden WHERE Nachname=" + Nachname + ";");
            rs.next();
           KDNR = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return KDNR;
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
