package Controleur.ControleurClasse;

import Modele.AnneeScolaire;
import Modele.Discipline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Controleur.Connexion;

public class DisciplineDAO extends Controleur<Discipline>{
    private int nombre_année_scolaire;
    private int nombre;

    public DisciplineDAO(Connexion conn) {
        super(conn);
        this.nombre = findAll().size()-1;
        this.nombre_année_scolaire = findAll().get(this.nombre).getId_discipline();
    }

    public boolean create(Discipline obj) throws ExceptionAlreadyExistant {

        try {
            ResultSet result = this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Discipline WHERE Discipline ='" + obj.getNom_classe() +"'");
            if(result.first()) {
                throw new ExceptionAlreadyExistant();
            }
            Statement stmt =  this.connect.getConn().createStatement();
            stmt.executeUpdate("Insert INTO Discipline VALUES (Null,'" + obj.getNom_classe()+"')");
            System.out.println("New discipline create in the databse : ");
            this.nombre_année_scolaire+=1;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Discipline obj) {

        try {
            ResultSet result = this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AnneeScolaire WHERE nom_anneScolaire ='" + obj.getNom_classe() +"'");
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

    public boolean update(Discipline obj, String newName) throws ExceptionAlreadyExistant {
        try {
            for ( int i=1; i< this.nombre_année_scolaire+1; i++) {

                if ( find(1,newName).getId_discipline() != 0 ) {
                    throw new ExceptionAlreadyExistant();
                }

                Statement stmt = this.connect.getConn().createStatement();
                stmt.executeUpdate("Update AnneeScolaire SET nom_anneScolaire = '" + newName + "' Where id_annee_scolaire = " + i + " AND nom_anneScolaire = '" + obj.getNom_classe() + "'");
            }
            System.out.println("school year update ");
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

    public Discipline find(int id, String name) {
        Discipline year = new Discipline();
        id=0;
        try {
            for (int i=0; i< nombre_année_scolaire+1; i++) {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AnneeScolaire WHERE id_annee_scolaire = " + i + " AND nom_anneScolaire ='" + name + "'");
                if (result.first())
                    year = new Discipline(
                            result.getInt("id_discipline"),
                            result.getString("nom_discipline")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return year;
    }

    public Discipline find (int id, int id1, int id2){
        id1 = id2 = 0;
        final Discipline anneeScolaire = find(id);
        return anneeScolaire;
    }


    public ArrayList<Discipline> findAll () {
        ArrayList<Discipline> YearArrayList = new ArrayList<>();
        try {
            ResultSet result =  this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Discipline "); //order by nom_anneScolaire DESC");
            while (result.next()){
                YearArrayList.add( new Discipline(
                        result.getInt("id_discipline"),
                        result.getString("nom_discipline")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return YearArrayList;
    }

    public ArrayList<Discipline> findAll (int id2) {
        id2 = 0;
        ArrayList<Discipline> DisciplineArrayList = new ArrayList<>();
        try {
            ResultSet result =  this.connect.getConn().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                  //  ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM `discipline` INNER JOIN enseignement ON discipline.id_discipline = enseignement.id_discipline WHERE enseignement.id_classe = " + id2 );
            ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT discipline.id_discipline, discipline.nom_discipline FROM `discipline` INNER JOIN enseignement ON discipline.id_discipline = enseignement.id_discipline WHERE enseignement.id_classe = " + id2 );
            while (result.next()){
                DisciplineArrayList.add( new Discipline(
                        result.getInt("id_discipline"),
                        result.getString("nom_discipline")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DisciplineArrayList;
    }
}