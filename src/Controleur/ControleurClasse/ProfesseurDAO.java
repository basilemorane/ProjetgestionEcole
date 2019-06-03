package Controleur.ControleurClasse;

import Modele.Eleve;
import Modele.Inscription;
import Modele.Professeur;
import Controleur.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProfesseurDAO extends Controleur<Professeur> {

        public ProfesseurDAO(Connexion conn) {
            super(conn);
        }

        public boolean create(Professeur obj) throws ExceptionAlreadyExistant {
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE  nom_personne ='" + obj.getNom() +"' AND prenom_personne ='" + obj.getPrenom() + "' AND type_personne = 1");
                if(result.first()) {
                    throw new ExceptionAlreadyExistant();
                }
                Statement stmt =  this.connect.getConn().createStatement();
                stmt.executeUpdate("Insert INTO personne VALUES (Null,'" + obj.getNom()+"', '" + obj.getPrenom() + "', 1)");
                System.out.println("New teacher create in the databse : ");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean delete(Professeur obj) {
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE  nom_personne ='" + obj.getNom() +"' AND prenom_personne ='" + obj.getPrenom() + "' AND type_personne = 1");
                if(result.first()) {
                    Statement stmt = this.connect.getConn().createStatement();
                    stmt.executeUpdate("Delete From personne Where id_personne = " + result.getInt("id_personne"));
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean update(Professeur obj, String newName) {
            String[] data = newName.split(" ", 2);
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE  nom_personne ='" + data[0]+"' AND prenom_personne ='" + data[1] + "' AND type_personne = 1");
                if(result.first()) {
                    Statement stmt = this.connect.getConn().createStatement();
                    stmt.executeUpdate("Update personne SET nom_personne = '" + obj.getNom() + "', prenom_personne = '" + obj.getPrenom() + "'  Where id_personne = " + result.getInt("id_personne"));
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public Professeur find(int id) {
            Professeur eleve = new Professeur();

            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE type_personne = '1' AND id_personne = " + id);
                if(result.first())
                    eleve = new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    );
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return eleve;
        }

        public Professeur find (Professeur obj){
            Professeur eleve = new Professeur();
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE  nom_personne ='" + obj.getNom() +"' AND prenom_personne ='" + obj.getPrenom() + "' AND type_personne = 1");
                if(result.first()) {
                    eleve = new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return eleve;
        }


        public Professeur find(int id, String name) {
            Professeur eleve = new Professeur();

            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE nom_personne ='" + name + "'");
                if(result.first())
                    eleve = new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    );
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return eleve;
        }

        public Professeur find (int id, int id1, int id2){
            id1 =0;
            Professeur eleve = new Professeur();
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        //ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT DISCTINCT * FROM personne INNER JOIN inscription ON personne.id_personne = inscription.id_personne AND inscription.id_classe = " + id2);
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM inscription WHERE id_classe = " + id2 + " AND id_inscription = " + id);
                if (result.first()) {
                    Inscription inscription = new Inscription(
                            result.getInt("id_inscription"),
                            result.getInt("id_classe"),
                            result.getInt("id_personne")
                    );
                    result = this.connect.getConn().createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            //ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT DISCTINCT * FROM personne INNER JOIN inscription ON personne.id_personne = inscription.id_personne AND inscription.id_classe = " + id2 ');
                            ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE id_personne = " + inscription.getId() + " AND type_personne = '1'");
                    if (result.first())
                        eleve = new Professeur(
                                result.getInt("id_personne"),
                                result.getString("nom_personne"),
                                result.getString("prenom_personne")
                        );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return eleve;
        }

        public Professeur recherch ( int id, String name){
            Professeur eleve = new Professeur();

            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE id_personne = " + id + " AND nom_personne LIKE '%" + name + "%'" );
                if(result.first())
                    eleve = new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    );
                else {
                    result = this.connect.getConn().createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE id_personne = " + id + " AND prenom_personne LIKE '%" + name + "%'");
                    if (result.first())
                        eleve = new Professeur(
                                result.getInt("id_personne"),
                                result.getString("nom_personne"),
                                result.getString("prenom_personne")
                        );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return eleve;
        }
        public ArrayList<Professeur> findAll (int id2) {
            ArrayList<Professeur> ArrayList = new ArrayList<>();
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne INNER JOIN inscription On inscription.id_classe = "+ id2 + " AND personne.id_personne = inscription.id_personne");
                while (result.next()) {
                    ArrayList.add(new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ArrayList;
        }

        public ArrayList<Professeur> findAll () {
            ArrayList<Professeur> ArrayList = new ArrayList<>();
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        //ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT DISCTINCT * FROM personne INNER JOIN inscription ON personne.id_personne = inscription.id_personne AND inscription.id_classe = " + id2);
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM inscription " );
                if (result.first()) {
                    Inscription inscription = new Inscription(
                            result.getInt("id_inscription"),
                            result.getInt("id_classe"),
                            result.getInt("id_personne")
                    );
                    result = this.connect.getConn().createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne WHERE id_personne = " + inscription.getId() + " AND type_personne = '1'");
                    if (result.first())
                        ArrayList.add(new Professeur(
                                result.getInt("id_personne"),
                                result.getString("nom_personne"),
                                result.getString("prenom_personne")
                        ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ArrayList;
        }


        public ArrayList<Professeur> findAllTeacher () {
            ArrayList<Professeur> ArrayList = new ArrayList<>();
            try {
                ResultSet result = this.connect.getConn().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM personne where type_personne = '1' " );
                while (result.next()) {
                    ArrayList.add(new Professeur(
                            result.getInt("id_personne"),
                            result.getString("nom_personne"),
                            result.getString("prenom_personne")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ArrayList;
        }
    }

