public abstract class Embarcacao {
    protected int vida;
    protected int posicaoX;
    protected int posicaoY;

    public Embarcacao(int vida) {
        this.vida = vida;
    }

    public abstract void atirar(String coordenada, Tabuleiro tabuleiro, boolean acerto);

    public abstract void levarDano(int dano);

    public abstract void naoDano();

    public abstract void posicionar(int x, int y);

    public abstract void morrer();

    public int getVida() {
        return vida;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }
}
