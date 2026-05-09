import java.util.ArrayList;
import java.util.List;

public class RaportActivitate {

    private int idRaport;
    private String dataGenerare;
    private int minuteUtilizateTotal;
    private List<String> listaAplicatiiAccesate;

    public RaportActivitate(int idRaport, String dataGenerare) {
        this.idRaport = idRaport;
        this.dataGenerare = dataGenerare;
        this.minuteUtilizateTotal = 0;
        this.listaAplicatiiAccesate = new ArrayList<>();
    }

    public void adaugaAplicatieAccesata(String numeAplicatie, int minuteUtilizate) {
        listaAplicatiiAccesate.add(numeAplicatie + " (" + minuteUtilizate + " min)");
        this.minuteUtilizateTotal += minuteUtilizate;
    }

    public String genereazaRaport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RAPORT ACTIVITATE ===\n");
        sb.append("ID Raport: ").append(idRaport).append("\n");
        sb.append("Data: ").append(dataGenerare).append("\n");
        sb.append("Total timp utilizat: ").append(minuteUtilizateTotal).append(" minute\n");
        sb.append("Aplicatii accesate:\n");
        if (listaAplicatiiAccesate.isEmpty()) {
            sb.append("  - Nicio aplicatie accesata\n");
        } else {
            for (String app : listaAplicatiiAccesate) {
                sb.append("  - ").append(app).append("\n");
            }
        }
        sb.append("=========================");
        return sb.toString();
    }

    public int getMinuteUtilizate() { return minuteUtilizateTotal; }
    public int getIdRaport() { return idRaport; }
    public String getDataGenerare() { return dataGenerare; }
    public List<String> getListaAplicatiiAccesate() { return listaAplicatiiAccesate; }

    @Override
    public String toString() {
        return "RaportActivitate{id=" + idRaport + ", data='" + dataGenerare +
               "', minuteTotal=" + minuteUtilizateTotal + "}";
    }
}
