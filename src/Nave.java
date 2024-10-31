import java.util.ArrayList;


abstract class Nave {
    protected String nome;
    protected int tamanho;
    protected ArrayList<int[]> posicao;
    protected boolean status = true;

    public Nave(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.posicao = new ArrayList<>();
    }

    public abstract void mirar(int x, int y);
    public abstract void atirar(int x, int y, Tabuleiro tabuleiro);
    public void levardano(int x, int y) {
        posicao.removeIf(pos -> pos[0] == x && pos[1] == y);
    }
    public void ndano() {
        if (posicao.isEmpty()) {
            morrer();
        }
    }
    public void morrer() {
        status = false;
        System.out.println(nome + " foi destruída.");
    }
}
