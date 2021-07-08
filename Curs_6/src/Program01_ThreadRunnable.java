// Varianta 1: Derivare clasa Thread și suprascriere metodă run
class FirExecutie1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("A");
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
// Varianta 2: Implementare interfață Runnable și utilizarea
// constructorului clasei Thread pentru construirea obiectului
class FirExecutie2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("B");
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
public class Program01_ThreadRunnable {

    public static void main(String[] args) {
        // Pornire fir de execuție - metoda start
        Thread t1 = new FirExecutie1();
        t1.start();
        Thread t2 = new Thread(new FirExecutie2());
        t2.start();
    }
}
