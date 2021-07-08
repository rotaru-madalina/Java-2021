package Financiar;

public final class Identificator {
    private final String ticker;
    private final String descriere;

    public Identificator(String ticker, String descriere) {
        this.ticker = ticker;
        this.descriere = descriere;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDescriere() {
        return descriere;
    }
}
