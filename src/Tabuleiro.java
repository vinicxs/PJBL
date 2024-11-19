import java.util.Random;

public class Tabuleiro {
    public static final int TAMANHO = 10; // Tamanho do tabuleiro
    public char[][] grid;

    public Tabuleiro() {
        grid = new char[TAMANHO][TAMANHO];
        inicializarTabuleiro();
    }

    // Inicializa o tabuleiro, preenchendo com '.' para indicar posições vazias
    private void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grid[i][j] = '.'; // Indica que a posição está vazia
            }
        }
    }

    // Verifica se as coordenadas estão dentro do limite do tabuleiro
    public boolean coordenadaValida(int x, int y) {
        return x >= 0 && x < TAMANHO && y >= 0 && y < TAMANHO;
    }

    // Converte coordenadas em texto ("A5") para índices da matriz
    public static int[] converterCoordenada(String coordenada) {
        int x = coordenada.toUpperCase().charAt(0) - 'A'; // Converte a letra em índice (0-9)
        int y = Integer.parseInt(coordenada.substring(1)) - 1; // Converte o número em índice (0-9)
        return new int[]{x, y};
    }

    // Método para posicionar uma peça com validação de limites e sobreposição
    public boolean posicionarPeca(int x, int y, int tamanho, boolean horizontal) {
        // Verifica se a peça cabe no tabuleiro sem ultrapassar os limites e se as posições estão livres
        for (int i = 0; i < tamanho; i++) {
            int posX = horizontal ? x : x + i;
            int posY = horizontal ? y + i : y;

            // Verifica se as coordenadas estão dentro do tabuleiro e se a posição está livre
            if (!coordenadaValida(posX, posY) || grid[posX][posY] != '.') {
                return false; // Posição inválida ou ocupada, retorna false
            }
        }

        // Posiciona a peça se todas as condições forem atendidas
        for (int i = 0; i < tamanho; i++) {
            int posX = horizontal ? x : x + i;
            int posY = horizontal ? y + i : y;
            grid[posX][posY] = 'P'; // Marca a posição da peça no tabuleiro
        }
        return true; // Posicionamento bem-sucedido
    }

    // Marca uma posição no tabuleiro para um ataque (X para acerto, O para erro)
    public void marcarAtaque(int x, int y, boolean acerto) {
        if (coordenadaValida(x, y)) {
            grid[x][y] = acerto ? 'X' : 'O';
        }
    }

    // Exibe o tabuleiro no console (para depuração e testes)
    public void exibirTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Posicionamento aleatório para a máquina (exemplo de uma implementação simples)
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

    // Getter para o grid do tabuleiro
    public char[][] getGrid() {
        return grid;
    }
}
