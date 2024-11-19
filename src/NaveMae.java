public class NaveMae extends Nave {

    public NaveMae() {
        super(5);
    }

    @Override
    public void atirar(String coordenada, Tabuleiro tabuleiro, boolean acerto) {
        int[] pos = Tabuleiro.converterCoordenada(coordenada);
        int x = pos[0];
        int y = pos[1];

        tabuleiro.marcarAtaque(x, y, acerto);
        if (acerto) {
            System.out.println("Nave-Mãe acertou em " + coordenada + "!");
        } else {
            System.out.println("Nave-Mãe errou em " + coordenada + "...");
        }
        tabuleiro.exibirTabuleiro();
    }

    @Override
    public void levarDano(int dano) {
        this.vida -= dano;
        if (this.vida <= 0) {
            morrer();
        }
        System.out.println("Nave-Mãe sofreu " + dano + " de dano. Vida restante: " + this.vida);
    }

    @Override
    public void naoDano() {
        System.out.println("A Nave-Mãe não foi atingida.");
    }

    @Override
    public void posicionar(int x, int y) {
        this.posicaoX = x;
        this.posicaoY = y;
        System.out.println("Nave-Mãe posicionado em (" + x + ", " + y + ")");
    }

    @Override
    public void morrer() {
        System.out.println("A Nave-Mãe foi destruída!");
    }
}
