import java.util.Scanner;

class Pessoa {
    private int id;
    private boolean turno = true; // Controle do turno do jogador
    private Tabuleiro tabuleiro; // Instância do Tabuleiro

    public Pessoa(int id, Tabuleiro tabuleiro) {
        this.id = id;
        this.tabuleiro = tabuleiro;
    }

    // Método para o jogador posicionar suas unidades no tabuleiro
    public void posiNavios() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Posicione suas unidades (ex: A 1 para linha A, coluna 1):");

        for (int i = 0; i < tabuleiro.getUnidadesDoJogador().size(); i++) {
            System.out.println("Unidade " + (i + 1) + ":");
            String linha = scanner.next(); // Ex: "A"
            int coluna = scanner.nextInt() - 1; // Ex: 1 (convertido para índice 0)

            int x = linha.charAt(0) - 'A'; // Converte a letra para índice de linha (A = 0, B = 1, ...)
            if (x >= 0 && x < 10 && coluna >= 0 && coluna < 10 && tabuleiro.isPosicaoVazia(x, coluna)) {
                tabuleiro.posicionarUnidadeJogador(x, coluna, "U"); // "U" representa unidade do jogador
            } else {
                System.out.println("Posição inválida ou já ocupada. Tente novamente.");
                i--; // Repetir posicionamento para a unidade atual
            }
        }
    }

    // Método para exibir o resultado do tiro
    public void revelResult(int x, int y, boolean acerto) {
        if (acerto) {
            System.out.println("Você acertou em (" + (char) (x + 'A') + ", " + (y + 1) + ")");
        } else {
            System.out.println("Você errou em (" + (char) (x + 'A') + ", " + (y + 1) + ")");
        }
    }

    // Método para alternar o turno
    public void altTurno() {
        turno = !turno; // Alterna o turno
        System.out.println(turno ? "É sua vez!" : "É a vez do oponente.");
    }

    // Método para verificar se o ataque acertou uma unidade
    public boolean verAcerto(int x, int y) {
        return tabuleiro.verificarAcerto(x, y);
    }

    // Conta o número de tiros acertados pelo jogador
    public void contar() {
        int acertos = 0;
        for (int[] pos : tabuleiro.getUnidadesDoJogador()) {
            if (tabuleiro.getPosicaoStatus(pos[0], pos[1]).equals("X")) {
                acertos++;
            }
        }
        System.out.println("Número total de acertos: " + acertos);
    }

    // Método para realizar um ataque
    public void realizarAtaque() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a posição para atacar (ex: A 5):");
        String linha = scanner.next(); // Ex: "A"
        int coluna = scanner.nextInt() - 1; // Ex: 5 (convertido para índice 4)

        int x = linha.charAt(0) - 'A';
        if (x >= 0 && x < 10 && coluna >= 0 && coluna < 10) {
            boolean acerto = verAcerto(x, coluna);
            revelResult(x, coluna, acerto);
            altTurno(); // Alterna o turno após o ataque
        } else {
            System.out.println("Coordenada inválida. Tente novamente.");
        }
    }

    public boolean isTurno() {
        return turno;
    }
}
