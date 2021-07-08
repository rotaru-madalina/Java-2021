package Financiar;


class Instrument extends Object implements Cloneable, Calculabil {
//    protected Identificator id;
    protected String simbol;
    protected String nume;
    protected double pret;
    protected int cantitate;
    protected Status stare;

    public Instrument() {
        // nu voi mai putea modifica ulterior aceste valori
//        this.id  = new Identificator(null, null);
        this.simbol = null;
        this.nume = null;
        this.pret = 0.F;
        this.cantitate = 0;
        this.stare = Status.NECUNOSCUT;
//        this.stare = new Status("NECUNOSCUT");
    }

    public Instrument(String simbol, String nume, double pret, Status stare) {
        this.simbol = simbol;
        this.nume = nume;
        this.pret = pret;
        this.stare = stare;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public Status getStare() {
        return stare;
    }

    public void setStare(Status stare) {
        this.stare = stare;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "simbol='" + simbol + '\'' +
                ", nume='" + nume + '\'' +
                ", pret=" + pret +
                '}';
//        return this.simbol + "," +
//                this.nume + "," +
//                Double.toString(this.pret) + "," +
//                stare.toString();
    }
}
