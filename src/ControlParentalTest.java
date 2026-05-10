import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControlParentalTest {

    private Parinte parinte;
    private Copil copil;
    private LimitaTimp limitaTimp;
    private ProfilCopil profil;
    private Dispozitiv dispozitiv;
    private Aplicatie aplicatie;

    @BeforeEach
    public void setUp() {
        parinte = new Parinte(1, "Ion Popescu", "ion@mail.com", "parola123");
        copil = new Copil(2, "Maria Popescu", "maria@mail.com", "copil123", 10);
        limitaTimp = new LimitaTimp(120, "08:00", "20:00");
        profil = new ProfilCopil(1, copil, limitaTimp);
        dispozitiv = new Dispozitiv(1, "Tableta Maria", "tableta");
        aplicatie = new Aplicatie(1, "YouTube", "video");
        dispozitiv.adaugaAplicatie(aplicatie);
        profil.adaugaDispozitiv(dispozitiv);
        parinte.adaugaProfil(profil);
    }

    // ===== TESTE AUTENTIFICARE =====

    @Test
    public void testAutentificareCorecta() {
        System.out.println("[TEST] Autentificare cu date corecte");
        System.out.println("  Email: ion@mail.com | Parola: parola123");
        boolean rezultat = parinte.autentificare("ion@mail.com", "parola123");
        System.out.println("  Rezultat autentificare: " + (rezultat ? "REUSITA" : "ESUATA"));
        assertTrue(rezultat, "Autentificarea cu date corecte ar trebui sa reuseasca.");
        System.out.println("  [PASS] Autentificarea a reusit corect.");
    }

    @Test
    public void testAutentificareGresita() {
        System.out.println("[TEST] Autentificare cu parola gresita");
        System.out.println("  Email: ion@mail.com | Parola: parolaGresita");
        boolean rezultat = parinte.autentificare("ion@mail.com", "parolaGresita");
        System.out.println("  Rezultat autentificare: " + (rezultat ? "REUSITA" : "ESUATA"));
        assertFalse(rezultat, "Autentificarea cu parola gresita ar trebui sa esueze.");
        System.out.println("  [PASS] Autentificarea a fost respinsa corect.");
    }

    @Test
    public void testAutentificareEmailGresit() {
        System.out.println("[TEST] Autentificare cu email gresit");
        System.out.println("  Email: altul@mail.com | Parola: parola123");
        boolean rezultat = parinte.autentificare("altul@mail.com", "parola123");
        System.out.println("  Rezultat autentificare: " + (rezultat ? "REUSITA" : "ESUATA"));
        assertFalse(rezultat, "Autentificarea cu email gresit ar trebui sa esueze.");
        System.out.println("  [PASS] Autentificarea a fost respinsa corect.");
    }

    // ===== TESTE PROFIL COPIL =====

    @Test
    public void testAdaugaProfil() {
        System.out.println("[TEST] Adaugare profil copil");
        System.out.println("  Profil adaugat pentru: " + copil.getNume());
        int nrProfiluri = parinte.getListaProfiluri().size();
        System.out.println("  Numar profiluri dupa adaugare: " + nrProfiluri);
        assertEquals(1, nrProfiluri, "Parintele ar trebui sa aiba 1 profil.");
        System.out.println("  [PASS] Profilul a fost adaugat corect.");
    }

    @Test
    public void testStergeProfil() {
        System.out.println("[TEST] Stergere profil copil cu id=1");
        System.out.println("  Profiluri inainte de stergere: " + parinte.getListaProfiluri().size());
        parinte.stergeProfil(1);
        int nrProfiluri = parinte.getListaProfiluri().size();
        System.out.println("  Profiluri dupa stergere: " + nrProfiluri);
        assertEquals(0, nrProfiluri, "Dupa stergere, lista de profiluri ar trebui sa fie goala.");
        System.out.println("  [PASS] Profilul a fost sters corect.");
    }

    @Test
    public void testStergeProfilInexistent() {
        System.out.println("[TEST] Stergere profil inexistent cu id=999");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca stergerea profilului cu id=999...");
            parinte.stergeProfil(999);
        }, "Stergerea unui profil inexistent ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru profil inexistent.");
    }

    @Test
    public void testAdaugaProfilNull() {
        System.out.println("[TEST] Adaugare profil null");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca adaugarea unui profil null...");
            parinte.adaugaProfil(null);
        }, "Adaugarea unui profil null ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru profil null.");
    }

    // ===== TESTE LIMITA TIMP =====

    @Test
    public void testLimitaTimp_NuEsteDepasita() {
        System.out.println("[TEST] Verificare limita timp - fara depasire");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        System.out.println("  Minute utilizate: 60");
        boolean depasita = limitaTimp.verificaDepasire(60);
        System.out.println("  Limita depasita: " + depasita);
        assertFalse(depasita, "60 minute nu depasesc limita de 120.");
        System.out.println("  [PASS] Limita nu este depasita corect.");
    }

    @Test
    public void testLimitaTimp_EsteDepasita() {
        System.out.println("[TEST] Verificare limita timp - limita atinsa exact");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        System.out.println("  Minute utilizate: 120");
        boolean depasita = limitaTimp.verificaDepasire(120);
        System.out.println("  Limita depasita: " + depasita);
        assertTrue(depasita, "120 minute ating limita de 120.");
        System.out.println("  [PASS] Limita atinsa exact detectata corect.");
    }

    @Test
    public void testLimitaTimp_DepasireClara() {
        System.out.println("[TEST] Verificare limita timp - depasire clara");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        System.out.println("  Minute utilizate: 150");
        boolean depasita = limitaTimp.verificaDepasire(150);
        System.out.println("  Limita depasita: " + depasita);
        assertTrue(depasita, "150 minute depasesc limita de 120.");
        System.out.println("  [PASS] Depasirea clara detectata corect.");
    }

    @Test
    public void testActualizeazaLimita() {
        System.out.println("[TEST] Actualizare limita de timp");
        System.out.println("  Limita veche: " + limitaTimp.getMinuteZilnice() + " minute");
        limitaTimp.actualizeazaLimita(60);
        System.out.println("  Limita noua: " + limitaTimp.getMinuteZilnice() + " minute");
        assertEquals(60, limitaTimp.getMinuteZilnice(), "Limita ar trebui sa fie actualizata la 60.");
        System.out.println("  [PASS] Limita actualizata corect la 60 minute.");
    }

    @Test
    public void testActualizeazaLimitaNegativa() {
        System.out.println("[TEST] Actualizare limita cu valoare negativa (-10)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca setarea limitei la -10 minute...");
            limitaTimp.actualizeazaLimita(-10);
        }, "Limita negativa ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru limita negativa.");
    }

    @Test
    public void testLimitaTimp_Invalid() {
        System.out.println("[TEST] Creare LimitaTimp cu 0 minute (invalid)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca crearea LimitaTimp cu 0 minute...");
            new LimitaTimp(0, "08:00", "20:00");
        }, "LimitaTimp cu 0 minute ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru 0 minute.");
    }

    // ===== TESTE UTILIZARE / BLOCARE AUTOMATA =====

    @Test
    public void testInregistreazaUtilizare_FaraDepasire() {
        System.out.println("[TEST] Inregistrare utilizare fara depasirea limitei");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        System.out.println("  Se inregistreaza 60 minute de utilizare...");
        profil.inregistreazaUtilizare(60);
        System.out.println("  Minute utilizate azi: " + profil.getMinuteUtilizateAzi());
        System.out.println("  Status dispozitiv: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        assertEquals(60, profil.getMinuteUtilizateAzi(), "Minutele utilizate ar trebui sa fie 60.");
        assertTrue(dispozitiv.isEsteActiv(), "Dispozitivul nu ar trebui sa fie blocat.");
        System.out.println("  [PASS] Utilizare inregistrata, dispozitiv ramas activ.");
    }

    @Test
    public void testInregistreazaUtilizare_CuDepasire() {
        System.out.println("[TEST] Inregistrare utilizare cu depasirea limitei");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        System.out.println("  Se inregistreaza 130 minute de utilizare...");
        profil.inregistreazaUtilizare(130);
        System.out.println("  Minute utilizate azi: " + profil.getMinuteUtilizateAzi());
        System.out.println("  Status dispozitiv: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        assertFalse(dispozitiv.isEsteActiv(), "Dispozitivul ar trebui sa fie blocat dupa depasirea limitei.");
        System.out.println("  [PASS] Dispozitivul a fost blocat automat dupa depasire.");
    }

    @Test
    public void testInregistreazaUtilizare_Negativa() {
        System.out.println("[TEST] Inregistrare utilizare negativa (-5 minute)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca inregistrarea a -5 minute...");
            profil.inregistreazaUtilizare(-5);
        }, "Utilizarea negativa ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru minute negative.");
    }

    @Test
    public void testResetMinuteZilnice() {
        System.out.println("[TEST] Reset minute zilnice");
        profil.inregistreazaUtilizare(50);
        System.out.println("  Minute utilizate inainte de reset: " + profil.getMinuteUtilizateAzi());
        profil.resetMinuteZilnice();
        System.out.println("  Minute utilizate dupa reset: " + profil.getMinuteUtilizateAzi());
        assertEquals(0, profil.getMinuteUtilizateAzi(), "Dupa reset, minutele utilizate ar trebui sa fie 0.");
        System.out.println("  [PASS] Resetul minutelor zilnice a functionat corect.");
    }

    // ===== TESTE DISPOZITIV =====

    @Test
    public void testBlocheazaDispozitiv() {
        System.out.println("[TEST] Blocare manuala dispozitiv");
        System.out.println("  Dispozitiv: " + dispozitiv.getNumeDispozitiv());
        System.out.println("  Status initial: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        dispozitiv.blocheazaDispozitiv();
        System.out.println("  Status dupa blocare: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        assertFalse(dispozitiv.isEsteActiv(), "Dispozitivul ar trebui sa fie blocat.");
        System.out.println("  [PASS] Dispozitivul a fost blocat corect.");
    }

    @Test
    public void testDeblocheazaDispozitiv() {
        System.out.println("[TEST] Deblocare dispozitiv dupa blocare");
        System.out.println("  Dispozitiv: " + dispozitiv.getNumeDispozitiv());
        dispozitiv.blocheazaDispozitiv();
        System.out.println("  Status dupa blocare: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        dispozitiv.deblocheazaDispozitiv();
        System.out.println("  Status dupa deblocare: " + (dispozitiv.isEsteActiv() ? "ACTIV" : "BLOCAT"));
        assertTrue(dispozitiv.isEsteActiv(), "Dispozitivul ar trebui sa fie deblocat.");
        System.out.println("  [PASS] Dispozitivul a fost deblocat corect.");
    }

    @Test
    public void testAdaugaDispozitivNull() {
        System.out.println("[TEST] Adaugare dispozitiv null in profil");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca adaugarea unui dispozitiv null...");
            profil.adaugaDispozitiv(null);
        }, "Adaugarea unui dispozitiv null ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru dispozitiv null.");
    }

    @Test
    public void testStergeDispozitivInexistent() {
        System.out.println("[TEST] Stergere dispozitiv inexistent cu id=999");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca stergerea dispozitivului cu id=999...");
            profil.stergeDispozitiv(999);
        }, "Stergerea unui dispozitiv inexistent ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru dispozitiv inexistent.");
    }

    // ===== TESTE APLICATIE =====

    @Test
    public void testAplicatieInitialPermisa() {
        System.out.println("[TEST] Verificare status initial aplicatie");
        System.out.println("  Aplicatie: " + aplicatie.getNumeAplicatie() + " (" + aplicatie.getCategorie() + ")");
        System.out.println("  Status initial: " + (aplicatie.estePermisa() ? "PERMISA" : "BLOCATA"));
        assertTrue(aplicatie.estePermisa(), "Aplicatia ar trebui sa fie permisa implicit.");
        System.out.println("  [PASS] Aplicatia este permisa implicit, corect.");
    }

    @Test
    public void testBlocheazaAplicatie() {
        System.out.println("[TEST] Blocare aplicatie");
        System.out.println("  Aplicatie: " + aplicatie.getNumeAplicatie());
        System.out.println("  Status inainte: " + (aplicatie.estePermisa() ? "PERMISA" : "BLOCATA"));
        aplicatie.blocheaza();
        System.out.println("  Status dupa blocare: " + (aplicatie.estePermisa() ? "PERMISA" : "BLOCATA"));
        assertFalse(aplicatie.estePermisa(), "Aplicatia ar trebui sa fie blocata.");
        System.out.println("  [PASS] Aplicatia a fost blocata corect.");
    }

    @Test
    public void testDeblocheazaAplicatie() {
        System.out.println("[TEST] Deblocare aplicatie dupa blocare");
        System.out.println("  Aplicatie: " + aplicatie.getNumeAplicatie());
        aplicatie.blocheaza();
        System.out.println("  Status dupa blocare: " + (aplicatie.estePermisa() ? "PERMISA" : "BLOCATA"));
        aplicatie.deblocheaza();
        System.out.println("  Status dupa deblocare: " + (aplicatie.estePermisa() ? "PERMISA" : "BLOCATA"));
        assertTrue(aplicatie.estePermisa(), "Aplicatia ar trebui sa fie deblocata.");
        System.out.println("  [PASS] Aplicatia a fost deblocata corect.");
    }

    // ===== TESTE COPIL =====

    @Test
    public void testVarstaInvalida_Negativa() {
        System.out.println("[TEST] Creare copil cu varsta negativa (-1)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca crearea unui copil cu varsta=-1...");
            new Copil(3, "Test", "test@mail.com", "pass", -1);
        }, "Varsta negativa ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru varsta negativa.");
    }

    @Test
    public void testVarstaInvalida_PreaMare() {
        System.out.println("[TEST] Creare copil cu varsta prea mare (18)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca crearea unui copil cu varsta=18...");
            new Copil(3, "Test", "test@mail.com", "pass", 18);
        }, "Varsta de 18 ani ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru varsta>=18.");
    }

    @Test
    public void testVizualizeazaTimpDisponibil() {
        System.out.println("[TEST] Vizualizare timp disponibil pentru copil");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        profil.inregistreazaUtilizare(30);
        System.out.println("  Minute utilizate: " + profil.getMinuteUtilizateAzi());
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        System.out.println("  Timp disponibil calculat: " + disponibil + " minute");
        assertEquals(90, disponibil, "Timpul disponibil ar trebui sa fie 90 minute (120 - 30).");
        System.out.println("  [PASS] Timpul disponibil calculat corect: 120 - 30 = 90 minute.");
    }

    @Test
    public void testTimpDisponibil_DupaDepasire() {
        System.out.println("[TEST] Timp disponibil dupa depasirea limitei");
        System.out.println("  Limita zilnica: " + limitaTimp.getMinuteZilnice() + " minute");
        profil.inregistreazaUtilizare(130);
        System.out.println("  Minute utilizate: " + profil.getMinuteUtilizateAzi());
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        System.out.println("  Timp disponibil calculat: " + disponibil + " minute");
        assertEquals(0, disponibil, "Dupa depasire, timpul disponibil ar trebui sa fie 0.");
        System.out.println("  [PASS] Timpul disponibil este 0 dupa depasire, corect.");
    }

    // ===== TESTE RAPORT =====

    @Test
    public void testGenereazaRaport() {
        System.out.println("[TEST] Generare raport activitate pentru profil id=1");
        RaportActivitate raport = parinte.vizualizeazaRaport(1);
        System.out.println("  Raport generat pentru: " + profil.getCopil().getNume());
        System.out.println("  Data raport: " + raport.getDataGenerare());
        assertNotNull(raport, "Raportul nu ar trebui sa fie null.");
        System.out.println("  [PASS] Raportul a fost generat cu succes.");
    }

    @Test
    public void testRaportProfilInexistent() {
        System.out.println("[TEST] Generare raport pentru profil inexistent (id=999)");
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  Se incearca generarea raportului pentru id=999...");
            parinte.vizualizeazaRaport(999);
        }, "Vizualizarea raportului pentru profil inexistent ar trebui sa arunce exceptie.");
        System.out.println("  [PASS] Exceptie aruncata corect pentru profil inexistent.");
    }

    @Test
    public void testRaportAdaugaActivitate() {
        System.out.println("[TEST] Adaugare activitati in raport si verificare total");
        RaportActivitate raport = new RaportActivitate(1, "2026-05-10");
        System.out.println("  Se adauga: YouTube - 30 minute");
        raport.adaugaAplicatieAccesata("YouTube", 30);
        System.out.println("  Se adauga: TikTok - 20 minute");
        raport.adaugaAplicatieAccesata("TikTok", 20);
        System.out.println("  Total minute calculate: " + raport.getMinuteUtilizate());
        System.out.println("  Numar aplicatii accesate: " + raport.getListaAplicatiiAccesate().size());
        assertEquals(50, raport.getMinuteUtilizate(), "Totalul minutelor ar trebui sa fie 50.");
        assertEquals(2, raport.getListaAplicatiiAccesate().size(), "Ar trebui sa fie 2 aplicatii accesate.");
        System.out.println("  [PASS] Raportul contine 50 minute si 2 aplicatii, corect.");
    }
}
