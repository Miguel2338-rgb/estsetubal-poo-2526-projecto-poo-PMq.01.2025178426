package view;

import entidades.Torre;
import jogo.Jogo;
import jogo.Mapa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private Jogo jogo;
    private GamePanel gamePanel;
    private HUDPanel hudPanel;
    private Timer gameTimer;
    private JScrollPane scrollPane;

    public GameWindow() {
        super("⚔ Defend the Kingdom");
        jogo = new Jogo();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        buildUI();
        setupMouseInput();
        setupGameLoop();

        pack();
        setLocationRelativeTo(null);

        showMenu();
    }

    private void buildUI() {
        gamePanel = new GamePanel(jogo);
        hudPanel = new HUDPanel(jogo, gamePanel, this);

        scrollPane = new JScrollPane(gamePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(900, 728));

        JPanel main = new JPanel(new BorderLayout());
        main.add(scrollPane, BorderLayout.CENTER);
        main.add(hudPanel, BorderLayout.EAST);

        setContentPane(main);
    }

    private void setupMouseInput() {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jogo.getEstado() == Jogo.Estado.MENU ||
                        jogo.getEstado() == Jogo.Estado.VITORIA ||
                        jogo.getEstado() == Jogo.Estado.DERROTA) return;

                int col = e.getX() / Mapa.CELL_SIZE;
                int row = e.getY() / Mapa.CELL_SIZE;

                if (col < 0 || col >= Mapa.COLS || row < 0 || row >= Mapa.ROWS) return;

                // Check if clicking on existing tower
                Torre t = jogo.getTorreNaPosicao(col, row);
                if (t != null) {
                    gamePanel.setTorreSelecionada(t);
                    hudPanel.clearTipoSelecionado();
                    hudPanel.atualizar();
                    return;
                }

                // Place tower
                String tipo = hudPanel.getTipoSelecionado();
                if (tipo != null) {
                    boolean ok = jogo.colocarTorre(col, row, tipo);
                    if (!ok) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    hudPanel.atualizar();
                } else {
                    // Deselect
                    gamePanel.setTorreSelecionada(null);
                    hudPanel.atualizar();
                }
            }
        });
    }

    private void setupGameLoop() {
        gameTimer = new Timer(33, e -> {  // ~30 FPS
            if (jogo.getEstado() == Jogo.Estado.JOGANDO) {
                jogo.tick();
                checkEndConditions();
            }
            gamePanel.repaint();
            hudPanel.atualizar();
        });
    }

    private void checkEndConditions() {
        if (jogo.getEstado() == Jogo.Estado.VITORIA) {
            gameTimer.stop();
            showEndScreen(true);
        } else if (jogo.getEstado() == Jogo.Estado.DERROTA) {
            gameTimer.stop();
            showEndScreen(false);
        }
    }

    public void iniciarOnda() {
        if (jogo.getEstado() == Jogo.Estado.ENTRE_ONDAS) {
            jogo.iniciarProximaOnda();
            hudPanel.atualizar();
        }
    }

    public void upgradesTorreSelecionada() {
        Torre sel = gamePanel.getTorreSelecionada();
        if (sel != null) {
            jogo.upgradesTorre(sel);
            hudPanel.atualizar();
        }
    }

    public void venderTorreSelecionada() {
        Torre sel = gamePanel.getTorreSelecionada();
        if (sel != null) {
            jogo.removerTorre(sel);
            gamePanel.setTorreSelecionada(null);
            hudPanel.atualizar();
        }
    }

    private void showMenu() {
        gameTimer.stop();
        JPanel menu = createOverlayPanel("⚔ DEFEND THE KINGDOM ⚔",
                "<html><center>Protege o reino das hordas inimigas!<br>" +
                        "Coloca torres no mapa verde, derrota os inimigos,<br>" +
                        "ganha moedas e sobrevive a todas as ondas.<br><br>" +
                        "<b>Torres disponíveis:</b><br>" +
                        "🔵 Torre Rápida (70💰) - Velocidade alta<br>" +
                        "🟠 Torre Pesada (330💰) - Dano alto<br>" +
                        "🟢 Franco-Atirador (850💰) - Longo alcance</center></html>",
                "▶ Começar Jogo", new Color(60, 140, 60));

        showOverlay(menu, () -> {
            jogo.iniciar();
            gameTimer.start();
            hudPanel.atualizar();
        });
    }

    private void showEndScreen(boolean vitoria) {
        String titulo = vitoria ? "🏆 VITÓRIA! 🏆" : "💀 DERROTA 💀";
        String msg = vitoria
                ? "<html><center>Parabéns! O Reino foi defendido!<br>Sobreviveste a todas as ondas!</center></html>"
                : "<html><center>O Reino caiu...<br>Os inimigos chegaram à base.</center></html>";
        Color btnColor = vitoria ? new Color(60, 140, 60) : new Color(160, 50, 50);
        String btnText = vitoria ? "🏆 Jogar Novamente" : "🔄 Tentar Novamente";

        JPanel overlay = createOverlayPanel(titulo, msg, btnText, btnColor);
        showOverlay(overlay, () -> {
            jogo.iniciar();
            gameTimer.start();
            hudPanel.clearTipoSelecionado();
            gamePanel.setTorreSelecionada(null);
            hudPanel.atualizar();
        });
    }

    private JPanel createOverlayPanel(String titulo, String msg, String btnText, Color btnColor) {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(new Color(25, 20, 40, 230));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 140, 60), 2),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 210, 80));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        box.add(lblTitulo);
        box.add(Box.createVerticalStrut(16));

        JLabel lblMsg = new JLabel(msg, SwingConstants.CENTER);
        lblMsg.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMsg.setForeground(new Color(220, 220, 230));
        lblMsg.setAlignmentX(CENTER_ALIGNMENT);
        box.add(lblMsg);
        box.add(Box.createVerticalStrut(24));

        JButton btn = new JButton(btnText);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(btnColor);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 44));
        btn.setMaximumSize(new Dimension(200, 44));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(btn);

        panel.add(box);
        panel.putClientProperty("startBtn", btn);
        return panel;
    }

    private void showOverlay(JPanel overlay, Runnable onStart) {
        JLayeredPane layered = getLayeredPane();
        overlay.setBounds(0, 0, getWidth() - 200, getHeight());
        layered.add(overlay, JLayeredPane.POPUP_LAYER);
        layered.revalidate();
        layered.repaint();

        JButton btn = (JButton) overlay.getClientProperty("startBtn");
        if (btn != null) {
            btn.addActionListener(e -> {
                layered.remove(overlay);
                layered.revalidate();
                layered.repaint();
                onStart.run();
            });
        }
    }
}