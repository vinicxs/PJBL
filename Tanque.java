public class Tanque extends Embarcacao {

    public Tanque() {
        super(75); // Vida inicial do Tanque
    }

    @Override
    public void mirar() {
        System.out.println("Tanque mirando no alvo...");
    }

    @Override
    public void atirar() {
        System.out.println("Tanque disparando!");
    }

    @Override
    public void levarDano(int dano) {
        this.vida -= dano;
        verificarVida();
    }

    @Override
    public int getDano() {
        return 15; // Dano causado por um tiro de Tanque
    }

    @Override
    public boolean morrer() {
        return this.vida <= 0;
    }
}
