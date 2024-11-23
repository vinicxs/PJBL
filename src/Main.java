import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame {
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroComputador;
    private JTextArea displayTabuleiro;
    private JTextArea registroMovimentos;
    private JTextField coordenadaInput;
    private List<String> historicoMovimentos;
    private boolean jogadorAtivo;
    private int unidadesJogadorRestantes;
    private int unidadesComputadorRestantes;

    public Main() {
        tabuleiroJogador = new Tabuleiro();
        tabuleiroComputador = new Tabuleiro();
        historicoMovimentos = new ArrayList<>();
        jogadorAtivo = true;
        unidadesJogadorRestantes = 17;
        unidadesComputadorRestantes = 17;
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Batalha Naval");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        displayTabuleiro = new JTextArea(Tabuleiro.TAMANHO, Tabuleiro.TAMANHO);
        displayTabuleiro.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayTabuleiro.setEditable(false);
        displayTabuleiro.setBorder(new LineBorder(Color.BLACK, 1));

        JScrollPane scrollPane = new JScrollPane(displayTabuleiro);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        coordenadaInput = new JTextField(5);
        JButton atacarButton = new JButton("Atacar");
        atacarButton.addActionListener(e -> realizarAtaqueJogador());

        registroMovimentos = new JTextArea(10, 30);
        registroMovimentos.setEditable(false);
        registroMovimentos.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollRegistro = new JScrollPane(registroMovimentos);

        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Coordenada (ex: A5):"));
        painelEntrada.add(coordenadaInput);
        painelEntrada.add(atacarButton);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.add(scrollPane);
        painelCentral.add(painelEntrada);
        painelCentral.add(scrollRegistro);

        add(painelCentral, BorderLayout.CENTER);

        escolherLadoJogador();
        posicionarUnidadesJogador();
        posicionarUnidadesComputador();
        atualizarDisplayTabuleiro();
    }

    private void escolherLadoJogador() {
        String[] opcoes = {"Naves", "Embarcações"};
        int escolha = JOptionPane.showOptionDialog(
                this,
                "Escolha seu lado:",
                "Seleção de Lado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        String ladoJogador = (escolha == 0) ? "Naves" : "Embarcações";
        String ladoComputador = ladoJogador.equals("Naves") ? "Embarcações" : "Naves";

        JOptionPane.showMessageDialog(this,
                "Você escolheu: " + ladoJogador + "\nComputador escolheu: " + ladoComputador);
    }

    private void posicionarUnidadesJogador() {
        String[] unidades = {"Porta-Aviões", "Contra-Torpedeiro (x3)", "Navio-Tanque (x2)"};
        int[] tamanhos = {5, 4, 3};

        for (int i = 0; i < unidades.length; i++) {
            int quantidade = unidades[i].contains("(x3)") ? 3 : unidades[i].contains("(x2)") ? 2 : 1;

            for (int j = 0; j < quantidade; j++) {
                boolean posicionado = false;

                while (!posicionado) {
                    String posicao = JOptionPane.showInputDialog(
                            this,
                            "Digite a posição inicial para " + unidades[i] + " #" + (j + 1) + " (ex: A5):");
                    if (posicao != null) {
                        int[] coords = Tabuleiro.converterCoordenada(posicao);
                        boolean horizontal = JOptionPane.showConfirmDialog(
                                this,
                                "Deseja posicionar " + unidades[i] + " horizontalmente?",
                                "Posicionamento",
                                JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION;

                        posicionado = tabuleiroJogador.posicionarPeca(coords[0], coords[1], tamanhos[i], horizontal);
                        if (!posicionado) {
                            JOptionPane.showMessageDialog(this, "Posição inválida ou já ocupada. Tente novamente.");
                        }
                    }
                }
                atualizarDisplayTabuleiro();
            }
        }
    }

    private void posicionarUnidadesComputador () {
        Random rand = new Random();
        String[] unidades = {"Porta-Aviões", "Contra-Torpedeiro (x3)", "Navio-Tanque (x2)"};
        int[] tamanhos = {5, 4, 3};

        for (int i = 0; i < unidades.length; i++) {
            int quantidade = unidades[i].contains("(x3)") ? 3 : unidades[i].contains("(x2)") ? 2 : 1;

            for (int j = 0; j < quantidade; j++) {
                boolean posicionado = false;

                while (!posicionado) {
                    int x = rand.nextInt(Tabuleiro.TAMANHO);
                    int y = rand.nextInt(Tabuleiro.TAMANHO);
                    boolean horizontal = rand.nextBoolean();

                    posicionado = tabuleiroComputador.posicionarPeca(x, y, tamanhos[i], horizontal);
                }
            }
        }
    }

    private void realizarAtaqueJogador() {
        if (!jogadorAtivo) {
            JOptionPane.showMessageDialog(this, "Aguarde sua vez!");
            return;
        }

        String coordenada = coordenadaInput.getText().toUpperCase();
        int[] pos = Tabuleiro.converterCoordenada(coordenada);

        if (!tabuleiroComputador.coordenadaValida(pos[0], pos[1])) {
            JOptionPane.showMessageDialog(this, "Coordenada inválida. Tente novamente.");
            return;
        }

        boolean acerto = tabuleiroComputador.getGrid()[pos[0]][pos[1]] == 'P';
        tabuleiroComputador.marcarAtaque(pos[0], pos[1], acerto);

        String resultado = acerto ? "Acertou!" : "Errou!";
        if (acerto) unidadesComputadorRestantes--;

        registrarMovimento("Jogador", coordenada, resultado);
        verificarFimDeJogo();
        jogadorAtivo = false;
        realizarAtaqueMaquina();
    }

    private void realizarAtaqueMaquina() {
        Random rand = new Random();
        boolean acerto;
        int x, y;

        do {
            x = rand.nextInt(Tabuleiro.TAMANHO);
            y = rand.nextInt(Tabuleiro.TAMANHO);
        } while (tabuleiroJogador.getGrid()[x][y] == 'X' || tabuleiroJogador.getGrid()[x][y] == 'O');

        acerto = tabuleiroJogador.getGrid()[x][y] == 'P';
        tabuleiroJogador.marcarAtaque(x, y, acerto);

        String coordenada = String.valueOf((char) ('A' + x)) + (y + 1);
        String resultado = acerto ? "Acertou!" : "Errou!";
        if (acerto) unidadesJogadorRestantes--;

        registrarMovimento("Maquina", coordenada, resultado);
        verificarFimDeJogo();
        jogadorAtivo = true;
        atualizarDisplayTabuleiro();
    }

    private void registrarMovimento(String atacante, String coordenada, String resultado) {
        String movimento = atacante + "," + coordenada + "," + resultado;
        historicoMovimentos.add(movimento);
        registroMovimentos.append(movimento + "\n");
    }

    private void verificarFimDeJogo() {
        if (unidadesJogadorRestantes == 0) {
            JOptionPane.showMessageDialog(this, "Você perdeu! Todas as suas unidades foram destruídas.");
            salvarMovimentosCSV();
            System.exit(0);
        }

        if (unidadesComputadorRestantes == 0) {
            JOptionPane.showMessageDialog(this, "Você venceu! Todas as unidades inimigas foram destruídas.");
            salvarMovimentosCSV();
            System.exit(0);
        }
    }

    private void salvarMovimentosCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_movimentos.csv"))) {
            writer.write("Atacante,Coordenada,Resultado\n");
            for (String movimento : historicoMovimentos) {
                writer.write(movimento + "\n");
            }
            JOptionPane.showMessageDialog(this, "Movimentos salvos em historico_movimentos.csv.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o histórico de movimentos.");
            e.printStackTrace();
        }
    }


    private void atualizarDisplayTabuleiro() {
        displayTabuleiro.setText("");
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                displayTabuleiro.append(tabuleiroJogador.getGrid()[i][j] + " ");
            }
            displayTabuleiro.append("\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

