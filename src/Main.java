import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Good Morning Campus2");
        //createGameTable("MyMenuDB.db");
        //Init - DBHelper

        dbHelper myHelper =new dbHelper();
        dbHelperWithNames helper = new dbHelperWithNames();
//        myHelper.createDB("MeineNoten.db");
        //myHelper.createTableNoten("MeineNoten.db");
//        myHelper.createTableTeilnehmerInnen("MeineNoten.db");
        //myHelper.updateTeilnehmerIn("MeineNoten.db");
       //helper.insertTeilnehmerInWithNames("MeineNoten.db", "Karol", "Karolczyk", 150);
       //helper.insertNotenWithParameter("MeineNoten.db", 2, "Datenbanken", 1);

        //helper.selectTeilnehmerInnen("MeineNoten.db");
        //helper.printAllNoten("MeineNoten.db");
        //helper.insertNotenPrepared("MeineNoten.db", 1,"DBP", 1);

//        TeilnehmerIn teilnehmerIn =new TeilnehmerIn(6,"Elisabeth","Unger",200);
//        helper.updateTeilnehmerIn(teilnehmerIn);

//        TeilnehmerIn teilnehmerIn3 =new TeilnehmerIn(1,"Karolina","Wasalska",250);
//        helper.updateTeilnehmerIn(teilnehmerIn3);

//        TeilnehmerIn t5 = helper.getTeilnehmerIn(5);
//        System.out.println(t5);
//        t5.setBonuspunkte(30);
//        System.out.println(t5);
//        helper.updateTeilnehmerIn(t5);


        TeilnehmerIn t1=helper.getTeilnehmerIn(1);
        System.out.println("\n" + t1);
        System.out.println(helper.getNotenFuerTN(1));

//        ArrayList<TeilnehmerIn> alleTeilnehmerInnen = helper.getAlleTeilnehmerInnen();
//        System.out.println("Alle TN:\n" + alleTeilnehmerInnen);
//        helper.deleteNoten(3);
//
//
//
//
//        TeilnehmerIn suche = helper.getTeilnehmerIn(6);
//
//        TeilnehmerIn tNeu =new TeilnehmerIn(-1, "Elisabeth","Unger",300);
//        helper.insertTeilnehmerIn(tNeu);
//
//        System.out.println("Mehr als 100 Bonuspunkte: " + helper.getAlleTeilnehmerInnenMitFilter(100));

//        int affectedRows = helper.updateNoten("MeineNoten.db",1,3,"Webdesign",2);
//
//        if (affectedRows==0){
//            System.out.println("NotenID wurde nicht gefunden");
//        } else {
//            System.out.println("Note wurde geändert");
//        }
//
//        int affectedRows2 = helper.deleteNoten("MeineNoten.db",1);
//
//        if (affectedRows2==0){
//            System.out.println("NotenID wurde nicht gefunden");
//        } else {
//            System.out.println("Note wurde gelöscht");
//        }
        //helper.deleteTeilnehmerWithID("MeineNoten.db",7);
    }











    public  static void createGameTable(String fileName ) {
        // SQLite connection string
        //url "jdbc:
        //jdbc:sqllite
        //jdbc:oracle
        //jdbc:access
        //jdbc:sqlite:PFAD

        /*
        Connection conn =null;
        try{
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
        }
        catch (SQLException ex)
        {
        }
        finally {
            if (conn!=null){
                try    {
                    conn.close();
                }
                catch(SQLException ex){
                }
            }
        }
         */
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +fileName;

        // SQL statement for creating a new table
        String ddlStatementToCreateATable = "CREATE TABLE Menu (\n"
                + "	MenuId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	Menu Bezeichnung VARCHAR(255),\n"
                + "	Preis DEZIMAL(3,2) \n"
                + ");";
        //1. Connection aufbauen -- Driver muss vorhanden sein - Project Structure - Modules - Dependeny - sqllite-jdbc-3.36.0.1.jar (Moodle)
        try (Connection conn = DriverManager.getConnection(url)){
            //2 Statement über die Connection holen - createStatement
            Statement stmt = conn.createStatement();
            //3 Statement abschicken --- execute
            stmt.execute(ddlStatementToCreateATable);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
