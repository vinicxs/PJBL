import java.io.*;
import java.util.ArrayList;

public class GerenciadorCSV {
    public static void salvarEmbarcacoes(ArrayList<Embarcacao> embarcacoes, String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (Embarcacao e : embarcacoes) {
                for (int[] pos : e.getPosicao()) {
                    writer.printf("%s,%d,%d\n", e.getNome(), pos[0], pos[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Embarcacao> carregarEmbarcacoes(String fileName) {
        ArrayList<Embarcacao> embarcacoes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] data = linha.split(",");
                // Parse dos dados e reconstrução da embarcação
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return embarcacoes;
    }
}
