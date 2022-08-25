import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbHelperWithNames {


    //Aufgaben
//Annahme Tabelle Note existiert bereits
//1. insertNoten(teilInNr, fach, note)
//2. updateNote(notenId, neuesFach, neueNote) ---> Ausgabe - Datensatz wurde geändert oder NICHT gefunden   7, 'DBP mit Java', 1
//2. deleteNote(notenId) ---> Note wurde gelöscht oder wurde nicht gefunden


    public void insertTeilnehmerInWithNames (String dbName, String vorname, String nachname, int bonusPunkte){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String insertTeilnehmerIn="INSERT INTO TeilnehmerInnen(Vorname, Nachname, Bonuspunkte) ";
            insertTeilnehmerIn += "VALUES( '" + vorname + "','"+ nachname +"',"+ bonusPunkte+ ")";

            Statement teilInsStmt = conn.createStatement();

            teilInsStmt.executeUpdate(insertTeilnehmerIn);

            System.out.println("TeilnehmerIn inserted");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertNotenWithParameter (String dbName , int teilNr, String fach, int note){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String insertTeilnehmerIn="INSERT INTO Noten (TeilInNr, Fach, Note) ";
            insertTeilnehmerIn += "VALUES( "   +teilNr + ",'" + fach + "'," + note + ")";

            Statement teilInsStmt = conn.createStatement();
            teilInsStmt.executeUpdate(insertTeilnehmerIn);
            System.out.println("Noten inserted");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int updateNoten(
            String dbName,
            int notenID , int neueTeilNr, String neuerFach, int neueNote){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;

        int rowsAffected=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String updString = "UPDATE Noten SET ";
            updString += " TeilInNr=" + neueTeilNr + " , ";
            updString += " Fach='" + neuerFach+ "', ";
            updString += " Note=" + neueNote + " ";
            updString += " WHERE NotenID=" + notenID;

            Statement teilUpdStmt = conn.createStatement();

            rowsAffected= teilUpdStmt.executeUpdate(updString); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    public int deleteNoten(
            String dbName, int notenID ){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;

        int rowsAffected=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String updString = "DELETE FROM Noten WHERE NotenID= " + notenID;

            Statement teilUpdStmt = conn.createStatement();

            rowsAffected= teilUpdStmt.executeUpdate(updString); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }
}
