import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Sectie {
    final int cod;
    final String denumire;
    final int nrLocuri;
    final List<Pacient> listaPacienti;

    public Sectie(int cod, String denumire, int nrLocuri) {
        this.cod = cod;
        this.denumire = denumire;
        this.nrLocuri = nrLocuri;
        listaPacienti = new ArrayList<>();
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public List<Pacient> getListaPacienti() {
        return listaPacienti;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", nrLocuri=" + nrLocuri +
                ", Varsta Medie="+medieVarsta()+
                '}';
    }

    public float medieVarsta()
    {
        float m = 0;
        for (Pacient p: listaPacienti
             ) {
            m+=p.getVarsta();
        }
        return  m/listaPacienti.size();
    }
}
