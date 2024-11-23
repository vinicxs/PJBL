import java.util.Random;

public class Tabuleiro {
    public static final int TAMANHO = 10; // Tamanho do tabuleiro
    private char[][] grid;

    public Tabuleiro() {
        grid = new char[TAMANHO][TAMANHO];
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grid[i][j] = '.'; // Indica que a posição está vazia
            }
        }
    }

    public boolean coordenadaValida(int x, int y) {
        return x >= 0 && x < TAMANHO && y >= 0 && y < TAMANHO;
    }

    public static int[] converterCoordenada(String coordenada) {
        int x = coordenada.toUpperCase().charAt(0) - 'A';
        int y = Integer.parseInt(coordenada.substring(1)) - 1;
        return new int[]{x, y};
    }

    public boolean posicionarPeca(int x, int y, int tamanho, boolean horizontal) {
        for (int i = 0; i < tamanho; i++) {
            int posX = horizontal ? x : x + i;
            int posY = horizontal ? y + i : y;

            if (!coordenadaValida(posX, posY) || grid[posX][posY] != '.') {
                return false;
            }
        }

        for (int i = 0; i < tamanho; i++) {
            int posX = horizontal ? x : x + i;
            int posY = horizontal ? y + i : y;
            grid[posX][posY] = 'P';
        }
        return true;
    }

    public void marcarAtaque(int x, int y, boolean acerto) {
        if (coordenadaValida(x, y)) {
            grid[x][y] = acerto ? 'X' : 'O';
        }
    }

    public void posicionarPecaAleatoria(int tamanho) {
        Random rand = new Random();
        boolean posicionado = false;
        while (!posicionado) {
            int x = rand.nextInt(TAMANHO);
            int y = rand.nextInt(TAMANHO);
            boolean horizontal = rand.nextBoolean();
            posicionado = posicionarPeca(x, y, tamanho, horizontal);
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    public void exibirTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
