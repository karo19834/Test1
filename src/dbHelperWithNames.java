import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class dbHelperWithNames {




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

    public void deleteTeilnehmerWithID(
            String dbName, int teilnNr ){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;

        try (Connection conn = DriverManager.getConnection(url)) {

            conn.createStatement().execute("PRAGMA foreign_keys = ON");
            String delString = "DELETE FROM TeilnehmerInnen WHERE TeilInNr= " + teilnNr;

            Statement teilDelStmt = conn.createStatement();
            teilDelStmt.executeUpdate(delString); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

        } catch (SQLException e) {
            if (e.getMessage().toLowerCase(Locale.ROOT).contains("foreign key")){
                System.out.println("TN kann nicht gelöscht werden, weil noch Noten vorhanden sind");
            } else {
                System.out.println(e.getMessage());
            }
        }

    }


    public void selectTeilnehmerInnen(String dbName){

        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


            String selTN="SELECT TeilInNr, Vorname, Nachname, Bonuspunkte ";
            selTN += " FROM TeilnehmerInnen";

            Statement selStmt = conn.createStatement();
            ResultSet rs =  selStmt.executeQuery(selTN);


            while(rs.next()) {
                System.out.printf("%d %s %s %f\n",
                        rs.getInt("TeilInNr"), rs.getString("Vorname"),
                        rs.getString("Nachname"), rs.getDouble("Bonuspunkte"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printAllNoten(String dbName){

        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

            String selTN="SELECT t.TeilInNr, Nachname, Fach, Note ";
            selTN += " FROM TeilnehmerInnen t INNER JOIN Noten n";
            selTN += " ON t.TeilInNr = n.TeilInNr ";

            Statement selStmt = conn.createStatement();
            ResultSet rs =  selStmt.executeQuery(selTN);

            while(rs.next()) {
                System.out.printf("%d %s %s %d\n",
                        rs.getInt("TeilInNr"),
                        rs.getString("Nachname"),
                        rs.getString("Fach"),
                        rs.getInt("Note"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertNotenWithParameter (String dbName , int teilNr, String fach, int note){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            conn.createStatement().execute("PRAGMA foreign_keys = ON");

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

    public void insertNotenPrepared(
            String dbName,
            int teilNr,
            String fach,
            int note){
        String url = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


            //BEI DML
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            String insertNoten="INSERT INTO Noten(TeilInNr, Fach, Note) VALUES(?,?,?)";


            PreparedStatement notenInsStmt = conn.prepareStatement(insertNoten);
            notenInsStmt.setInt(1,teilNr);
            notenInsStmt.setString(2,fach); //drop table MeineKunden
            notenInsStmt.setInt(3,note);

            notenInsStmt.executeUpdate(); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("Note hinzugefügt");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    String dbConnectionString  = "jdbc:sqlite:/Users/karolinawasalska/Desktop/SQL/MeineNoten.db";

    public int updateTeilnehmerIn(TeilnehmerIn teilnehmerIn){
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String updateTeilnehmerIn = "UPDATE TeilnehmerInnen SET ";
            updateTeilnehmerIn += "Vorname=?, Nachname=?, Bonuspunkte=? WHERE TeilInNr=?";

            PreparedStatement updStmt =  conn.prepareStatement(updateTeilnehmerIn);
            updStmt.setString(1,teilnehmerIn.getVorname());
            updStmt.setString(2,teilnehmerIn.getNachname());
            updStmt.setDouble(3,teilnehmerIn.getBonuspunkte());
            updStmt.setInt(4,teilnehmerIn.getTeilInNr());

            rowCount = updStmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }


    public TeilnehmerIn getTeilnehmerIn(int teilInNr) {
        TeilnehmerIn tGefunden =new TeilnehmerIn();

        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {
            //SELECT * FROM TeilnehmerInnen WHERE Bonsupunkte >? OR (Vorname = ? AND TeilInNr < ?
            String selTeilnehmerIn = "SELECT Vorname, Nachname, Bonuspunkte FROM TeilnehmerInnen WHERE TeilInNr=?";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
            pSelect.setInt(1,teilInNr);;
            ResultSet rs=  pSelect.executeQuery();

            if (rs.next()){
                //Es gibt eine Zeile - eine Teilnehmerin
                tGefunden.setTeilInNr(teilInNr);
                tGefunden.setVorname(rs.getString("Vorname"));
                tGefunden.setNachname(rs.getString("Nachname"));
                tGefunden.setBonuspunkte(rs.getDouble("Bonuspunkte"));
            } else {
                //kein TN Gefunden
                tGefunden.setVorname("nicht vorhanden");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  tGefunden;
    }

    public ArrayList<TeilnehmerIn> getAlleTeilnehmerInnen() {
        ArrayList<Integer> teilInNrList =new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selAlleTeilnehmerIn = "SELECT TeilInNr FROM TeilnehmerInnen";
            PreparedStatement pSelect = conn.prepareStatement(selAlleTeilnehmerIn);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                teilInNrList.add(rs.getInt("TeilInNr"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<TeilnehmerIn> liste=new ArrayList<>();

        for (int nr : teilInNrList) {
            liste.add(getTeilnehmerIn(nr));
        }

        return liste;

    }


    public void deleteNoten(int teilNr){

        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String deleteNote= "DELETE FROM NOTEN WHERE TeilInNr=?";
            PreparedStatement deleteStmt  = conn.prepareStatement(deleteNote);
            deleteStmt.setInt(1,teilNr);
            deleteStmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int insertTeilnehmerIn(TeilnehmerIn teilnehmerIn){
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String insertTeilnehmerIn = "INSERT INTO TeilnehmerInnen(Vorname,Nachname,Bonuspunkte) ";
            insertTeilnehmerIn += "VALUES(?,?,?)";

            PreparedStatement insStmt =  conn.prepareStatement(insertTeilnehmerIn);
            insStmt.setString(1,teilnehmerIn.getVorname());
            insStmt.setString(2,teilnehmerIn.getNachname());
            insStmt.setDouble(3,teilnehmerIn.getBonuspunkte());

            rowCount = insStmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }
    public ArrayList<TeilnehmerIn> getAlleTeilnehmerInnenMitFilter(double minBonuspunkte) {
        ArrayList<Integer> teilInNrList =new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selAlleTeilnehmerIn = "SELECT TeilInNr FROM TeilnehmerInnen WHERE Bonuspunkte > ?";

            PreparedStatement pSelect = conn.prepareStatement(selAlleTeilnehmerIn);
            pSelect.setDouble(1,minBonuspunkte);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                teilInNrList.add(rs.getInt("TeilInNr"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<TeilnehmerIn> liste=new ArrayList<>();

        for (int nr : teilInNrList) {
            liste.add(getTeilnehmerIn(nr));
        }

        return liste;
    }

    public ArrayList<Noten> getNotenFuerTN(int teilInNr) {
        ArrayList<Noten> noten =new ArrayList<Noten>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selectNoten = "SELECT NotenId, Fach, Note fROm Noten WHERE teilinNr = ?";

            PreparedStatement pSelect = conn.prepareStatement(selectNoten);
            pSelect.setInt(1,teilInNr);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                Noten n=new Noten();
                n.setNotenId(rs.getInt("NotenId"));
                n.setTeilInNr(teilInNr);
                n.setFach(rs.getString("Fach"));
                n.setNote(rs.getInt("Note"));
                noten.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return noten;

    }


}
