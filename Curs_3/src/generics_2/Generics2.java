package generics_2;

import java.util.*;


class Student implements Comparable, Cloneable {
    private Map<String, Integer> note;
    private final String nume;

    public Student(String nume) {
        this.nume = nume;
        this.note = new HashMap<>();
    }

    public String getNume() {
        return nume;
    }

    public boolean areNota(String disciplina) {
        return note.containsKey(disciplina);
    }

    public int getNota(String disciplina) {
        if (areNota(disciplina)) {
            return note.get(disciplina);
        } else {
            throw new NoSuchElementException(String.format(
                    "Studentul '%s' nu are notă la disciplina '%s'.", nume, disciplina));
        }
    }

    public Set<String> getDiscipline() {
        return note.keySet();
    }

    public void adaugaNota(String disciplina, int nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("Notă invalidă.");
        }
        note.put(disciplina, nota);
    }

    public void stergeNota(String disciplina) {
        note.remove(disciplina);
    }

    @Override
    public String toString() {
        var message = new StringBuilder();
        message.append(nume);
        message.append(System.lineSeparator());
        for (Map.Entry<String, Integer> nota : note.entrySet()) {
            message.append("   " + nota.getKey() + ":" + nota.getValue());
            message.append(System.lineSeparator());
        }
        return message.toString();
    }

    @Override
    public int compareTo(Object o) {
        Student altStudent = (Student)o;
        return nume.compareTo(altStudent.nume);
        // sa se compare media generala a notelor aferente disciplinelor din dictionar
    }

    @Override
    public Student clone() {
        try {
            Student copie = (Student)super.clone();
            copie.note = new HashMap<>(this.note);
            return copie;
        }
        catch(CloneNotSupportedException e) {
            throw new AssertionError("Clone nu este suportat.", e);
        }
    }
}

public class Generics2 {

    public static void afiseazaCatalog(List<Student> studenti, String disciplina) {

        var studentiSortati = new ArrayList<>(studenti);
        Collections.sort(studentiSortati);

        System.out.println();
        System.out.println("Catalog " + disciplina);
        System.out.println();

        for (var student : studentiSortati) {
            System.out.printf("%-8s", student.getNume());
            if (student.areNota(disciplina)) {
                System.out.printf("%3d", student.getNota(disciplina));
            } else {
                System.out.print("Absent");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Student gabriel = new Student("Gabriel");
        gabriel.adaugaNota("Java", 9);
        gabriel.adaugaNota("PAW", 8);
        gabriel.adaugaNota("POO", 10);
        System.out.println(gabriel.toString());

        // realizam o actualizare a valorii pentru cheia funizata ca parametru
        gabriel.adaugaNota(new String("Java"), 10);
        System.out.println(gabriel);

        gabriel.stergeNota("PAW");
        gabriel.adaugaNota("SGBD", 8);
        System.out.println(gabriel);

        for (String disciplina : gabriel.getDiscipline()) {
            System.out.println(disciplina + " -> " + gabriel.getNota(disciplina));
        }

        Student ioana = new Student("Ioana");
        ioana.adaugaNota("Java", 9);
        ioana.adaugaNota("PAW", 10);

        Student vasile = new Student("Vasile");

        List<Student> studenti = new ArrayList<>();
        studenti.add(ioana);
        Collections.addAll(studenti, vasile, gabriel);
        afiseazaCatalog(studenti, "Java");

        var clonaGabriel = gabriel.clone();
        clonaGabriel.adaugaNota("Java", 8);
        clonaGabriel.adaugaNota("Test", 10);
        System.out.println(gabriel);
        System.out.println(clonaGabriel);
    }
}
