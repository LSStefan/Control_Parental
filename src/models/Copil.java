package models;

public class Copil extends Utilizator {

    private int varsta;

    public Copil(int id, String nume, String email, String parola, int varsta) {
        super(id, nume, email, parola);
        if (varsta < 0 || varsta > 17) {
            throw new IllegalArgumentException("Varsta copilului trebuie sa fie intre 0 si 17 ani.");
        }
        this.varsta = varsta;
    }

    public void solicitaExtindereTimp(ProfilCopil profil, int minuteSuplimentare) {
        if (minuteSuplimentare <= 0) {
            throw new IllegalArgumentException("Minutele suplimentare trebuie sa fie pozitive.");
        }
        System.out.println("Copilul " + getNume() + " solicita " + minuteSuplimentare + " minute suplimentare.");
        // Parintele va trebui sa aprobe solicitarea
    }

    public int vizualizeazaTimpDisponibil(ProfilCopil profil) {
        LimitaTimp limita = profil.getLimitaTimp();
        int minuteUtilizate = profil.getMinuteUtilizateAzi();
        int disponibil = limita.getMinuteZilnice() - minuteUtilizate;
        return Math.max(disponibil, 0);
    }

    public int getVarsta() { return varsta; }
    public void setVarsta(int varsta) {
        if (varsta < 0 || varsta > 17) {
            throw new IllegalArgumentException("Varsta copilului trebuie sa fie intre 0 si 17 ani.");
        }
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Copil{id=" + getId() + ", nume='" + getNume() + "', varsta=" + varsta + "}";
    }
}
