public class PortaAviao extends Embarcacao {

    public PortaAviao() {
        super(100); // Vida inicial do Porta Avião
    }

    @Override
    public void mirar() {
        System.out.println("PortaAvião mirando no alvo...");
    }

    @Override
    public void atirar() {
        System.out.println("PortaAvião disparando!");
    }

    @Override
    public void levarDano(int dano) {
        this.vida -= dano;
        verificarVida();
    }

    @Override
    public int getDano() {
        return 25; // Dano causado por um tiro de PortaAvião
    }

    @Override
    public boolean morrer() {
        return this.vida <= 0;
    }
}
