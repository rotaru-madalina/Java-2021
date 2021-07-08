package generics_1;

import java.util.Arrays;


class Colectie <T> {
    private Object[] elemente = new Object[0];

    public void addElement(T element) {
        elemente = Arrays.copyOf(elemente, elemente.length + 1);
        elemente[elemente.length - 1] = element;
    }

    public T getElement(int index){
        return (T)elemente[index];
    }

    public int getNumarElemente(){
        return elemente.length;
    }

    // ar trebui sa avem garantia ca exista metoda toString() suprascrisa
    // pentru orice tip de element <T> am furniza ca paramentru clasei
    @Override
    public String toString() {
        return "Colectie{" +
                "elemente=" + Arrays.toString(elemente) +
                '}';
    }
    // ar trebui sa avem garantia ca exista metoda equals() suprascrisa
    // pentru oricee tip de element <T> am furniza ca paramentru clasei
    public <T> boolean isElement(T element) {
        for (int i = 0; i < elemente.length; i++) {
            if (elemente[i].equals(element)){
                return true;
            }
        }
        return false;
    }
}

public class Generics1 {
    public static <T> void afisareColectie(Colectie<T> colectie) {
        for (int index = 0; index < colectie.getNumarElemente(); index++) {
            System.out.println(colectie.getElement(index));
        }
    }

    public static <T> boolean existaElement(Colectie<T> colectie, T element) {
        for (int index = 0; index < colectie.getNumarElemente(); index++) {
            if (colectie.getElement(index).equals(element)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Colectie<String> v1 = new Colectie<>();
        v1.addElement("Ana-Maria Badulescu");
        v1.addElement("este studenta");
        v1.addElement("la Informatica Economica");
        System.out.println(v1.toString());
        afisareColectie(v1);

        if (v1.isElement("este studenta")) {
            System.out.println("Este!");
        }

        if (!v1.isElement("Ana-Maria")) {
            System.out.println("Nu este1!");
        }

        if (!existaElement(v1, "Ana-Maria Badulescu")) {
            System.out.println("Nu este!");
        }

        Colectie<Integer> v2 = new Colectie<>();
        v2.addElement(1);
        v2.addElement(2);
        v2.addElement(3);
//        v2.addElement("Un sir de caractere");
        afisareColectie(v2);

        // colectie eterogena de tipuri de obiecte
        Colectie v3 = new Colectie();
        v3.addElement(7); v3.addElement("inca un element");
        afisareColectie(v3);
        if (existaElement(v3, 7)) {
            System.out.println("Exista!");
        }
        if (existaElement(v3, "inca un element")) {
            System.out.println("Exista si acesta!");
        }
    }
}

