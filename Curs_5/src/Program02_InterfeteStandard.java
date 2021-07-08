import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Program02_InterfeteStandard {

    static <T> List<T> generareLista(int numarElemente, Supplier<T> generator) {
        var rezultat = new ArrayList<T>();
        for (int index = 0; index < numarElemente; index++) {
            rezultat.add(generator.get());
        }
        return rezultat;
    }

    static <T> void consumaLista(List<T> lista, Consumer<T> consumator) {
        for (var valoare : lista) {
            consumator.accept(valoare);
        }
    }

    static <T> List<T> filtrareLista(List<T> lista, Predicate<T> predicat) {
        var rezultat = new ArrayList<T>();
        for (var valoare : lista) {
            if (predicat.test(valoare)) {
                rezultat.add(valoare);
            }
        }
        return rezultat;
    }

    public static void main(String[] args) {

        // Construire și utilizare interfața standard Generator
        Supplier<Integer> generatorAleator =
                () -> (int) (Math.random() * 200 - 100);    // număr între -100 și +100, metoda random() returneaza valori in [0, 1]
        // [0, 1] -> [a, b] (random()*(b-a) + a)

        var lista = generareLista(7, generatorAleator);


        // Construire operație folosind expresie lambda și interfața standard Consumer
        Consumer<List<Integer>> afisareLista = oLista -> {
            consumaLista(
                    oLista,
                    elem -> System.out.print(elem + " "));
            System.out.println();
        };
        afisareLista.accept(lista);

        // Utilizare metode de compunere
        Predicate<Integer> estePar = n -> n % 2 == 0;
        Predicate<Integer> estePozitiv = n -> n > 0;
        afisareLista.accept(filtrareLista(lista, estePar.and(estePozitiv)));

        // Implementare interfață funcțională standard prin clasă anonimă
        var suma = new Consumer<Integer>() {
            int suma;
            @Override
            public void accept(Integer valoare) {
                suma += valoare;
            }

            public int getSuma() { return suma; }
        };
        consumaLista(lista, suma);
        System.out.println("Suma este " + suma.getSuma());

        // Exemplu simplu de functii si compunerea acestora
        Function<Integer, Integer> adunare = e -> e + e;
        Function<Integer, Integer> inmultire = e -> e * 5;
        Function<Integer, Integer> putere = e -> e * e;

        System.out.println("Adunare urmata de inmultire:");
        System.out.println(inmultire.compose(adunare).apply(3).toString());

        System.out.println("Ridicarea la putere urmata de inmultire:");
        System.out.println(inmultire.compose(putere).apply(3).toString());

        System.out.println("Adunare urmata de inmultire ai apoi de ridicarea la putere:");
        System.out.println(inmultire.andThen(putere).compose(adunare).apply(4).toString());

    }
}
