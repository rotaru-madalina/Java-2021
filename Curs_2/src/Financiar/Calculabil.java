package Financiar;

public interface Calculabil {
    public default double valoare(double pret, int cantitate) {
        return pret * cantitate;
    }

//    default double valoare(Instrument instrument) {
//        return instrument.getPret();
//    }
}
