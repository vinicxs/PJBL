import java.util.Scanner;

public class Tabuleiro {
    private int[][] matriz;
    private Scanner scanner;

    public Tabuleiro() {
        matriz = new int[10][10];
        scanner = new Scanner(System.in);
    }

    public boolean posicionarVeiculo(int xInicial, int yInicial, int tamanho, boolean horizontal) {

        if (horizontal) {
            if (xInicial + tamanho > 10) {
                return false;
            }
        } else {
            if (yInicial + tamanho > 10) {
                return false;
            }
        }

        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                if (matriz[yInicial][xInicial + i] != 0) {
                    return false;
                }
            } else {
                if (matriz[yInicial + i][xInicial] != 0) {
                    return false;
                }
            }
        }

        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                matriz[yInicial][xInicial + i] = 1;
            } else {
                matriz[yInicial + i][xInicial] = 1;
            }
        }

        return true;
    }

    public void mostrarTabuleiro() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void posicionarUsuario(int tamanho, String nomeVeiculo) {
        boolean posicionado = false;
        while (!posicionado) {
            System.out.println("Posicionando " + nomeVeiculo + " (tamanho " + tamanho + ")");
            System.out.print("Informe a linha inicial (0 a 9): ");
            int linha = scanner.nextInt();
            System.out.print("Informe a coluna inicial (0 a 9): ");
            int coluna = scanner.nextInt();
            System.out.print("Deseja posicionar na horizontal? (true para sim, false para não): ");
            boolean horizontal = scanner.nextBoolean();

            if (posicionarVeiculo(coluna, linha, tamanho, horizontal)) {
                System.out.println(nomeVeiculo + " posicionado com sucesso!");
                posicionado = true;
            } else {
                System.out.println("Posicionamento inválido. Tente novamente.");
            }
        }
    }

    public void posicionarComputador(int tamanho, String nomeVeiculo) {
        boolean posicionado = false;
        while (!posicionado) {
            int linha = (int) (Math.random() * 10);
            int coluna = (int) (Math.random() * 10);
            boolean horizontal = Math.random() < 0.5;

            if (posicionarVeiculo(coluna, linha, tamanho, horizontal)) {
                System.out.println(nomeVeiculo + " posicionado pelo computador.");
                posicionado = true;
            }
        }
    }
}
