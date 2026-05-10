package models;

public abstract class Utilizator {

    private int id;
    private String nume;
    private String email;
    private String parola;

    public Utilizator(int id, String nume, String email, String parola) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public boolean autentificare(String emailIntroodus, String parolaIntrodusa) {
        return this.email.equals(emailIntroodus) && this.parola.equals(parolaIntrodusa);
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getParola() { return parola; }

    public void setNume(String nume) { this.nume = nume; }
    public void setEmail(String email) { this.email = email; }
    public void setParola(String parola) { this.parola = parola; }

    @Override
    public String toString() {
        return "Utilizator{id=" + id + ", nume='" + nume + "', email='" + email + "'}";
    }
}
