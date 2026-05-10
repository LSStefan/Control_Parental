package models;

import java.util.ArrayList;
import java.util.List;

public class ProfilCopil {

    private int idProfil;
    private Copil copil;
    private List<Dispozitiv> listaDispozitive;
    private LimitaTimp limitaTimp;
    private int minuteUtilizateAzi;

    public ProfilCopil(int idProfil, Copil copil, LimitaTimp limitaTimp) {
        if (copil == null) {
            throw new IllegalArgumentException("Copilul nu poate fi null.");
        }
        if (limitaTimp == null) {
            throw new IllegalArgumentException("Limita de timp nu poate fi null.");
        }
        this.idProfil = idProfil;
        this.copil = copil;
        this.limitaTimp = limitaTimp;
        this.listaDispozitive = new ArrayList<>();
        this.minuteUtilizateAzi = 0;
    }

    public void adaugaDispozitiv(Dispozitiv dispozitiv) {
        if (dispozitiv == null) {
            throw new IllegalArgumentException("Dispozitivul nu poate fi null.");
        }
        listaDispozitive.add(dispozitiv);
        System.out.println("Dispozitiv '" + dispozitiv.getNumeDispozitiv() +
                "' adaugat la profilul lui " + copil.getNume());
    }

    public void stergeDispozitiv(int idDispozitiv) {
        boolean gasit = listaDispozitive.removeIf(d -> d.getIdDispozitiv() == idDispozitiv);
        if (!gasit) {
            throw new IllegalArgumentException("Dispozitivul cu id=" + idDispozitiv + " nu a fost gasit.");
        }
        System.out.println("Dispozitivul cu id=" + idDispozitiv + " a fost sters.");
    }

    public void inregistreazaUtilizare(int minute) {
        if (minute < 0) {
            throw new IllegalArgumentException("Minutele de utilizare nu pot fi negative.");
        }
        this.minuteUtilizateAzi += minute;
        if (limitaTimp.verificaDepasire(minuteUtilizateAzi)) {
            System.out.println("ATENTIE: Limita zilnica depasita pentru " + copil.getNume() + "!");
            blocheazaToateDispozitivele();
        }
    }

    public void blocheazaToateDispozitivele() {
        for (Dispozitiv d : listaDispozitive) {
            d.blocheazaDispozitiv();
        }
    }

    public void deblocheazaToateDispozitivele() {
        for (Dispozitiv d : listaDispozitive) {
            d.deblocheazaDispozitiv();
        }
    }

    public RaportActivitate genereazaRaport() {
        RaportActivitate raport = new RaportActivitate(idProfil, java.time.LocalDate.now().toString());
        for (Dispozitiv d : listaDispozitive) {
            for (Aplicatie a : d.getListaAplicatii()) {
                if (!a.estePermisa()) {
                    raport.adaugaAplicatieAccesata(a.getNumeAplicatie() + " [BLOCATA]", 0);
                } else {
                    raport.adaugaAplicatieAccesata(a.getNumeAplicatie(), 0);
                }
            }
        }
        return raport;
    }

    public Dispozitiv getDispozitiv(int idDispozitiv) {
        for (Dispozitiv d : listaDispozitive) {
            if (d.getIdDispozitiv() == idDispozitiv) return d;
        }
        return null;
    }

    public void resetMinuteZilnice() {
        this.minuteUtilizateAzi = 0;
    }

    public int getIdProfil() { return idProfil; }
    public Copil getCopil() { return copil; }
    public LimitaTimp getLimitaTimp() { return limitaTimp; }
    public List<Dispozitiv> getListaDispozitive() { return listaDispozitive; }
    public int getMinuteUtilizateAzi() { return minuteUtilizateAzi; }

    @Override
    public String toString() {
        return "ProfilCopil{id=" + idProfil + ", copil='" + copil.getNume() +
               "', dispozitive=" + listaDispozitive.size() +
               ", minuteAzi=" + minuteUtilizateAzi + "}";
    }
}
