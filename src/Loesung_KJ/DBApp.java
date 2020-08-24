package Loesung_KJ;

public class DBApp {

    public static void main(String[] args) {
        DBHelper dbh = new DBHelper();
        dbh.init();
        dbh.createKundenTable();
        dbh.createRechnungenTable();
        dbh.insertDataIntoKunden(new Kunde("Manfred", "Steyer", "m", 10));
        dbh.insertDataIntoKunden(new Kunde("Elisabeth", "Mayer", "f", 20));
        dbh.insertDataIntoKunden(new Kunde("Johann", "Grabner", "m", 15));
        dbh.insertDataIntoKunden(new Kunde("Andreas", "Huber", "m", 17));
        dbh.insertDataIntoKunden(new Kunde("Testperson", "Nullwert", null, 10));
        dbh.insertDataIntoRechnungen(new Rechnung("12.10.2012", 500.00, 1));
        dbh.insertDataIntoRechnungen(new Rechnung("14.10.2012", 700.00, 1));
        dbh.insertDataIntoRechnungen(new Rechnung("20.10.2012", 450.00, 2));
        dbh.insertDataIntoRechnungen(new Rechnung("29.10.2012", 320.00, 3));
        System.out.println(dbh.getKunde(1));
        System.out.println(dbh.getKunde(2));
        System.out.println(dbh.getKunde(3));
        System.out.println(dbh.getKunde(8));
        System.out.println(dbh.getAlleKunden());
        System.out.println("KundenId des letzten eingefügten Kunden: " + dbh.insertKunde(new Kunde("Susi", "Sorglos", "f", 28)));

    }
}
