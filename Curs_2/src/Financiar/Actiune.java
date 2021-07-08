package Financiar;

public class Actiune extends Instrument implements Cloneable, Calculabil {
    private double dividend;

    public Actiune(String simbol, String nume, double pret, Status stare, double dividend) {
        super(simbol, nume, pret, stare);
        this.dividend = dividend;
    }

    public Actiune() {
        super();
        this.dividend = 0;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    @Override
    public double valoare(double pret, int cantitate) {
        return (pret * cantitate) + (pret*cantitate*this.dividend);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Actiune clona = (Actiune)super.clone();

        clona.dividend = this.dividend;
        clona.simbol = this.simbol;
        clona.nume = this.nume;
        clona.pret = this.pret;
        clona.cantitate = this.cantitate;
        // copiere deep daca atributele sunt referinte la obiecte

        return clona;
    }

    // cnad Instrument este declarata abstracta
//    @Override
//    public String toString() {
//        return "Actiune{" +
//                "dividend=" + dividend +
//                '}';
//    }


    @Override
    public String toString() {
        return "Actiune{" +
                "dividend=" + dividend +
                ", simbol='" + simbol + '\'' +
                ", nume='" + nume + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                ", stare=" + stare +
                '}';
    }
}
