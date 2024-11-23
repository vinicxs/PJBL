public class ContraTorpedeiro extends Embarcacao {

    public ContraTorpedeiro() {
        super(3);
    }

    @Override
    public void atirar(String coordenada, Tabuleiro tabuleiro, boolean acerto) {
        int[] pos = Tabuleiro.converterCoordenada(coordenada);
        int x = pos[0];
        int y = pos[1];

        tabuleiro.marcarAtaque(x, y, acerto);
        if (acerto) {
            System.out.println("ContraTorpedeiro acertou em " + coordenada + "!");
        } else {
            System.out.println("ContraTorpedeiro errou em " + coordenada + "...");
        }
        tabuleiro.exibirTabuleiro();
    }

    @Override
    public void levarDano(int dano) {
        this.vida -= dano;
        if (this.vida <= 0) {
            morrer();
        }
        System.out.println("ContraTorpedeiro sofreu " + dano + " de dano. Vida restante: " + this.vida);
    }

    @Override
    public void naoDano() {
        System.out.println("O ContraTorpedeiro não foi atingido.");
    }

    @Override
    public void posicionar(int x, int y) {
        this.posicaoX = x;
        this.posicaoY = y;
        System.out.println("ContraTorpedeiro posicionado em (" + x + ", " + y + ")");
    }

    @Override
    public void morrer() {
        System.out.println("O ContraTorpedeiro foi destruído!");
    }
}
