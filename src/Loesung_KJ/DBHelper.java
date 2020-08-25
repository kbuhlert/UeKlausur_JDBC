package Loesung_KJ;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private Connection con = null;

    //Aufg 3) Verbindung zur Datenbank
    public void init(){
        String connection = "jdbc:sqlite:UeKlausur2";
        try {
            con = DriverManager.getConnection(connection);
            System.out.println("Verbindung zur Datenbank hergestellt");
           Statement stmt = con.createStatement();
           stmt.executeUpdate("PRAGMA foreign_keys=on;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Aufg 3a) Create Table mit Tabellennamen / Test if table exists

    public boolean tableExists (String tablename){
        boolean tableExists = false;
        String tableCheck = "SELECT * FROM sqlite_master WHERE type='table' AND name='" + tablename + "';";

        try {
            Statement stm = con.createStatement();
           ResultSet rs = stm.executeQuery(tableCheck);

           if(!rs.next()){      //ResultSet ist leer, da kein Datensatz mit dem Namen der Tabelle gefunden wurde
               System.out.println("Tabelle " + tablename + " ist noch nicht in DB vorhanden");
               return tableExists;
           }else return tableExists = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tableExists;
    }

    public void createKundenTable(){
        String createTable = "CREATE TABLE Kunden(KDNR INTEGER PRIMARY KEY AUTOINCREMENT, Vorname VARCHAR(50), Nachname VARCHAR(100), " +
                "Geschlecht VARCHAR(20), Bonuspunkte int);";
        //todo: FRAGE - Sollen wir immer testen, ob Tabelle existiert, wenn wir neue Tabellen kreieren?
        if(!tableExists("Kunden")){
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(createTable);
            System.out.println("Tabelle Kunden wurde erstellt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        }else{
            //todo: FRAGE - Sollen wir Tabelle löschen, wenn sie Bereits existier? Wenn ja wie? Mit neuem DDL-Statement (DROP und dann CREATE)
            // und try/catch im ELSE?
            System.out.println("Die Tabelle 'Kunden' existiert bereits.");
        }
    }

    public void createRechnungenTable(){
        String createTable = "CREATE TABLE Rechnungen(ReNr INTEGER PRIMARY KEY AUTOINCREMENT, Datum VARCHAR(10), " +
                "Gesamtbetrag DECIMAL(9,2), KDNR int, FOREIGN KEY (KDNR) REFERENCES Kunden (KDNR));";

        if(!tableExists("Rechnungen")){
            try {
                Statement stm = con.createStatement();
                stm.executeUpdate(createTable);
                System.out.println("Tabelle Rechnungen wurde erstellt");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Die Tabelle 'Rechnungen' existiert bereits.");
        }
    }
    public void insertDataIntoKunden(Kunde k){
        String vorname = k.getVorname();
        String nachname = k.getNachname();
        String geschlecht = k.getGeschlecht();
        int bonuspunkte = k.getBonuspunkte();
        int KDNR;

        String insert = "INSERT INTO kunden(Vorname, Nachname, Geschlecht, Bonuspunkte) VALUES (?,?,?,?);";
        try {
            PreparedStatement pstm = con.prepareStatement(insert);
            pstm.setString(1,vorname);
            pstm.setString(2,nachname);
            pstm.setString(3,geschlecht);
            pstm.setInt(4,bonuspunkte);
            pstm.executeUpdate();
            System.out.println("Datensatz: Kunde " + nachname + ", " + vorname + " wurde in Tabelle eingefügt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT last_insert_rowid();");
            rs.next();
            KDNR = rs.getInt(1);
            k.setKDNR(KDNR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertDataIntoRechnungen (Rechnung r){
        String datum = r.getDatum();
        double gesamtbetrag = r.getGesamtbetrag();
        int KDNR = r.getKDNR();

        String inserRechnung = "INSERT INTO Rechnungen(Datum, Gesamtbetrag, KDNR) VALUES(?,?,?);";

        try {
            PreparedStatement pstmt = con.prepareStatement(inserRechnung);
            pstmt.setString(1,datum);
            pstmt.setDouble(2,gesamtbetrag);
            pstmt.setInt(3,KDNR);
            pstmt.executeUpdate();
            System.out.println("Datensatz: Rechnung " + datum + ", " + gesamtbetrag + ", " + KDNR    +" wurde in Tabelle eingefügt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Aufg 4a) Kundendaten mittels KundenID ausgeben
    public Kunde getKunde(int KDNR){
        String vorname = null;
        String nachname = null;
        String geschlecht = null;
        int bonuspunkte = 0;

        String selectKunde = "SELECT Vorname, Nachname, Geschlecht, Bonuspunkte FROM kunden WHERE KDNR= " + KDNR ;

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(selectKunde);
            rs.next();
           vorname = rs.getString("Vorname");
           nachname = rs.getString("Nachname");
           geschlecht = rs.getString("Geschlecht");
           bonuspunkte = rs.getInt("Bonuspunkte");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Kunde k = new Kunde(vorname,nachname,geschlecht,bonuspunkte);
        return k;
    }

    //Aufg 4b) Alle Kunden in Liste ausgeben und wenn Geschlecht Null, dann mit "nicht definiert" ersetzen
    //todo: Frage - Wie könnte ich hier auch die KDNR dem Kunden mitgeben?
    public ArrayList<Kunde> getAlleKunden(){
        String alleKundenSelect = "SELECT * FROM kunden";
        ArrayList<Kunde> kundenliste = new ArrayList<>();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(alleKundenSelect);
            while (rs.next()){
               String vorname = rs.getString("Vorname");
               String nachname = rs.getString("Nachname");
               String  geschlecht = rs.getString("Geschlecht");
               int bonuspunkte = rs.getInt("Bonuspunkte");

               if(geschlecht == null){
                   geschlecht = "nicht definiert";
               }

               kundenliste.add(new Kunde(vorname,nachname,geschlecht,bonuspunkte));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return kundenliste;
    }

    //Aufg 5a) Einfügen von Kunde und Ausgabe von rowID
    public int insertKunde (Kunde k){
        int KDNR = k.getKDNR();
        String vorname = k.getVorname();
        String nachname = k.getNachname();
        String geschlecht = k.getGeschlecht();
        int bonuspunkte = k.getBonuspunkte();

        String insertKunde = "INSERT INTO kunden(Vorname, Nachname, Geschlecht, Bonuspunkte) VALUES (?,?,?,?);";
        try {
            PreparedStatement pstm = con.prepareStatement(insertKunde);
            pstm.setString(1,vorname);
            pstm.setString(2,nachname);
            pstm.setString(3,geschlecht);
            pstm.setInt(4,bonuspunkte);
            pstm.executeUpdate();
            System.out.println("Datensatz: Kunde " + nachname + ", " + vorname + " wurde in Tabelle eingefügt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT last_insert_rowid();");
            rs.next();
            KDNR = rs.getInt(1);
            k.setKDNR(KDNR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return KDNR;
    }


    //Aufg 5b) UPDATE von Kundendaten
    public void updateKunde (Kunde k){

        String vorname = k.getVorname();
        String nachname = k.getNachname();
        String geschlecht = k.getGeschlecht();
        int bonuspunkte = k.getBonuspunkte();
        int KDNR = k.getKDNR();   //todo: Klären, wie bekomme ich KDNR, wenn es nicht als PArameter übergeben wird

        String updateKunde = "UPDATE Kunde SET Vorname='" + vorname +
                "', Nachname='" + nachname +
                "', Geschlecht='" + geschlecht +
                "', Bonuspunkte='" + bonuspunkte + "' WHERE KDNR=" + KDNR;
        try {
            Statement stm = null;
            stm = con.createStatement();

            if(stm.executeUpdate(updateKunde) ==0){
                System.out.println("Kunde existiert nicht");
                throw new KundeDoesNotExist("Kunde existiert nicht");
            }else {
                System.out.println("Kundendaten wurden aktualisiert");
            }
        } catch (SQLException | KundeDoesNotExist throwables) {
            throwables.printStackTrace();
        }
    }

    //Aufg 6a)


}
