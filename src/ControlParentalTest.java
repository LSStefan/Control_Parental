
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
        assertTrue(parinte.autentificare("ion@mail.com", "parola123"),
                "Autentificarea cu date corecte ar trebui sa reuseasca.");
    }

    @Test
    public void testAutentificareGresita() {
        assertFalse(parinte.autentificare("ion@mail.com", "parolaGresita"),
                "Autentificarea cu parola gresita ar trebui sa esueze.");
    }

    @Test
    public void testAutentificareEmailGresit() {
        assertFalse(parinte.autentificare("altul@mail.com", "parola123"),
                "Autentificarea cu email gresit ar trebui sa esueze.");
    }

    // ===== TESTE PROFIL COPIL =====

    @Test
    public void testAdaugaProfil() {
        assertEquals(1, parinte.getListaProfiluri().size(),
                "Parintele ar trebui sa aiba 1 profil.");
    }

    @Test
    public void testStergeProfil() {
        parinte.stergeProfil(1);
        assertEquals(0, parinte.getListaProfiluri().size(),
                "Dupa stergere, lista de profiluri ar trebui sa fie goala.");
    }

    @Test
    public void testStergeProfilInexistent() {
        assertThrows(IllegalArgumentException.class, () -> parinte.stergeProfil(999),
                "Stergerea unui profil inexistent ar trebui sa arunce exceptie.");
    }

    @Test
    public void testAdaugaProfilNull() {
        assertThrows(IllegalArgumentException.class, () -> parinte.adaugaProfil(null),
                "Adaugarea unui profil null ar trebui sa arunce exceptie.");
    }

    // ===== TESTE LIMITA TIMP =====

    @Test
    public void testLimitaTimp_NuEsteDepasita() {
        assertFalse(limitaTimp.verificaDepasire(60),
                "60 minute nu depasesc limita de 120.");
    }

    @Test
    public void testLimitaTimp_EsteDepasita() {
        assertTrue(limitaTimp.verificaDepasire(120),
                "120 minute ating limita de 120.");
    }

    @Test
    public void testLimitaTimp_DepasireClara() {
        assertTrue(limitaTimp.verificaDepasire(150),
                "150 minute depasesc limita de 120.");
    }

    @Test
    public void testActualizeazaLimita() {
        limitaTimp.actualizeazaLimita(60);
        assertEquals(60, limitaTimp.getMinuteZilnice(),
                "Limita ar trebui sa fie actualizata la 60.");
    }

    @Test
    public void testActualizeazaLimitaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> limitaTimp.actualizeazaLimita(-10),
                "Limita negativa ar trebui sa arunce exceptie.");
    }

    @Test
    public void testLimitaTimp_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> new LimitaTimp(0, "08:00", "20:00"),
                "LimitaTimp cu 0 minute ar trebui sa arunce exceptie.");
    }

    // ===== TESTE UTILIZARE / BLOCARE AUTOMATA =====

    @Test
    public void testInregistreazaUtilizare_FaraDepasire() {
        profil.inregistreazaUtilizare(60);
        assertEquals(60, profil.getMinuteUtilizateAzi(),
                "Minutele utilizate ar trebui sa fie 60.");
        assertTrue(dispozitiv.isEsteActiv(),
                "Dispozitivul nu ar trebui sa fie blocat.");
    }

    @Test
    public void testInregistreazaUtilizare_CuDepasire() {
        profil.inregistreazaUtilizare(130);
        assertFalse(dispozitiv.isEsteActiv(),
                "Dispozitivul ar trebui sa fie blocat dupa depasirea limitei.");
    }

    @Test
    public void testInregistreazaUtilizare_Negativa() {
        assertThrows(IllegalArgumentException.class, () -> profil.inregistreazaUtilizare(-5),
                "Utilizarea negativa ar trebui sa arunce exceptie.");
    }

    @Test
    public void testResetMinuteZilnice() {
        profil.inregistreazaUtilizare(50);
        profil.resetMinuteZilnice();
        assertEquals(0, profil.getMinuteUtilizateAzi(),
                "Dupa reset, minutele utilizate ar trebui sa fie 0.");
    }

    // ===== TESTE DISPOZITIV =====

    @Test
    public void testBlocheazaDispozitiv() {
        dispozitiv.blocheazaDispozitiv();
        assertFalse(dispozitiv.isEsteActiv(),
                "Dispozitivul ar trebui sa fie blocat.");
    }

    @Test
    public void testDeblocheazaDispozitiv() {
        dispozitiv.blocheazaDispozitiv();
        dispozitiv.deblocheazaDispozitiv();
        assertTrue(dispozitiv.isEsteActiv(),
                "Dispozitivul ar trebui sa fie deblocat.");
    }

    @Test
    public void testAdaugaDispozitivNull() {
        assertThrows(IllegalArgumentException.class, () -> profil.adaugaDispozitiv(null),
                "Adaugarea unui dispozitiv null ar trebui sa arunce exceptie.");
    }

    @Test
    public void testStergeDispozitivInexistent() {
        assertThrows(IllegalArgumentException.class, () -> profil.stergeDispozitiv(999),
                "Stergerea unui dispozitiv inexistent ar trebui sa arunce exceptie.");
    }

    // ===== TESTE APLICATIE =====

    @Test
    public void testAplicatieInitialPermisa() {
        assertTrue(aplicatie.estePermisa(),
                "Aplicatia ar trebui sa fie permisa implicit.");
    }

    @Test
    public void testBlocheazaAplicatie() {
        aplicatie.blocheaza();
        assertFalse(aplicatie.estePermisa(),
                "Aplicatia ar trebui sa fie blocata.");
    }

    @Test
    public void testDeblocheazaAplicatie() {
        aplicatie.blocheaza();
        aplicatie.deblocheaza();
        assertTrue(aplicatie.estePermisa(),
                "Aplicatia ar trebui sa fie deblocata.");
    }

    // ===== TESTE COPIL =====

    @Test
    public void testVarstaInvalida_Negativa() {
        assertThrows(IllegalArgumentException.class,
                () -> new Copil(3, "Test", "test@mail.com", "pass", -1),
                "Varsta negativa ar trebui sa arunce exceptie.");
    }

    @Test
    public void testVarstaInvalida_PreaMare() {
        assertThrows(IllegalArgumentException.class,
                () -> new Copil(3, "Test", "test@mail.com", "pass", 18),
                "Varsta de 18 ani ar trebui sa arunce exceptie.");
    }

    @Test
    public void testVizualizeazaTimpDisponibil() {
        profil.inregistreazaUtilizare(30);
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        assertEquals(90, disponibil,
                "Timpul disponibil ar trebui sa fie 90 minute (120 - 30).");
    }

    @Test
    public void testTimpDisponibil_DupaDepasire() {
        profil.inregistreazaUtilizare(130);
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        assertEquals(0, disponibil,
                "Dupa depasire, timpul disponibil ar trebui sa fie 0.");
    }

    // ===== TESTE RAPORT =====

    @Test
    public void testGenereazaRaport() {
        RaportActivitate raport = parinte.vizualizeazaRaport(1);
        assertNotNull(raport, "Raportul nu ar trebui sa fie null.");
    }

    @Test
    public void testRaportProfilInexistent() {
        assertThrows(IllegalArgumentException.class, () -> parinte.vizualizeazaRaport(999),
                "Vizualizarea raportului pentru profil inexistent ar trebui sa arunce exceptie.");
    }

    @Test
    public void testRaportAdaugaActivitate() {
        RaportActivitate raport = new RaportActivitate(1, "2026-05-10");
        raport.adaugaAplicatieAccesata("YouTube", 30);
        raport.adaugaAplicatieAccesata("TikTok", 20);
        assertEquals(50, raport.getMinuteUtilizate(),
                "Totalul minutelor ar trebui sa fie 50.");
        assertEquals(2, raport.getListaAplicatiiAccesate().size(),
                "Ar trebui sa fie 2 aplicatii accesate.");
    }
}
