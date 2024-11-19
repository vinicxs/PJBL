import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame {
    private Tabuleiro tabuleiro;
    private JTextArea displayTabuleiro;
    private JTextField coordenadaInput;
    private JTextArea registroMovimentos;
    private String ladoJogador;
    private List<String> movimentos;
    private boolean jogadorAtivo;
    private int danoJogador;
    private int danoMaquina;

    public Main() {
        tabuleiro = new Tabuleiro();
        movimentos = new ArrayList<>();
        jogadorAtivo = true;
        danoJogador = 0;
        danoMaquina = 0;
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Batalha Naval");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Escolher lado e posicionar unidades
        escolherLadoJogador();
        posicionarUnidadesJogador();

        // Área de texto para exibir o tabuleiro com borda e centralização
        displayTabuleiro = new JTextArea(Tabuleiro.TAMANHO, Tabuleiro.TAMANHO);
        displayTabuleiro.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayTabuleiro.setEditable(false);
        displayTabuleiro.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPane = new JScrollPane(displayTabuleiro);

        // Centralizar o scrollPane no painel
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo de entrada para coordenadas e botão de ataque
        coordenadaInput = new JTextField(5);
        JButton atacarButton = new JButton("Atacar");

        atacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jogadorAtivo) {
                    String coordenada = coordenadaInput.getText().toUpperCase();
                    int[] pos = Tabuleiro.converterCoordenada(coordenada);

                    if (tabuleiro.coordenadaValida(pos[0], pos[1])) {
                        boolean acerto = Math.random() > 0.5;
                        tabuleiro.marcarAtaque(pos[0], pos[1], acerto);
                        String resultado = acerto ? "Acertou!" : "Errou!";
                        registroMovimentos.append("Jogador atacou " + coordenada + ": " + resultado + "\n");

                        movimentos.add("Jogador atacou " + coordenada + ": " + resultado);
                        if (acerto) danoJogador++; // Incrementa dano causado pelo jogador
                        atualizarDisplayTabuleiro();

                        jogadorAtivo = false;
                        realizarAtaqueMaquina();
                    } else {
                        JOptionPane.showMessageDialog(null, "Coordenada inválida. Tente novamente.");
                    }
                    coordenadaInput.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Aguarde sua vez!");
                }
            }
        });

        // Painel de entrada para coordenadas e botão de ataque
        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Coordenada (ex: A5): "));
        painelEntrada.add(coordenadaInput);
        painelEntrada.add(atacarButton);

        // Área para exibir o registro de movimentos com borda
        registroMovimentos = new JTextArea(5, 20);
        registroMovimentos.setEditable(false);
        registroMovimentos.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollRegistro = new JScrollPane(registroMovimentos);

        // Organiza os componentes em um layout centralizado
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.add(scrollPane);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(painelEntrada);
        painelCentral.add(scrollRegistro);

        add(painelCentral, BorderLayout.CENTER);

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

        ladoJogador = (escolha == 0) ? "Naves" : "Embarcações";
        String ladoComputador = ladoJogador.equals("Naves") ? "Embarcações" : "Naves";
        System.out.println("Computador ficará com o lado: " + ladoComputador);
    }

    private void posicionarUnidadesJogador() {
        String[] unidades = {
                "Porta-Aviões", "Nave-Mãe", "Contra-Torpedeiro (x3)", "Nave-Exploradora (x3)",
                "Navio-Tanque (x2)", "Nave-Tanque (x2)"
        };

        for (String unidade : unidades) {
            int quantidade = unidade.contains("(x3)") ? 3 : unidade.contains("(x2)") ? 2 : 1;

            for (int i = 0; i < quantidade; i++) {
                String posicao = JOptionPane.showInputDialog(this,
                        "Digite a posição inicial para " + unidade + " #" + (i + 1) + " (ex: A5):");
                if (posicao != null) {
                    int[] coords = Tabuleiro.converterCoordenada(posicao);
                    boolean horizontal = JOptionPane.showConfirmDialog(this,
                            "Deseja posicionar " + unidade + " horizontalmente?",
                            "Posicionamento", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                    int tamanho = switch (unidade) {
                        case "Porta-Aviões", "Nave-Mãe" -> 5;
                        case "Contra-Torpedeiro (x3)", "Nave-Exploradora (x3)" -> 4;
                        case "Navio-Tanque (x2)", "Nave-Tanque (x2)" -> 3;
                        default -> 1;
                    };

                    if (!tabuleiro.posicionarPeca(coords[0], coords[1], tamanho, horizontal)) {
                        JOptionPane.showMessageDialog(this,
                                "Posição inválida ou peça já ocupada. Tente novamente.");
                        i--;
                    } else {
                        atualizarDisplayTabuleiro();
                    }
                }
            }
        }
    }

    private void realizarAtaqueMaquina() {
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random rand = new Random();
            int x, y;
            do {
                x = rand.nextInt(Tabuleiro.TAMANHO);
                y = rand.nextInt(Tabuleiro.TAMANHO);
            } while (!tabuleiro.coordenadaValida(x, y));

            boolean acerto = Math.random() > 0.5;
            tabuleiro.marcarAtaque(x, y, acerto);
            String coordenadaMaquina = String.valueOf((char) ('A' + x)) + (y + 1);
            String resultado = acerto ? "Acertou!" : "Errou!";
            registroMovimentos.append("Máquina atacou " + coordenadaMaquina + ": " + resultado + "\n");

            movimentos.add("Máquina atacou " + coordenadaMaquina + ": " + resultado);
            if (acerto) danoMaquina++;
            atualizarDisplayTabuleiro();
            jogadorAtivo = true;
        });
    }

    private void atualizarDisplayTabuleiro() {
        displayTabuleiro.setText("");
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                displayTabuleiro.append(tabuleiro.grid[i][j] + " ");
            }
            displayTabuleiro.append("\n");
        }
    }

    private void salvarMovimentos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorio_final.txt"))) {
            writer.write("Relatório de Movimentos:\n");
            for (String movimento : movimentos) {
                writer.write(movimento);
                writer.newLine();
            }
            writer.write("\nTotal de danos:\n");
            writer.write("Danos causados pelo jogador: " + danoJogador + "\n");
            writer.write("Danos causados pela máquina: " + danoMaquina + "\n");

            JOptionPane.showMessageDialog(this, "Relatório final salvo como relatorio_final.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o relatório final.");
            e.printStackTrace();
        }
    }

    // Método para finalizar o jogo e salvar o relatório final automaticamente
    private void finalizarJogo() {
        salvarMovimentos();
        JOptionPane.showMessageDialog(this, "O jogo terminou! Relatório final salvo como relatorio_final.txt.");
        System.exit(0); // Fecha o programa após o término do jogo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

