package view;

import entidades.Torre;
import jogo.Jogo;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class HUDPanel extends JPanel {
    private Jogo jogo;
    private GamePanel gamePanel;
    private GameWindow gameWindow;

    private JLabel lblMoedas, lblVida, lblOnda;
    private JProgressBar pbVida;
    private JButton btnIniciarOnda;
    private JButton btnRapida, btnPesada, btnFranco;
    private JButton btnUpgrade, btnVender;
    private JLabel lblTorreSel;

    private String tipoSelecionado = null;

    public HUDPanel(Jogo jogo, GamePanel gamePanel, GameWindow gameWindow) {
        this.jogo = jogo;
        this.gamePanel = gamePanel;
        this.gameWindow = gameWindow;
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(30, 25, 40));
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        buildUI();
    }

    private void buildUI() {
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel title = new JLabel("⚔ Defend the Kingdom", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 14));
        title.setForeground(new Color(255, 210, 80));
        title.setAlignmentX(CENTER_ALIGNMENT);
        top.add(title);
        top.add(Box.createVerticalStrut(10));

        top.add(makeStatLabel("💰 Moedas:"));
        lblMoedas = makeValueLabel("0");
        top.add(lblMoedas);
        top.add(Box.createVerticalStrut(4));

        top.add(makeStatLabel("❤ Vida:"));
        pbVida = new JProgressBar(0, 20);
        pbVida.setForeground(new Color(220, 50, 50));
        pbVida.setBackground(new Color(60, 30, 30));
        pbVida.setStringPainted(false);
        pbVida.setMaximumSize(new Dimension(Integer.MAX_VALUE, 14));
        top.add(pbVida);
        lblVida = makeValueLabel("20");
        top.add(lblVida);
        top.add(Box.createVerticalStrut(4));

        top.add(makeStatLabel("🌊 Onda:"));
        lblOnda = makeValueLabel("0 / 8");
        top.add(lblOnda);
        top.add(Box.createVerticalStrut(10));

        btnIniciarOnda = new JButton("▶ Iniciar Onda");
        styleButton(btnIniciarOnda, new Color(60, 140, 60));
        btnIniciarOnda.addActionListener(e -> gameWindow.iniciarOnda());
        top.add(btnIniciarOnda);
        top.add(Box.createVerticalStrut(12));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 70, 100));
        top.add(sep);
        top.add(Box.createVerticalStrut(8));

        JLabel torresTitle = new JLabel("🏰 Colocar Torres", SwingConstants.CENTER);
        torresTitle.setFont(new Font("Arial", Font.BOLD, 12));
        torresTitle.setForeground(new Color(200, 200, 255));
        torresTitle.setAlignmentX(CENTER_ALIGNMENT);
        top.add(torresTitle);
        top.add(Box.createVerticalStrut(6));

        btnRapida = makeTowerButton("Torre Rápida\n70💰 | Dano:12 | Vel:↑↑", "RAPIDA", new Color(70, 160, 220));
        btnPesada = makeTowerButton("Torre Pesada\n330💰 | Dano:40 | Vel:↓", "PESADA", new Color(200, 100, 50));
        btnFranco = makeTowerButton("Franco-Atirador\n850💰 | Dano:70 | Alc:↑↑", "FRANCO", new Color(60, 200, 120));

        top.add(btnRapida);
        top.add(Box.createVerticalStrut(4));
        top.add(btnPesada);
        top.add(Box.createVerticalStrut(4));
        top.add(btnFranco);
        top.add(Box.createVerticalStrut(12));

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(80, 70, 100));
        top.add(sep2);
        top.add(Box.createVerticalStrut(8));

        // Tower info
        lblTorreSel = new JLabel("<html><center>Clique numa torre<br>para ver detalhes</center></html>", SwingConstants.CENTER);
        lblTorreSel.setForeground(new Color(160, 160, 180));
        lblTorreSel.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTorreSel.setAlignmentX(CENTER_ALIGNMENT);
        top.add(lblTorreSel);
        top.add(Box.createVerticalStrut(6));

        btnUpgrade = new JButton("⬆ Upgrade");
        styleButton(btnUpgrade, new Color(120, 80, 180));
        btnUpgrade.setEnabled(false);
        btnUpgrade.addActionListener(e -> gameWindow.upgradesTorreSelecionada());
        top.add(btnUpgrade);
        top.add(Box.createVerticalStrut(4));

        btnVender = new JButton("💸 Vender");
        styleButton(btnVender, new Color(160, 80, 40));
        btnVender.setEnabled(false);
        btnVender.addActionListener(e -> gameWindow.venderTorreSelecionada());
        top.add(btnVender);

        add(top, BorderLayout.NORTH);

        // Bottom: hint + quit button
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        JLabel hint = new JLabel("<html><center>Clique no mapa verde<br>para colocar torre</center></html>", SwingConstants.CENTER);
        hint.setForeground(new Color(120, 120, 140));
        hint.setFont(new Font("Arial", Font.ITALIC, 10));
        hint.setAlignmentX(CENTER_ALIGNMENT);
        bottom.add(hint);

        bottom.add(Box.createVerticalStrut(10));

        JSeparator sepBottom = new JSeparator();
        sepBottom.setForeground(new Color(80, 70, 100));
        bottom.add(sepBottom);

        bottom.add(Box.createVerticalStrut(8));

        JButton btnSair = new JButton("✕  Sair do Jogo");
        styleButton(btnSair, new Color(160, 40, 40));
        btnSair.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(
                    HUDPanel.this,
                    "Tens a certeza que queres sair?",
                    "Sair do Jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) System.exit(0);
        });
        bottom.add(btnSair);

        add(bottom, BorderLayout.SOUTH);
    }

    private JLabel makeStatLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(180, 180, 200));
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JLabel makeValueLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JButton makeTowerButton(String text, String tipo, Color color) {
        String[] parts = text.split("\n");
        JButton btn = new JButton("<html><b>" + parts[0] + "</b><br><small>" + parts[1] + "</small></html>");
        btn.setBackground(color.darker().darker());
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            tipoSelecionado = tipo;
            gamePanel.setTorreSelecionada(null);
            updateTowerButtonStyles(tipo);
        });
        return btn;
    }

    private void updateTowerButtonStyles(String selected) {
        resetButtonStyle(btnRapida, new Color(70, 160, 220), "RAPIDA".equals(selected));
        resetButtonStyle(btnPesada, new Color(200, 100, 50), "PESADA".equals(selected));
        resetButtonStyle(btnFranco, new Color(60, 200, 120), "FRANCO".equals(selected));
    }

    private void resetButtonStyle(JButton btn, Color color, boolean active) {
        if (active) {
            btn.setBackground(color.darker());
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 220, 50), 2),
                    BorderFactory.createEmptyBorder(3, 5, 3, 5)
            ));
        } else {
            btn.setBackground(color.darker().darker());
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 1),
                    BorderFactory.createEmptyBorder(4, 6, 4, 6)
            ));
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void atualizar() {
        lblMoedas.setText(jogo.getJogador().getMoedas() + " 💰");
        int vida = jogo.getJogador().getVida();
        lblVida.setText(vida + " ❤");
        pbVida.setMaximum(20);
        pbVida.setValue(vida);
        lblOnda.setText(jogo.getNumeroOnda() + " / " + jogo.getTotalOndas());

        boolean entreOndas = jogo.getEstado() == Jogo.Estado.ENTRE_ONDAS;
        btnIniciarOnda.setEnabled(entreOndas);
        btnIniciarOnda.setText(entreOndas ? "▶ Iniciar Onda " + (jogo.getNumeroOnda() + 1) : "🌊 Onda em curso...");

        Torre sel = gamePanel.getTorreSelecionada();
        if (sel != null) {
            lblTorreSel.setText("<html><center><b>" + sel.getNome() + "</b> Nv." + sel.getNivel() +
                    "<br>Dano: " + sel.getDano() + " | Cadência: " + sel.getCadencia() +
                    "<br>Alcance: " + (int)sel.getAlcance() +
                    "<br>Upgrade: " + (sel.getNivel() < 3 ? sel.getCustoUpgrade() + "💰" : "MAX") +
                    "</center></html>");
            btnUpgrade.setEnabled(sel.getNivel() < 3 && jogo.getJogador().getMoedas() >= sel.getCustoUpgrade());
            btnVender.setEnabled(true);
        } else {
            lblTorreSel.setText("<html><center>Clique numa torre<br>para ver detalhes</center></html>");
            btnUpgrade.setEnabled(false);
            btnVender.setEnabled(false);
        }
    }

    public String getTipoSelecionado() { return tipoSelecionado; }
    public void clearTipoSelecionado() {
        tipoSelecionado = null;
        updateTowerButtonStyles(null);
    }
}
