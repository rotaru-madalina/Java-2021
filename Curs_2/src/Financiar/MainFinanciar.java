package Financiar;

public class MainFinanciar {
    public static void main(String[] args) {
        Instrument i1 = new Instrument("BRD",
                "Banca Romana pentru Dezvoltare", 12.78, Status.TRANZACTIONABIL);
        System.out.println(i1);

        Instrument i2 = new Instrument();
        try {
            i2 = (Instrument) i1.clone();
            System.out.println(i2.toString());
            i1.setPret(44.2);
            i1.setNume("O banca din Romania");
            System.out.println("i1 dupa modificare" + i1.toString());
            System.out.println("i2 dupa modificare i1: " + i2.toString());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        Actiune a1 = new Actiune("BRD",
                "Banca Romana pentru Dezvoltare", 12.78, Status.TRANZACTIONABIL, 0.02);
        Actiune a2 = new Actiune();
        Actiune a3 = null;

        System.out.println(a1.toString());
        System.out.println(a2.toString());


        try {
            a2 = (Actiune)a1.clone();
            System.out.println(a2.toString());
            a3 = (Actiune)a1.clone();
            System.out.println(a3.toString());
            a1.setPret(55.9);
            System.out.println("a1 dupa modificare " + a1.toString());
            System.out.println("a2 dupa modificare a1: " + a2.toString());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
