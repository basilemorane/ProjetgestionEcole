package Controleur.ControleurClasse;

import Modele.Discipline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Controleur.Connexion;
import Modele.Notes;



/**
 * Classe Evaluation DAO
 * permet de recuperer les données dans la base de donnée pour la classe correspondante
 *      - un constructeur avec paramètre
 *      - une methdde create
 *      - une methode delete
 *      - une methode update
 *      - methode find () (Object)
 *      - methode findALL() (ArrayList <Object>)
 *
 */

public class EvaluationDAO extends Controleur<Notes>{

    public EvaluationDAO(Connexion conn) {
        super(conn);
    }

    public boolean create(Notes obj) throws ExceptionAlreadyExistant {
        try {
            Statement stmt = this.connect.getConn().createStatement();
            stmt.executeUpdate("INSERT INTO `evaluation` VALUES (NULL, '" + obj.getId_detail_bulletin() + "', '" + obj.getNote() + "')");
            return true;
        } catch (SQLException evt ){
            throw new ExceptionAlreadyExistant();
        }
    }

    public boolean delete(Notes obj) {

        try {
            ResultSet result = this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AnneeScolaire WHERE nom_anneScolaire ='" + obj.getId_detail_bulletin() +"'");
            if ( result.first()){
                Statement stmt = this.connect.getConn().createStatement();
                stmt.executeUpdate("Delete From AnneeScolaire Where id_annee_scolaire = " + result.getInt("id_annee_scolaire"));
                System.out.println("School year delete ");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public boolean delete(int idNotes) {

        try {
            ResultSet result = this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM evaluation WHERE id_evaluation ='" + idNotes +"'");
            if ( result.first()){
                Statement stmt = this.connect.getConn().createStatement();
                stmt.executeUpdate("Delete From evaluation Where id_evaluation = " + result.getInt("id_evaluation"));
                System.out.println("Notes delete ");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public boolean update(Notes obj, String newName)  {
        try {
                Statement stmt = this.connect.getConn().createStatement();
                stmt.executeUpdate("Update Evaluation SET notes = '" + newName + "' Where id_annee_scolaire  AND nom_anneScolaire = 1'");
            System.out.println("school year update ");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Notes obj, int idEvaluation)  {
        try {
            Statement stmt = this.connect.getConn().createStatement();
            stmt.executeUpdate("Update Evaluation SET note = '" + obj.getNote() + "' Where id_evaluation = '" + idEvaluation + "'");
            System.out.println("Grade update ");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Discipline find (Discipline obj){
        return obj;
    }

    public Discipline find(int id) {
        Discipline year = new Discipline();

        try {
            ResultSet result = this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AnneeScolaire WHERE id_annee_scolaire =" + id);
            if(result.first())
                System.out.println("Find at least one SchoolYear in the database");
            year = new Discipline(
                    result.getInt("id_discipline"),
                    result.getString("nom_discipline")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return year;
    }

    public Notes find(int id, String name) {
        Notes year = new Notes();
        id=0;
        try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM discipline WHERE id_discipline =  AND nom_discipline ='" + name + "'");
                if (result.first())
                    year = new Notes(
                            result.getInt("id_discipline"),
                            result.getInt("id_discipline"),
                            result.getInt("id_discipline")

                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return year;
    }

    public Notes find (int id, int id1, int id2){
        id1 = id2 = 0;
        final Notes anneeScolaire = new Notes();
        return anneeScolaire;
    }

    public Notes find (Notes obj){
        return obj;
    }


    public ArrayList<Notes> findAll () {
        ArrayList<Notes> YearArrayList = new ArrayList<>();
        try {
            ResultSet result =  this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Discipline ");
            while (result.next()){
                YearArrayList.add( new Notes(
                        result.getInt("id_discipline"),
                        result.getInt("id_discipline"),
                        result.getInt("id_discipline")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return YearArrayList;
    }

    public ArrayList<Notes> findAll (int idEnseignment, int idIsnscription, int idTrimestre) {
        ArrayList<Notes> ArrayList = new ArrayList<>();
        try {
            ResultSet result =  this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM `evaluation` INNER JOIN detailbulletin ON evaluation.id_detail_bulletin = detailbulletin.id_detail_bulletin Where detailbulletin.id_enseignement = '" + idEnseignment + " ' AND detailbulletin.id_bulletin = (SELECT id_bulletin from bulletin where bulletin.id_inscription = '" + idIsnscription + "' AND bulletin.id_trimestre = '" + idTrimestre + "')");
            while (result.next()){
                  ArrayList.add( new Notes(
                        result.getInt("id_evaluation"),
                        result.getInt("id_detail_bulletin"),
                        result.getInt("note")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ArrayList;
    }
}
