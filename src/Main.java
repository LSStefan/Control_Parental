import java.util.*;
import java.io.*;

public class Main {

    // ─── Date in memorie ───────────────────────────────────────────────────────
    static List<Parinte>      parinti    = new ArrayList<>();
    static List<Copil>        copii      = new ArrayList<>();
    static List<ProfilCopil>  profiluri  = new ArrayList<>();
    static List<Dispozitiv>   dispozitive = new ArrayList<>();

    static final String CSV_PARINTI     = "parinti.csv";
    static final String CSV_COPII       = "copii.csv";
    static final String CSV_PROFILURI   = "profiluri.csv";
    static final String CSV_DISPOZITIVE = "dispozitive.csv";
    static final String CSV_APLICATII   = "aplicatii.csv";

    static Scanner scanner = new Scanner(System.in);
    static int nextId = 100;

    // ═══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        incarcaDate();
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   SISTEM CONTROL PARENTAL  v1.0      ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            afiseazaMeniuPrincipal();
            String optiune = scanner.nextLine().trim();
            switch (optiune) {
                case "1": gestioneazaParinti(); break;
                case "2": gestioneazaCopii(); break;
                case "3": gestioneazaProfiluri(); break;
                case "4": gestioneazaDispozitive(); break;
                case "5": gestioneazaAplicatii(); break;
                case "6": inregistreazaUtilizare(); break;
                case "7": veziRapoarte(); break;
                case "8": salveazaDate(); System.out.println("✔  Date salvate cu succes."); break;
                case "0":
                    salveazaDate();
                    System.out.println("✔  Date salvate. La revedere!");
                    running = false;
                    break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static void afiseazaMeniuPrincipal() {
        System.out.println("\n══════════ MENIU PRINCIPAL ══════════");
        System.out.println("  1. Gestionare Parinti");
        System.out.println("  2. Gestionare Copii");
        System.out.println("  3. Gestionare Profiluri Copii");
        System.out.println("  4. Gestionare Dispozitive");
        System.out.println("  5. Gestionare Aplicatii");
        System.out.println("  6. Inregistreaza Utilizare");
        System.out.println("  7. Rapoarte Activitate");
        System.out.println("  8. Salveaza Date");
        System.out.println("  0. Iesire");
        System.out.print("Alegere: ");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 1. PARINTI
    // ═══════════════════════════════════════════════════════════════════════════
    static void gestioneazaParinti() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── PARINTI ───────────────────────────");
            System.out.println("  1. Adauga parinte");
            System.out.println("  2. Listare parinti");
            System.out.println("  3. Sterge parinte");
            System.out.println("  0. Inapoi");
            System.out.print("Alegere: ");
            switch (scanner.nextLine().trim()) {
                case "1": adaugaParinte(); break;
                case "2": listeazaParinti(); break;
                case "3": stergeParinte(); break;
                case "0": back = true; break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static void adaugaParinte() {
        System.out.print("Nume: ");   String nume   = scanner.nextLine().trim();
        System.out.print("Email: ");  String email  = scanner.nextLine().trim();
        System.out.print("Parola: "); String parola = scanner.nextLine().trim();
        int id = nextId++;
        Parinte p = new Parinte(id, nume, email, parola);
        parinti.add(p);
        System.out.println("✔  Parinte adaugat: " + p);
    }

    static void listeazaParinti() {
        if (parinti.isEmpty()) { System.out.println("  (niciun parinte inregistrat)"); return; }
        System.out.println("  ID   Nume               Email");
        System.out.println("  ─────────────────────────────────────────");
        for (Parinte p : parinti)
            System.out.printf("  %-4d %-18s %s%n", p.getId(), p.getNume(), p.getEmail());
    }

    static void stergeParinte() {
        listeazaParinti();
        System.out.print("ID parinte de sters: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = parinti.removeIf(p -> p.getId() == id);
            System.out.println(ok ? "✔  Parinte sters." : "✘  ID negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 2. COPII
    // ═══════════════════════════════════════════════════════════════════════════
    static void gestioneazaCopii() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── COPII ─────────────────────────────");
            System.out.println("  1. Adauga copil");
            System.out.println("  2. Listare copii");
            System.out.println("  3. Sterge copil");
            System.out.println("  4. Timp disponibil copil");
            System.out.println("  0. Inapoi");
            System.out.print("Alegere: ");
            switch (scanner.nextLine().trim()) {
                case "1": adaugaCopil(); break;
                case "2": listeazaCopii(); break;
                case "3": stergeCopil(); break;
                case "4": timpDisponibilCopil(); break;
                case "0": back = true; break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static void adaugaCopil() {
        System.out.print("Nume: ");   String nume   = scanner.nextLine().trim();
        System.out.print("Email: ");  String email  = scanner.nextLine().trim();
        System.out.print("Parola: "); String parola = scanner.nextLine().trim();
        System.out.print("Varsta (0-17): ");
        try {
            int varsta = Integer.parseInt(scanner.nextLine().trim());
            int id = nextId++;
            Copil c = new Copil(id, nume, email, parola, varsta);
            copii.add(c);
            System.out.println("✔  Copil adaugat: " + c);
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    static void listeazaCopii() {
        if (copii.isEmpty()) { System.out.println("  (niciun copil inregistrat)"); return; }
        System.out.println("  ID   Nume               Varsta");
        System.out.println("  ──────────────────────────────");
        for (Copil c : copii)
            System.out.printf("  %-4d %-18s %d ani%n", c.getId(), c.getNume(), c.getVarsta());
    }

    static void stergeCopil() {
        listeazaCopii();
        System.out.print("ID copil de sters: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = copii.removeIf(c -> c.getId() == id);
            System.out.println(ok ? "✔  Copil sters." : "✘  ID negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void timpDisponibilCopil() {
        listeazaCopii();
        System.out.print("ID copil: ");
        try {
            int idC = Integer.parseInt(scanner.nextLine().trim());
            Copil copil = null;
            for (Copil c : copii) if (c.getId() == idC) { copil = c; break; }
            if (copil == null) { System.out.println("✘  Copil negasit."); return; }
            ProfilCopil profil = null;
            for (ProfilCopil p : profiluri) if (p.getCopil().getId() == idC) { profil = p; break; }
            if (profil == null) { System.out.println("✘  Copilul nu are profil asociat."); return; }
            int disponibil = copil.vizualizeazaTimpDisponibil(profil);
            System.out.printf("⏱  Timp disponibil azi pentru %s: %d minute%n", copil.getNume(), disponibil);
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 3. PROFILURI
    // ═══════════════════════════════════════════════════════════════════════════
    static void gestioneazaProfiluri() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── PROFILURI COPII ───────────────────");
            System.out.println("  1. Creeaza profil");
            System.out.println("  2. Listare profiluri");
            System.out.println("  3. Sterge profil");
            System.out.println("  4. Actualizeaza limita de timp");
            System.out.println("  5. Blocheaza toate dispozitivele");
            System.out.println("  6. Deblocheaza toate dispozitivele");
            System.out.println("  7. Reset minute zilnice");
            System.out.println("  0. Inapoi");
            System.out.print("Alegere: ");
            switch (scanner.nextLine().trim()) {
                case "1": creeazaProfil(); break;
                case "2": listeazaProfiluri(); break;
                case "3": stergeProfil(); break;
                case "4": actualizeazaLimita(); break;
                case "5": blocheazaToate(); break;
                case "6": deblocheazaToate(); break;
                case "7": resetMinute(); break;
                case "0": back = true; break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static void creeazaProfil() {
        listeazaCopii();
        System.out.print("ID copil: ");
        try {
            int idC = Integer.parseInt(scanner.nextLine().trim());
            Copil copil = null;
            for (Copil c : copii) if (c.getId() == idC) { copil = c; break; }
            if (copil == null) { System.out.println("✘  Copil negasit."); return; }
            System.out.print("Minute zilnice permise: ");
            int min = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Ora inceput (ex: 08:00): "); String oraI = scanner.nextLine().trim();
            System.out.print("Ora sfarsit (ex: 20:00): "); String oraS = scanner.nextLine().trim();
            LimitaTimp limita = new LimitaTimp(min, oraI, oraS);
            int id = nextId++;
            ProfilCopil profil = new ProfilCopil(id, copil, limita);
            profiluri.add(profil);
            if (!parinti.isEmpty()) {
                listeazaParinti();
                System.out.print("ID parinte asociat (Enter pt a sari): ");
                String inp = scanner.nextLine().trim();
                if (!inp.isEmpty()) {
                    int idP = Integer.parseInt(inp);
                    for (Parinte p : parinti) {
                        if (p.getId() == idP) { p.adaugaProfil(profil); break; }
                    }
                }
            }
            System.out.println("✔  Profil creat: " + profil);
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    static void listeazaProfiluri() {
        if (profiluri.isEmpty()) { System.out.println("  (niciun profil)"); return; }
        System.out.println("  ID   Copil            Min/zi  Utilizat  Interval");
        System.out.println("  ────────────────────────────────────────────────");
        for (ProfilCopil p : profiluri)
            System.out.printf("  %-4d %-16s %-7d %-9d %s-%s%n",
                p.getIdProfil(), p.getCopil().getNume(),
                p.getLimitaTimp().getMinuteZilnice(), p.getMinuteUtilizateAzi(),
                p.getLimitaTimp().getOraInceput(), p.getLimitaTimp().getOraSfarsit());
    }

    static void stergeProfil() {
        listeazaProfiluri();
        System.out.print("ID profil de sters: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = profiluri.removeIf(p -> p.getIdProfil() == id);
            System.out.println(ok ? "✔  Profil sters." : "✘  ID negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void actualizeazaLimita() {
        listeazaProfiluri();
        System.out.print("ID profil: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            ProfilCopil profil = null;
            for (ProfilCopil p : profiluri) if (p.getIdProfil() == id) { profil = p; break; }
            if (profil == null) { System.out.println("✘  Profil negasit."); return; }
            System.out.print("Minute noi: ");
            int min = Integer.parseInt(scanner.nextLine().trim());
            profil.getLimitaTimp().actualizeazaLimita(min);
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    static void blocheazaToate() {
        listeazaProfiluri();
        System.out.print("ID profil: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (ProfilCopil p : profiluri) {
                if (p.getIdProfil() == id) { p.blocheazaToateDispozitivele(); return; }
            }
            System.out.println("✘  Profil negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void deblocheazaToate() {
        listeazaProfiluri();
        System.out.print("ID profil: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (ProfilCopil p : profiluri) {
                if (p.getIdProfil() == id) { p.deblocheazaToateDispozitivele(); return; }
            }
            System.out.println("✘  Profil negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void resetMinute() {
        listeazaProfiluri();
        System.out.print("ID profil (sau 'all' pentru toate): ");
        String inp = scanner.nextLine().trim();
        if (inp.equalsIgnoreCase("all")) {
            for (ProfilCopil p : profiluri) p.resetMinuteZilnice();
            System.out.println("✔  Reset facut pentru toate profilurile.");
        } else {
            try {
                int id = Integer.parseInt(inp);
                for (ProfilCopil p : profiluri) {
                    if (p.getIdProfil() == id) { p.resetMinuteZilnice(); System.out.println("✔  Reset facut."); return; }
                }
                System.out.println("✘  Profil negasit.");
            } catch (NumberFormatException e) { System.out.println("✘  Input invalid."); }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 4. DISPOZITIVE
    // ═══════════════════════════════════════════════════════════════════════════
    static void gestioneazaDispozitive() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── DISPOZITIVE ───────────────────────");
            System.out.println("  1. Adauga dispozitiv");
            System.out.println("  2. Listare dispozitive");
            System.out.println("  3. Asociaza dispozitiv la profil");
            System.out.println("  4. Sterge dispozitiv din profil");
            System.out.println("  5. Blocheaza dispozitiv");
            System.out.println("  6. Deblocheaza dispozitiv");
            System.out.println("  7. Sincronizeaza restrictii");
            System.out.println("  0. Inapoi");
            System.out.print("Alegere: ");
            switch (scanner.nextLine().trim()) {
                case "1": adaugaDispozitiv(); break;
                case "2": listeazaDispozitive(); break;
                case "3": asociazaDispozitiv(); break;
                case "4": stergeDispozitivDinProfil(); break;
                case "5": blocheazaDispozitiv(); break;
                case "6": deblocheazaDispozitiv(); break;
                case "7": sincronizeazaRestrictii(); break;
                case "0": back = true; break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static void adaugaDispozitiv() {
        System.out.print("Nume dispozitiv: "); String nume = scanner.nextLine().trim();
        System.out.print("Tip (Telefon/Tableta/Laptop): "); String tip = scanner.nextLine().trim();
        int id = nextId++;
        Dispozitiv d = new Dispozitiv(id, nume, tip);
        dispozitive.add(d);
        System.out.println("✔  Dispozitiv adaugat: " + d);
    }

    static void listeazaDispozitive() {
        if (dispozitive.isEmpty()) { System.out.println("  (niciun dispozitiv)"); return; }
        System.out.println("  ID   Nume                 Tip          Activ");
        System.out.println("  ─────────────────────────────────────────────");
        for (Dispozitiv d : dispozitive)
            System.out.printf("  %-4d %-20s %-12s %s%n",
                d.getIdDispozitiv(), d.getNumeDispozitiv(), d.getTip(),
                d.isEsteActiv() ? "DA" : "NU");
    }

    static void asociazaDispozitiv() {
        listeazaDispozitive();
        System.out.print("ID dispozitiv: ");
        try {
            int idD = Integer.parseInt(scanner.nextLine().trim());
            Dispozitiv disp = null;
            for (Dispozitiv d : dispozitive) if (d.getIdDispozitiv() == idD) { disp = d; break; }
            if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
            listeazaProfiluri();
            System.out.print("ID profil: ");
            int idP = Integer.parseInt(scanner.nextLine().trim());
            for (ProfilCopil p : profiluri) {
                if (p.getIdProfil() == idP) { p.adaugaDispozitiv(disp); return; }
            }
            System.out.println("✘  Profil negasit.");
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    static void stergeDispozitivDinProfil() {
        listeazaProfiluri();
        System.out.print("ID profil: ");
        try {
            int idP = Integer.parseInt(scanner.nextLine().trim());
            ProfilCopil profil = null;
            for (ProfilCopil p : profiluri) if (p.getIdProfil() == idP) { profil = p; break; }
            if (profil == null) { System.out.println("✘  Profil negasit."); return; }
            for (Dispozitiv d : profil.getListaDispozitive())
                System.out.printf("  ID=%-4d %s%n", d.getIdDispozitiv(), d.getNumeDispozitiv());
            System.out.print("ID dispozitiv de sters: ");
            int idD = Integer.parseInt(scanner.nextLine().trim());
            profil.stergeDispozitiv(idD);
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    static void blocheazaDispozitiv() {
        listeazaDispozitive();
        System.out.print("ID dispozitiv: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (Dispozitiv d : dispozitive) {
                if (d.getIdDispozitiv() == id) { d.blocheazaDispozitiv(); return; }
            }
            System.out.println("✘  Negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void deblocheazaDispozitiv() {
        listeazaDispozitive();
        System.out.print("ID dispozitiv: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (Dispozitiv d : dispozitive) {
                if (d.getIdDispozitiv() == id) { d.deblocheazaDispozitiv(); return; }
            }
            System.out.println("✘  Negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void sincronizeazaRestrictii() {
        listeazaDispozitive();
        System.out.print("ID dispozitiv: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (Dispozitiv d : dispozitive) {
                if (d.getIdDispozitiv() == id) { d.sincronizeazaRestrictii(); return; }
            }
            System.out.println("✘  Negasit.");
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 5. APLICATII
    // ═══════════════════════════════════════════════════════════════════════════
    static void gestioneazaAplicatii() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── APLICATII ─────────────────────────");
            System.out.println("  1. Adauga aplicatie pe dispozitiv");
            System.out.println("  2. Listeaza aplicatiile unui dispozitiv");
            System.out.println("  3. Blocheaza aplicatie");
            System.out.println("  4. Deblocheaza aplicatie");
            System.out.println("  5. Sterge aplicatie");
            System.out.println("  0. Inapoi");
            System.out.print("Alegere: ");
            switch (scanner.nextLine().trim()) {
                case "1": adaugaAplicatie(); break;
                case "2": listeazaAplicatii(); break;
                case "3": blocheazaAplicatie(); break;
                case "4": deblocheazaAplicatie(); break;
                case "5": stergeAplicatie(); break;
                case "0": back = true; break;
                default: System.out.println("✘  Optiune invalida.");
            }
        }
    }

    static Dispozitiv selecteazaDispozitiv() {
        listeazaDispozitive();
        System.out.print("ID dispozitiv: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            for (Dispozitiv d : dispozitive) if (d.getIdDispozitiv() == id) return d;
        } catch (NumberFormatException e) {}
        return null;
    }

    static void adaugaAplicatie() {
        Dispozitiv disp = selecteazaDispozitiv();
        if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
        System.out.print("Nume aplicatie: "); String nume = scanner.nextLine().trim();
        System.out.print("Categorie (Jocuri/Social/Educatie): "); String cat = scanner.nextLine().trim();
        int id = nextId++;
        Aplicatie app = new Aplicatie(id, nume, cat);
        disp.adaugaAplicatie(app);
    }

    static void listeazaAplicatii() {
        Dispozitiv disp = selecteazaDispozitiv();
        if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
        if (disp.getListaAplicatii().isEmpty()) { System.out.println("  (nicio aplicatie)"); return; }
        System.out.println("  ID   Nume                 Categorie      Blocata");
        System.out.println("  ──────────────────────────────────────────────────");
        for (Aplicatie a : disp.getListaAplicatii())
            System.out.printf("  %-4d %-20s %-14s %s%n",
                a.getIdAplicatie(), a.getNumeAplicatie(), a.getCategorie(),
                a.isEsteBlocata() ? "DA" : "NU");
    }

    static void blocheazaAplicatie() {
        Dispozitiv disp = selecteazaDispozitiv();
        if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
        System.out.print("ID aplicatie: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Aplicatie app = disp.getAplicatie(id);
            if (app == null) System.out.println("✘  Aplicatie negasita.");
            else app.blocheaza();
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void deblocheazaAplicatie() {
        Dispozitiv disp = selecteazaDispozitiv();
        if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
        System.out.print("ID aplicatie: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Aplicatie app = disp.getAplicatie(id);
            if (app == null) System.out.println("✘  Aplicatie negasita.");
            else app.deblocheaza();
        } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
    }

    static void stergeAplicatie() {
        Dispozitiv disp = selecteazaDispozitiv();
        if (disp == null) { System.out.println("✘  Dispozitiv negasit."); return; }
        System.out.print("ID aplicatie de sters: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            disp.stergeAplicatie(id);
            System.out.println("✔  Aplicatie stearsa.");
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 6. INREGISTREAZA UTILIZARE
    // ═══════════════════════════════════════════════════════════════════════════
    static void inregistreazaUtilizare() {
        listeazaProfiluri();
        System.out.print("ID profil: ");
        try {
            int idP = Integer.parseInt(scanner.nextLine().trim());
            ProfilCopil profil = null;
            for (ProfilCopil p : profiluri) if (p.getIdProfil() == idP) { profil = p; break; }
            if (profil == null) { System.out.println("✘  Profil negasit."); return; }
            System.out.print("Minute de adaugat: ");
            int min = Integer.parseInt(scanner.nextLine().trim());
            profil.inregistreazaUtilizare(min);
            System.out.printf("✔  Utilizare inregistrata. Total azi: %d/%d minute.%n",
                profil.getMinuteUtilizateAzi(), profil.getLimitaTimp().getMinuteZilnice());
        } catch (Exception e) { System.out.println("✘  " + e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 7. RAPOARTE
    // ═══════════════════════════════════════════════════════════════════════════
    static void veziRapoarte() {
        listeazaProfiluri();
        System.out.print("ID profil (sau Enter pentru toate): ");
        String inp = scanner.nextLine().trim();
        if (inp.isEmpty()) {
            for (ProfilCopil p : profiluri)
                System.out.println(p.genereazaRaport().genereazaRaport());
        } else {
            try {
                int id = Integer.parseInt(inp);
                for (ProfilCopil p : profiluri) {
                    if (p.getIdProfil() == id) { System.out.println(p.genereazaRaport().genereazaRaport()); return; }
                }
                System.out.println("✘  Profil negasit.");
            } catch (NumberFormatException e) { System.out.println("✘  ID invalid."); }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // PERSISTENTA CSV — SALVARE
    // ═══════════════════════════════════════════════════════════════════════════
    static void salveazaDate() {
        salveazaParinti();
        salveazaCopii();
        salveazaProfiluri();
        salveazaDispozitiveAplicatii();
    }

    static void salveazaParinti() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PARINTI))) {
            pw.println("id,nume,email,parola");
            for (Parinte p : parinti)
                pw.printf("%d,%s,%s,%s%n",
                    p.getId(), esc(p.getNume()), esc(p.getEmail()), esc(p.getParola()));
        } catch (IOException e) { System.out.println("✘  Eroare salvare parinti: " + e.getMessage()); }
    }

    static void salveazaCopii() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_COPII))) {
            pw.println("id,nume,email,parola,varsta");
            for (Copil c : copii)
                pw.printf("%d,%s,%s,%s,%d%n",
                    c.getId(), esc(c.getNume()), esc(c.getEmail()), esc(c.getParola()), c.getVarsta());
        } catch (IOException e) { System.out.println("✘  Eroare salvare copii: " + e.getMessage()); }
    }

    static void salveazaProfiluri() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PROFILURI))) {
            pw.println("idProfil,idCopil,minuteZilnice,oraInceput,oraSfarsit,minuteUtilizateAzi");
            for (ProfilCopil p : profiluri)
                pw.printf("%d,%d,%d,%s,%s,%d%n",
                    p.getIdProfil(), p.getCopil().getId(),
                    p.getLimitaTimp().getMinuteZilnice(),
                    esc(p.getLimitaTimp().getOraInceput()),
                    esc(p.getLimitaTimp().getOraSfarsit()),
                    p.getMinuteUtilizateAzi());
        } catch (IOException e) { System.out.println("✘  Eroare salvare profiluri: " + e.getMessage()); }
    }

    static void salveazaDispozitiveAplicatii() {
        try (PrintWriter pwD = new PrintWriter(new FileWriter(CSV_DISPOZITIVE));
             PrintWriter pwA = new PrintWriter(new FileWriter(CSV_APLICATII))) {

            pwD.println("idDispozitiv,numeDispozitiv,tip,esteActiv,idProfil");
            pwA.println("idAplicatie,numeAplicatie,categorie,esteBlocata,idDispozitiv");

            // Seturi de id-uri deja scrise pentru dispozitive
            Set<Integer> dispScrise = new HashSet<>();

            for (ProfilCopil profil : profiluri) {
                for (Dispozitiv d : profil.getListaDispozitive()) {
                    if (!dispScrise.contains(d.getIdDispozitiv())) {
                        pwD.printf("%d,%s,%s,%b,%d%n",
                            d.getIdDispozitiv(), esc(d.getNumeDispozitiv()),
                            esc(d.getTip()), d.isEsteActiv(), profil.getIdProfil());
                        dispScrise.add(d.getIdDispozitiv());
                    }
                    for (Aplicatie a : d.getListaAplicatii())
                        pwA.printf("%d,%s,%s,%b,%d%n",
                            a.getIdAplicatie(), esc(a.getNumeAplicatie()),
                            esc(a.getCategorie()), a.isEsteBlocata(), d.getIdDispozitiv());
                }
            }
            // Dispozitive neatasate la niciun profil
            for (Dispozitiv d : dispozitive) {
                if (!dispScrise.contains(d.getIdDispozitiv())) {
                    pwD.printf("%d,%s,%s,%b,-1%n",
                        d.getIdDispozitiv(), esc(d.getNumeDispozitiv()), esc(d.getTip()), d.isEsteActiv());
                    for (Aplicatie a : d.getListaAplicatii())
                        pwA.printf("%d,%s,%s,%b,%d%n",
                            a.getIdAplicatie(), esc(a.getNumeAplicatie()),
                            esc(a.getCategorie()), a.isEsteBlocata(), d.getIdDispozitiv());
                }
            }
        } catch (IOException e) { System.out.println("✘  Eroare salvare dispozitive: " + e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // PERSISTENTA CSV — INCARCARE
    // ═══════════════════════════════════════════════════════════════════════════
    static void incarcaDate() {
        incarcaParinti();
        incarcaCopii();
        incarcaDispozitiveAplicatii();
        incarcaProfiluri();
        // Recalculeaza nextId
        int max = 99;
        for (Parinte p : parinti)     if (p.getId() > max) max = p.getId();
        for (Copil c : copii)         if (c.getId() > max) max = c.getId();
        for (ProfilCopil p : profiluri) if (p.getIdProfil() > max) max = p.getIdProfil();
        for (Dispozitiv d : dispozitive) if (d.getIdDispozitiv() > max) max = d.getIdDispozitiv();
        nextId = max + 1;

        System.out.println("✔  Date incarcate: " + parinti.size() + " parinti, " +
            copii.size() + " copii, " + profiluri.size() + " profiluri, " +
            dispozitive.size() + " dispozitive.");
    }

    static void incarcaParinti() {
        File f = new File(CSV_PARINTI);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] t = line.split(",", -1);
                if (t.length < 4) continue;
                parinti.add(new Parinte(Integer.parseInt(t[0].trim()), t[1], t[2], t[3]));
            }
        } catch (Exception e) { System.out.println("✘  Eroare citire parinti: " + e.getMessage()); }
    }

    static void incarcaCopii() {
        File f = new File(CSV_COPII);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] t = line.split(",", -1);
                if (t.length < 5) continue;
                copii.add(new Copil(Integer.parseInt(t[0].trim()), t[1], t[2], t[3],
                                    Integer.parseInt(t[4].trim())));
            }
        } catch (Exception e) { System.out.println("✘  Eroare citire copii: " + e.getMessage()); }
    }

    static void incarcaDispozitiveAplicatii() {
        File fd = new File(CSV_DISPOZITIVE);
        if (fd.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fd))) {
                br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] t = line.split(",", -1);
                    if (t.length < 5) continue;
                    Dispozitiv d = new Dispozitiv(Integer.parseInt(t[0].trim()), t[1], t[2]);
                    if ("false".equalsIgnoreCase(t[3].trim())) d.blocheazaDispozitiv();
                    dispozitive.add(d);
                }
            } catch (Exception e) { System.out.println("✘  Eroare citire dispozitive: " + e.getMessage()); }
        }
        File fa = new File(CSV_APLICATII);
        if (fa.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fa))) {
                br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] t = line.split(",", -1);
                    if (t.length < 5) continue;
                    int idDisp = Integer.parseInt(t[4].trim());
                    for (Dispozitiv d : dispozitive) {
                        if (d.getIdDispozitiv() == idDisp) {
                            Aplicatie a = new Aplicatie(Integer.parseInt(t[0].trim()), t[1], t[2]);
                            if ("true".equalsIgnoreCase(t[3].trim())) a.blocheaza();
                            d.getListaAplicatii().add(a);
                            break;
                        }
                    }
                }
            } catch (Exception e) { System.out.println("✘  Eroare citire aplicatii: " + e.getMessage()); }
        }
    }

    static void incarcaProfiluri() {
        File f = new File(CSV_PROFILURI);
        if (!f.exists()) return;
        // Prima trecere: creaza profilurile
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] t = line.split(",", -1);
                if (t.length < 6) continue;
                int idProfil = Integer.parseInt(t[0].trim());
                int idCopil  = Integer.parseInt(t[1].trim());
                Copil copil  = null;
                for (Copil c : copii) if (c.getId() == idCopil) { copil = c; break; }
                if (copil == null) continue;
                LimitaTimp limita = new LimitaTimp(Integer.parseInt(t[2].trim()), t[3], t[4]);
                ProfilCopil profil = new ProfilCopil(idProfil, copil, limita);
                int minuteAzi = Integer.parseInt(t[5].trim());
                if (minuteAzi > 0) profil.inregistreazaUtilizare(minuteAzi);
                profiluri.add(profil);
            }
        } catch (Exception e) { System.out.println("✘  Eroare citire profiluri: " + e.getMessage()); }

        // A doua trecere: asociaza dispozitivele la profiluri
        File fd = new File(CSV_DISPOZITIVE);
        if (!fd.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(fd))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] t = line.split(",", -1);
                if (t.length < 5) continue;
                int idDisp   = Integer.parseInt(t[0].trim());
                int idProfil = Integer.parseInt(t[4].trim());
                if (idProfil < 0) continue;
                Dispozitiv disp = null;
                for (Dispozitiv d : dispozitive) if (d.getIdDispozitiv() == idDisp) { disp = d; break; }
                ProfilCopil profil = null;
                for (ProfilCopil p : profiluri) if (p.getIdProfil() == idProfil) { profil = p; break; }
                if (disp != null && profil != null && !profil.getListaDispozitive().contains(disp))
                    profil.getListaDispozitive().add(disp);
            }
        } catch (Exception e) { System.out.println("✘  Eroare re-asociere dispozitive: " + e.getMessage()); }
    }

    // CSV escape: inlocuieste virgula cu semicolon (campuri simple fara ghilimele)
    static String esc(String s) {
        if (s == null) return "";
        return s.replace(",", ";");
    }
}
