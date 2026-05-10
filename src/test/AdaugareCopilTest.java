package test;
import models.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AdaugareCopilTest {

    private Parinte parinte;
    private Copil copil;
    private LimitaTimp limitaTimp;
    private ProfilCopil profil;

    @BeforeEach
    public void setUp() {
        parinte    = new Parinte(1, "Ion Popescu", "ion@mail.com", "parola123");
        copil      = new Copil(2, "Maria Popescu", "maria@mail.com", "copil123", 10);
        limitaTimp = new LimitaTimp(120, "08:00", "20:00");
        profil     = new ProfilCopil(1, copil, limitaTimp);
        parinte.adaugaProfil(profil);
    }

    // 1. Autentificare parinte

    @Test
    public void testAutentificareCorecta() {
        System.out.println("Test: autentificare cu date corecte");
        boolean rezultat = parinte.autentificare("ion@mail.com", "parola123");
        System.out.println("  Rezultat: " + rezultat);
        assertTrue(rezultat);
    }

    @Test
    public void testAutentificareGresita() {
        System.out.println("Test: autentificare cu parola gresita");
        boolean rezultat = parinte.autentificare("ion@mail.com", "parolaGresita");
        System.out.println("  Rezultat: " + rezultat);
        assertFalse(rezultat);
    }

    // 2. Validarea varstei copilului

    @ParameterizedTest
    @CsvSource({"0", "8", "10", "17"})
    public void testCreareCopilVarstaValida(int varsta) {
        System.out.println("Test: creare copil cu varsta " + varsta);
        Copil c = new Copil(3, "Test", "test@mail.com", "pass", varsta);
        System.out.println("  Copil creat: " + c);
        Assertions.assertEquals(varsta, c.getVarsta());
    }

    @Test
    public void testCreareCopilVarstaNegativa() {
        System.out.println("Test: creare copil cu varsta -1 (invalida)");
        assertThrows(IllegalArgumentException.class,
            () -> new Copil(3, "Test", "test@mail.com", "pass", -1));
        System.out.println("  Exceptie aruncata corect");
    }

    @Test
    public void testCreareCopilVarsta18() {
        System.out.println("Test: creare copil cu varsta 18 (invalida)");
        assertThrows(IllegalArgumentException.class,
            () -> new Copil(3, "Test", "test@mail.com", "pass", 18));
        System.out.println("  Exceptie aruncata corect");
    }

    // 3. Validarea limitei de timp

    @Test
    public void testLimitaTimpInvalida() {
        System.out.println("Test: LimitaTimp cu 0 minute");
        assertThrows(IllegalArgumentException.class,
            () -> new LimitaTimp(0, "08:00", "20:00"));
        System.out.println("  Exceptie aruncata corect");
    }

    @Test
    public void testLimitaTimpNegativa() {
        System.out.println("Test: LimitaTimp cu -30 minute");
        assertThrows(IllegalArgumentException.class,
            () -> new LimitaTimp(-30, "08:00", "20:00"));
        System.out.println("  Exceptie aruncata corect");
    }

    // 4. Adaugarea profilului la parinte

    @Test
    public void testAdaugaProfilCuSucces() {
        System.out.println("Test: adaugare profil copil");
        System.out.println("  Profiluri in lista: " + parinte.getListaProfiluri().size());
        System.out.println("  Copil asociat: " + parinte.getProfil(1).getCopil().getNume());
        Assertions.assertEquals(1, parinte.getListaProfiluri().size());
        Assertions.assertEquals("Maria Popescu", parinte.getProfil(1).getCopil().getNume());
    }

    @Test
    public void testAdaugaProfilNull() {
        System.out.println("Test: adaugare profil null");
        assertThrows(IllegalArgumentException.class,
            () -> parinte.adaugaProfil(null));
        System.out.println("  Exceptie aruncata corect");
    }

    @Test
    public void testStergeProfilExistent() {
        System.out.println("Test: stergere profil cu id=1");
        parinte.stergeProfil(1);
        System.out.println("  Profiluri ramase: " + parinte.getListaProfiluri().size());
        Assertions.assertEquals(0, parinte.getListaProfiluri().size());
    }

    @Test
    public void testStergeProfilInexistent() {
        System.out.println("Test: stergere profil cu id=999 (inexistent)");
        assertThrows(IllegalArgumentException.class,
            () -> parinte.stergeProfil(999));
        System.out.println("  Exceptie aruncata corect");
    }

    // 5. Scenariul complet - parametrizat

    @ParameterizedTest
    @CsvSource({
        "Maria,  10, 120",
        "Andrei,  8,  60",
        "Sofia,  15, 240",
        "Alex,    0,  30",
        "Bianca, 17, 480"
    })
    public void testScenariuCompletAdaugareCopil(String nume, int varsta, int minute) {
        System.out.println("Test scenariu complet: " + nume.trim() + ", varsta=" + varsta + ", minute=" + minute);

        Assertions.assertTrue(parinte.autentificare("ion@mail.com", "parola123"));

        Copil c = new Copil(5, nume.trim(), nume.trim() + "@mail.com", "pass", varsta);
        LimitaTimp limita = new LimitaTimp(minute, "08:00", "20:00");
        ProfilCopil p = new ProfilCopil(99, c, limita);
        parinte.adaugaProfil(p);

        ProfilCopil gasit = parinte.getProfil(99);
        System.out.println("  Profil gasit: " + gasit);

        assertNotNull(gasit);
        Assertions.assertEquals(nume.trim(), gasit.getCopil().getNume());
        Assertions.assertEquals(minute, gasit.getLimitaTimp().getMinuteZilnice());
        Assertions.assertEquals(0, gasit.getMinuteUtilizateAzi());
    }

    // 6. Timp disponibil

    @Test
    public void testTimpDisponibilNormal() {
        System.out.println("Test: timp disponibil dupa 60 minute utilizate din 120");
        profil.inregistreazaUtilizare(60);
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        System.out.println("  Timp disponibil: " + disponibil + " minute");
        assertEquals(60, disponibil);
    }

    @Test
    public void testTimpDisponibilDupaDepasire() {
        System.out.println("Test: timp disponibil dupa depasirea limitei (130 din 120)");
        profil.inregistreazaUtilizare(130);
        int disponibil = copil.vizualizeazaTimpDisponibil(profil);
        System.out.println("  Timp disponibil: " + disponibil + " minute");
        assertEquals(0, disponibil);
    }

    @Test
    public void testInregistrareUtilizareNegativa() {
        System.out.println("Test: inregistrare utilizare cu valoare negativa (-10)");
        assertThrows(IllegalArgumentException.class,
            () -> profil.inregistreazaUtilizare(-10));
        System.out.println("  Exceptie aruncata corect");
    }
}
