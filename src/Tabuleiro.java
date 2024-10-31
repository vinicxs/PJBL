import java.util.ArrayList;
import java.util.Random;

class Tabuleiro {
    private final int tamanho = 10;
    private String[][] matriz;
    private ArrayList<int[]> unidadesDoJogador; // Lista de posições das unidades do jogador
    private ArrayList<int[]> unidadesDoOponente; // Lista de posições das unidades do oponente

    public Tabuleiro() {
        matriz = new String[tamanho][tamanho];
        unidadesDoJogador = new ArrayList<>();
        unidadesDoOponente = new ArrayList<>();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                matriz[i][j] = ".";
            }
        }
    }

    // Retorna a lista de unidades do jogador
    public ArrayList<int[]> getUnidadesDoJogador() {
        return unidadesDoJogador;
    }

    // Posiciona uma unidade do jogador na matriz e na lista de unidades
    public void posicionarUnidadeJogador(int x, int y, String simbolo) {
        matriz[x][y] = simbolo;
        unidadesDoJogador.add(new int[]{x, y});
    }

    // Verifica se uma posição está vazia
    public boolean isPosicaoVazia(int x, int y) {
        return matriz[x][y].equals(".");
    }

    // Verifica se a posição atacada tem uma unidade do oponente
    public boolean verificarAcerto(int x, int y) {
        for (int[] pos : unidadesDoOponente) {
            if (pos[0] == x && pos[1] == y) {
                matriz[x][y] = "X"; // Marca um acerto
                return true;
            }
        }
        matriz[x][y] = "O"; // Marca um erro
        return false;
    }

    // Retorna o status de uma posição específica no tabuleiro
    public String getPosicaoStatus(int x, int y) {
        return matriz[x][y];
    }

    public void exibirTabuleiro() {
        System.out.println("Tabuleiro:");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
