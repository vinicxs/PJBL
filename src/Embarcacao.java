import java.util.ArrayList;

abstract class Embarcacao {
    protected String nome;
    protected int tamanho;
    protected ArrayList<int[]> posicao;
    protected boolean status = true;

    public Embarcacao(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.posicao = new ArrayList<>();
    }

    public abstract void mirar(int x, int y);
    public abstract void atirar(int x, int y, Tabuleiro tabuleiro);
    public void levardano(int x, int y) {
        // Código para marcar uma posição atingida
        posicao.removeIf(pos -> pos[0] == x && pos[1] == y);
    }
    public void ndano() {
        // Verifica se a embarcação está destruída
        if (posicao.isEmpty()) {
            morrer();
        }
    }
    public void morrer() {
        // Define o status como destruído
        status = false;
        System.out.println(nome + " foi destruído.");
    }

    public String getNome() { return nome; }
    public int getTamanho() { return tamanho; }
    public ArrayList<int[]> getPosicao() { return posicao; }
    public boolean isStatus() { return status; }
}
