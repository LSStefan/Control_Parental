public class LimitaTimp {

    private int minuteZilnice;
    private String oraInceput;
    private String oraSfarsit;

    public LimitaTimp(int minuteZilnice, String oraInceput, String oraSfarsit) {
        if (minuteZilnice <= 0) {
            throw new IllegalArgumentException("Minutele zilnice trebuie sa fie pozitive.");
        }
        this.minuteZilnice = minuteZilnice;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
    }

    public boolean verificaDepasire(int minuteUtilizate) {
        return minuteUtilizate >= minuteZilnice;
    }

    public void actualizeazaLimita(int minuteNoi) {
        if (minuteNoi <= 0) {
            throw new IllegalArgumentException("Limita trebuie sa fie pozitiva.");
        }
        this.minuteZilnice = minuteNoi;
        System.out.println("Limita actualizata la " + minuteNoi + " minute/zi.");
    }

    public int getMinuteZilnice() { return minuteZilnice; }
    public String getOraInceput() { return oraInceput; }
    public String getOraSfarsit() { return oraSfarsit; }

    public void setOraInceput(String oraInceput) { this.oraInceput = oraInceput; }
    public void setOraSfarsit(String oraSfarsit) { this.oraSfarsit = oraSfarsit; }

    @Override
    public String toString() {
        return "LimitaTimp{minuteZilnice=" + minuteZilnice +
               ", interval=" + oraInceput + "-" + oraSfarsit + "}";
    }
}
