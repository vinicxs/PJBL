class Oponente {
    private String nome;
    private int pontosVida;

    public Oponente(String nome, int pontosVida) {
        this.nome = nome;
        this.pontosVida = pontosVida;
    }

    public void posiNavios(Tabuleiro tabuleiro) {
        // Lógica para posicionar navios
    }

    public void revelResult(int x, int y, boolean acerto) {
        if (acerto) {
            System.out.println("Oponente acertou em (" + x + ", " + y + ")");
        } else {
            System.out.println("Oponente errou em (" + x + ", " + y + ")");
        }
    }

    public void altTurno() {
        // Alteração de turno
    }

    public boolean verAcerto(int x, int y) {
        // Verifica se o tiro foi um acerto
        return false; // Implementação de exemplo
    }

    public void contar() {
        // Conta o número de tiros acertados
    }
}
