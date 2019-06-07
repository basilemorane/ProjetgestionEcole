package Modele;

public class Bulletin {
    private int id;
    private int id_trimestre;
    private int id_inscription;

    public Bulletin () {
        this.id = 0;
        this.id_trimestre = 0;
        this.id_inscription = 0;
    }

    public Bulletin (int id, int idtrimestre, int idInscription) {
        this.id = id;
        this.id_trimestre = idtrimestre;
        this.id_inscription = idInscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_inscription() {
        return id_inscription;
    }

    public int getId_trimestre() {
        return id_trimestre;
    }

    public void setId_inscription(int id_inscription) {
        this.id_inscription = id_inscription;
    }

    public void setId_trimestre(int id_trimestre) {
        this.id_trimestre = id_trimestre;
    }

}