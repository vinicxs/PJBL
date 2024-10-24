public class Contratorpedeiro extends Embarcacao {

    public Contratorpedeiro() {
        super(50); // Vida inicial do Contratorpedeiro
    }

    @Override
    public void mirar() {
        System.out.println("Contratorpedeiro mirando no alvo...");
    }

    @Override
    public void atirar() {
        System.out.println("Contratorpedeiro disparando!");
    }

    @Override
    public void levarDano(int dano) {
        this.vida -= dano;
        verificarVida();
    }

    @Override
    public int getDano() {
        return 10; // Dano causado por um tiro de Contratorpedeiro
    }

    @Override
    public boolean morrer() {
        return this.vida <= 0;
    }
}
