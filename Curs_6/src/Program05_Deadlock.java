class TestDeadlock {
    Object lockA = new Object(), lockB = new Object();
    private int a = 0, b = 0;

//    public void incAB() {
//        synchronized (lockA) {
//            a++;
//            synchronized (lockB) {
//                b++;
//            }
//        }
//    }
//
//    public void incBA() {
//        synchronized (lockB) {
//            b++;
//            synchronized (lockA) {
//                a++;
//            }
//        }
//    }

    // Varianta corectă:
    public void incAB() {
        synchronized (lockB) {          // se blochează ambele resurse,
            synchronized (lockA) {      // în ACEEAȘI ORDINE
                a++;                    // ordinea operațiilor asupra datelor nu contează
                b++;
            }
        }
    }

    public void incBA() {
        synchronized (lockB) {          // se blochează ambele resurse,
            synchronized (lockA) {      // în ACEEAȘI ORDINE
                b++;
                a++;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("a = %d, b = %d", a, b);
    }
}

public class Program05_Deadlock {

    public static void main(String[] args) throws Exception {
        var test = new TestDeadlock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(0, 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.incAB();
                    System.out.println(test);
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(0, 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.incBA();
                    System.out.println(test);
                }

            }
        }).start();
    }
}
