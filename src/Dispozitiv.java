import java.util.ArrayList;
import java.util.List;

public class Dispozitiv {

    private int idDispozitiv;
    private String numeDispozitiv;
    private String tip;
    private List<Aplicatie> listaAplicatii;
    private boolean esteActiv;

    public Dispozitiv(int idDispozitiv, String numeDispozitiv, String tip) {
        this.idDispozitiv = idDispozitiv;
        this.numeDispozitiv = numeDispozitiv;
        this.tip = tip;
        this.listaAplicatii = new ArrayList<>();
        this.esteActiv = true;
    }

    public void blocheazaDispozitiv() {
        this.esteActiv = false;
        System.out.println("Dispozitivul '" + numeDispozitiv + "' a fost blocat.");
    }

    public void deblocheazaDispozitiv() {
        this.esteActiv = true;
        System.out.println("Dispozitivul '" + numeDispozitiv + "' a fost deblocat.");
    }

    public void sincronizeazaRestrictii() {
        System.out.println("Restrictiile au fost sincronizate pe dispozitivul '" + numeDispozitiv + "'.");
        for (Aplicatie app : listaAplicatii) {
            System.out.println("  - " + app.getNumeAplicatie() + ": " +
                    (app.estePermisa() ? "permisa" : "blocata"));
        }
    }

    public void adaugaAplicatie(Aplicatie aplicatie) {
        if (aplicatie == null) {
            throw new IllegalArgumentException("Aplicatia nu poate fi null.");
        }
        listaAplicatii.add(aplicatie);
        System.out.println("Aplicatia '" + aplicatie.getNumeAplicatie() + "' adaugata pe '" + numeDispozitiv + "'.");
    }

    public void stergeAplicatie(int idAplicatie) {
        boolean gasita = listaAplicatii.removeIf(a -> a.getIdAplicatie() == idAplicatie);
        if (!gasita) {
            throw new IllegalArgumentException("Aplicatia cu id=" + idAplicatie + " nu a fost gasita.");
        }
    }

    public Aplicatie getAplicatie(int idAplicatie) {
        for (Aplicatie a : listaAplicatii) {
            if (a.getIdAplicatie() == idAplicatie) return a;
        }
        return null;
    }

    public int getIdDispozitiv() { return idDispozitiv; }
    public String getNumeDispozitiv() { return numeDispozitiv; }
    public String getTip() { return tip; }
    public List<Aplicatie> getListaAplicatii() { return listaAplicatii; }
    public boolean isEsteActiv() { return esteActiv; }

    @Override
    public String toString() {
        return "Dispozitiv{id=" + idDispozitiv + ", nume='" + numeDispozitiv +
               "', tip='" + tip + "', activ=" + esteActiv +
               ", aplicatii=" + listaAplicatii.size() + "}";
    }
}
