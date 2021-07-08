import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


final class Tranzactie {
    final int cantitate;

    public Tranzactie(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getCantitate() {
        return cantitate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "cantitate=" + cantitate +
                '}';
    }
}


final class Produs {

    private final int cod;
    private final String denumire;
    private final double pret;
    private final List<Tranzactie> tranzactii;

    public Produs(int cod, String denumire, double pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.pret = pret;
        tranzactii = new ArrayList<>();
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getPret() {
        return pret;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public double getValoareStoc() {
        int cantitateCurenta = 0;
        for (var tranzactie : tranzactii) {
            cantitateCurenta += tranzactie.getCantitate();
        }
        return cantitateCurenta * getPret();
    }

    @Override
    public String toString() {
        return "Produs{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", tranzactii=" + tranzactii +
                ", valStoc=" + getValoareStoc() +
                '}';
    }
}

class ExempluSubiect {

    static final String CALE_PRODUSE = "date\\produse.txt";
    static final String CALE_TRANZACTII = "date\\tranzactii.json";

    static final int SERVER_PORT =8276;

    static List<Produs> produse;

    public static void main(String[] args) throws Exception {

//        int numarProduse = 0;
//        try (var fisier = new Scanner(new File(CALE_PRODUSE))) {
//            while (fisier.hasNextLine()) {
//                var linie = fisier.nextLine();
//                var produs = new Produs(
//                        Integer.parseInt(linie.split(",")[0]),
//                        linie.split(",")[1],
//                        Double.parseDouble(linie.split(",")[2])
//                );
//                numarProduse++;
//            }
//        }
//        System.out.printf("CERINTA 1: Numar produse %d%n", numarProduse);
//        System.out.println();

        try (var fisier = new BufferedReader(new FileReader(CALE_PRODUSE))) {
            produse = fisier.lines()
                    .map(linie -> new Produs(
                            Integer.parseInt(linie.split(",")[0]),
                            linie.split(",")[1],
                            Double.parseDouble(linie.split(",")[2])
                    ))
                    .collect(Collectors.toList());
        }

        System.out.printf("CERINTA 1: Numar produse %d%n", produse.size());
        System.out.println();

        System.out.println("CERINTA 2:");
        produse.stream()
                .sorted((p1, p2) -> p1.getDenumire().compareTo(p2.getDenumire()))
                .forEach(p -> System.out.println(p.getDenumire()));
        System.out.println();

        try (var fisier = new FileReader(CALE_TRANZACTII)) {
            var array = new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < array.length(); i++) {
                var jsonTranzactie = array.getJSONObject(i);

                var cod = jsonTranzactie.getInt("codProdus");
                var cantitate = jsonTranzactie.getInt("cantitate");
                var tip = jsonTranzactie.getString("tip");

                if (tip.equals("iesire")) {
                    cantitate = -cantitate;
                }

                produse.stream()
                        .filter(p -> p.getCod() == cod)
                        .findFirst().orElse(null)
                        .getTranzactii().add(new Tranzactie(cantitate));
            }
        }

        System.out.println("CERINTA 3: -> in fisier");
        try (var fisier = new PrintWriter(new FileWriter(
                "date\\lista.txt"))) {
            produse.stream()
                    .sorted((p1, p2) -> Integer.compare(p2.getTranzactii().size(), p1.getTranzactii().size()))
                    .forEach(p -> fisier.printf("%s, %d%n",
                            p.getDenumire(),
                            p.getTranzactii().size()));
        }

        double valoareTotala = 0;
        for (var produs : produse) {
            valoareTotala += produs.getValoareStoc();
        }
        System.out.printf(
                "CERINTA 4: Valoarea totala a stocului curent este %.2f lei%n",
                valoareTotala);

        // -> pornire server pe thread secundar
        new Thread(ExempluSubiect::serverTcp).start();

        // -> trimitere cerere către server
        try (var socket = new Socket("localhost", SERVER_PORT);
             var out = new ObjectOutputStream(socket.getOutputStream());
             var in = new ObjectInputStream(socket.getInputStream());
        ) {
            int codProdus = 2;

            out.writeObject(2);

            // -> citeste de pe socket raspunsul de la server
            double valoareStoc = (Double) in.readObject();

            System.out.printf(
                    "CERINTA 5: Valoarea stocului pentru produsul #%d este %.2f lei%n",
                    codProdus,
                    valoareStoc);
        }
    }

    public static void serverTcp() {
        try (
                var serverSocket = new ServerSocket(SERVER_PORT);
                var socket = serverSocket.accept();
                var in = new ObjectInputStream(socket.getInputStream());
                var out = new ObjectOutputStream(socket.getOutputStream());
        ) {
            int codProdus = (Integer) in.readObject();
            Produs produs = produse.stream()
                    .filter(p -> p.getCod() == codProdus)
                    .findFirst().orElse(null);
            out.writeObject(produs.getValoareStoc());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
