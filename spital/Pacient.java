final class Pacient {
    private final long codPacient;
    private final String nume;
    private final int varsta;
    private final int codSectie;

    public Pacient(long codPacient, String nume, int varsta, int codSectie) {
        this.codPacient = codPacient;
        this.nume = nume;
        this.varsta = varsta;
        this.codSectie = codSectie;
    }

    public long getCodPacient() {
        return codPacient;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public int getCodSectie() {
        return codSectie;
    }
}
