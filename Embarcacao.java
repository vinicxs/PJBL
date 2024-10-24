public abstract class Embarcacao {
    protected int vida;

    public Embarcacao(int vidaInicial) {
        this.vida = vidaInicial;
    }

    // Métodos abstratos
    public abstract void mirar();
    public abstract void atirar();
    public abstract void levarDano(int dano);
    public abstract int getDano();
    public abstract boolean morrer();

    protected void verificarVida() {
        if (vida <= 0) {
            vida = 0;
        }
    }
}
