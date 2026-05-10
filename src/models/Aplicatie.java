package models;

public class Aplicatie {

    private int idAplicatie;
    private String numeAplicatie;
    private String categorie;
    private boolean esteBlocata;

    public Aplicatie(int idAplicatie, String numeAplicatie, String categorie) {
        this.idAplicatie = idAplicatie;
        this.numeAplicatie = numeAplicatie;
        this.categorie = categorie;
        this.esteBlocata = false;
    }

    public void blocheaza() {
        this.esteBlocata = true;
        System.out.println("Aplicatia '" + numeAplicatie + "' a fost blocata.");
    }

    public void deblocheaza() {
        this.esteBlocata = false;
        System.out.println("Aplicatia '" + numeAplicatie + "' a fost deblocata.");
    }

    public boolean estePermisa() {
        return !esteBlocata;
    }

    public int getIdAplicatie() { return idAplicatie; }
    public String getNumeAplicatie() { return numeAplicatie; }
    public String getCategorie() { return categorie; }
    public boolean isEsteBlocata() { return esteBlocata; }

    public void setCategorie(String categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Aplicatie{id=" + idAplicatie + ", nume='" + numeAplicatie +
               "', categorie='" + categorie + "', blocata=" + esteBlocata + "}";
    }
}
