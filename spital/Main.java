import org.json.JSONArray;
import org.json.JSONTokener;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static final String CALE_PACIENTI = "date\\pacienti.txt";
    static final String CALE_SECTII = "date\\sectii.json";
    static final int SERVER_PORT = 8276;
    static List<Pacient> pacienti;
    static List<Sectie> sectii = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        try(var fisier = new FileReader(CALE_SECTII)){
            var array = new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<array.length();i++){
                var jsonSectie = array.getJSONObject(i);

                var cod = jsonSectie.getInt("cod_sectie");
                var den =  jsonSectie.getString("denumire");
                var nr = jsonSectie.getInt("numar_locuri");
                sectii.add(new Sectie(cod,den,nr));
            }
        }
        sectii.stream().filter(p -> p.getNrLocuri()>10).forEach(p ->System.out.println(p));
        
        try(var fisier = new BufferedReader(new FileReader(CALE_PACIENTI))){
            pacienti = fisier.lines()
                    .map(linie -> new Pacient(
                            Long.parseLong(linie.split(",")[0]),
                            linie.split(",")[1],
                            Integer.parseInt(linie.split(",")[2]),
                            Integer.parseInt(linie.split(",")[3])
                    ))
                    .collect(Collectors.toList());
        }
        for (Pacient p2 :pacienti) {
            sectii.stream()
                    .filter(p -> p.getCod() == p2.getCodSectie())
                    .findFirst().orElse(null).getListaPacienti().add(p2);
        }
        System.out.println();
        sectii.stream().sorted((p1,p2) -> Float.compare(p2.medieVarsta(), p1.medieVarsta()))
                .forEach(p -> System.out.println(p));

        try(var fisier = new PrintWriter(new FileWriter("date\\jurnal.txt"))){
            sectii.stream().forEach(p -> fisier.printf("%d %s %d%n", p.getCod(), p.getDenumire(), p.getListaPacienti().size()));
        }

        new Thread(Main::serverTCP).start();

        try(var socket = new Socket("localhost", SERVER_PORT);
        var out = new ObjectOutputStream(socket.getOutputStream());
        var in = new ObjectInputStream(socket.getInputStream());
        ){
            int codS = 2;
            out.writeObject(codS);
            int locuriLibere = (Integer)in.readObject();
            System.out.println(locuriLibere);
        }
    }

    public static void serverTCP() {
        try(
                var serverSocket = new ServerSocket(SERVER_PORT);
                var socket = serverSocket.accept();
                var in = new ObjectInputStream(socket.getInputStream());
                var out = new ObjectOutputStream(socket.getOutputStream());
                ){
            int codSectie = (Integer) in.readObject();
            Sectie sectie = sectii.stream().filter(p -> p.getCod() == codSectie).findFirst().orElse(null);
            out.writeObject(sectie.getNrLocuri() - sectie.getListaPacienti().size());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
