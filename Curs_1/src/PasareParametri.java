public class PasareParametri {

    static void interschimbValori(int a, int b) {
        int aux;

        aux = a;
        a = b;
        b = aux;
    }

    static void interschimbObiecte(int[] vector) {
        int aux;

        aux = vector[0];
        vector[0] = vector[1];
        vector[1] = aux;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar incorect de argumente: PasareParametri <int> <int>");
            System.exit(-1);
        }

        int[] masivInt = new int[args.length];

        for (int i=0; i<args.length; i++) {
            masivInt[i] = Integer.parseInt(args[i]);
            System.out.print(masivInt[i] + " ");
        }
        System.out.println();

        int a = masivInt[0], b = masivInt[1];

        System.out.printf("Valori initiale: \n a=%d b=%d \n", a, b);
        interschimbValori(a, b);
        System.out.printf("Valori interschimbate: \n a=%d b=%d \n", a, b);

        System.out.printf("Valori initiale masiv: \n a=%d b=%d \n", masivInt[0], masivInt[1]);
        interschimbObiecte(masivInt);
        System.out.printf("Valori intershimbate masiv: \n a=%d b=%d \n", masivInt[0], masivInt[1]);
    }
}
