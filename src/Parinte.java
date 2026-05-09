import java.util.ArrayList;
import java.util.List;

public class Parinte extends Utilizator {

    private List<ProfilCopil> listaProfiluri;

    public Parinte(int id, String nume, String email, String parola) {
        super(id, nume, email, parola);
        this.listaProfiluri = new ArrayList<>();
    }

    public void adaugaProfil(ProfilCopil profil) {
        if (profil == null) {
            throw new IllegalArgumentException("Profilul nu poate fi null.");
        }
        listaProfiluri.add(profil);
        System.out.println("Profil adaugat pentru copilul: " + profil.getCopil().getNume());
    }

    public void stergeProfil(int idProfil) {
        boolean gasit = listaProfiluri.removeIf(p -> p.getIdProfil() == idProfil);
        if (!gasit) {
            throw new IllegalArgumentException("Profilul cu id=" + idProfil + " nu a fost gasit.");
        }
        System.out.println("Profilul cu id=" + idProfil + " a fost sters.");
    }

    public RaportActivitate vizualizeazaRaport(int idProfil) {
        for (ProfilCopil profil : listaProfiluri) {
            if (profil.getIdProfil() == idProfil) {
                return profil.genereazaRaport();
            }
        }
        throw new IllegalArgumentException("Nu exista profil cu id=" + idProfil);
    }

    public ProfilCopil getProfil(int idProfil) {
        for (ProfilCopil profil : listaProfiluri) {
            if (profil.getIdProfil() == idProfil) {
                return profil;
            }
        }
        return null;
    }

    public List<ProfilCopil> getListaProfiluri() { return listaProfiluri; }

    @Override
    public String toString() {
        return "Parinte{id=" + getId() + ", nume='" + getNume() + "', profiluri=" + listaProfiluri.size() + "}";
    }
}
