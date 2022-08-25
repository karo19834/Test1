import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
       // helper.insertTeilnehmerInWithNames("MeineNoten.db", "Jakob", "Wasalski", 150);
       helper.insertNotenWithParameter("MeineNoten.db", 1, "Datenbanken", 2);


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
