package view;

import entidades.*;
import jogo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import utils.Posicao;

public class GamePanel extends JPanel {
    private Jogo jogo;
    private Torre torreSelecionada;

    // Grass and path colors
    private static final Color GRASS       = new Color(80, 140, 60);
    private static final Color GRASS_DARK  = new Color(65, 120, 45);
    private static final Color PATH_COLOR  = new Color(170, 140, 90);
    private static final Color PATH_DARK   = new Color(145, 118, 72);
    private static final Color BASE_COLOR  = new Color(180, 50, 50);
    private static final Color START_COLOR = new Color(50, 120, 200);

    public GamePanel(Jogo jogo) {
        this.jogo = jogo;
        setPreferredSize(new Dimension(jogo.getMapa().getPixelWidth(), jogo.getMapa().getPixelHeight()));
        setBackground(GRASS);
    }

    public void setTorreSelecionada(Torre t) { torreSelecionada = t; }
    public Torre getTorreSelecionada() { return torreSelecionada; }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g);
        drawTowers(g);
        drawEnemies(g);
        drawProjectiles(g);
    }

    private void drawGrid(Graphics2D g) {
        Mapa mapa = jogo.getMapa();
        int[][] grid = mapa.getGrid();
        int cs = Mapa.CELL_SIZE;

        for (int row = 0; row < Mapa.ROWS; row++) {
            for (int col = 0; col < Mapa.COLS; col++) {
                int px = col * cs, py = row * cs;
                boolean checker = (row + col) % 2 == 0;

                switch (grid[row][col]) {
                    case 0 -> { // Grass
                        g.setColor(checker ? GRASS : GRASS_DARK);
                        g.fillRect(px, py, cs, cs);
                    }
                    case 1 -> { // Path
                        g.setColor(checker ? PATH_COLOR : PATH_DARK);
                        g.fillRect(px, py, cs, cs);
                        // Path details
                        g.setColor(new Color(0, 0, 0, 20));
                        g.drawRect(px, py, cs, cs);
                    }
                    case 2 -> { // Base
                        g.setColor(checker ? PATH_COLOR : PATH_DARK);
                        g.fillRect(px, py, cs, cs);
                        // Castle icon
                        drawBase(g, px, py, cs);
                    }
                }
            }
        }

        // Draw start marker
        List<Point> caminho = mapa.getCaminho();
        if (!caminho.isEmpty()) {
            Point start = caminho.get(0);
            int px = start.x * cs, py = start.y * cs;
            g.setColor(START_COLOR);
            g.fillRoundRect(px + 4, py + 4, cs - 8, cs - 8, 8, 8);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString("IN", px + cs/2 - 8, py + cs/2 + 4);
        }
    }

    private void drawBase(Graphics2D g, int px, int py, int cs) {
        g.setColor(BASE_COLOR);
        g.fillRoundRect(px + 4, py + 4, cs - 8, cs - 8, 6, 6);
        // Battlements
        g.setColor(new Color(150, 40, 40));
        for (int i = 0; i < 3; i++) {
            g.fillRect(px + 6 + i * 9, py + 5, 6, 6);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9));
        g.drawString("BASE", px + cs/2 - 14, py + cs/2 + 4);
    }

    private void drawTowers(Graphics2D g) {
        for (Torre t : jogo.getTorres()) {
            t.draw(g, t == torreSelecionada);
        }
    }

    private void drawEnemies(Graphics2D g) {
        for (Inimigo e : jogo.getInimigosAtivos()) {
            if (e.estaMorto() || e.chegouABase()) continue;

            int ex = (int) e.getX();
            int ey = (int) e.getY();
            int r = e.getRaio();

            // Shadow
            g.setColor(new Color(0, 0, 0, 60));
            g.fillOval(ex - r + 2, ey - r + 2, r * 2, r * 2);

            // Body
            g.setColor(e.getCor());
            g.fillOval(ex - r, ey - r, r * 2, r * 2);

            // Outline
            g.setColor(e.getCor().darker());
            g.setStroke(new BasicStroke(1.5f));
            g.drawOval(ex - r, ey - r, r * 2, r * 2);
            g.setStroke(new BasicStroke(1f));

            // HP bar
            int barW = r * 2 + 4;
            int barH = 4;
            int barX = ex - barW / 2;
            int barY = ey - r - 7;
            g.setColor(new Color(200, 0, 0));
            g.fillRoundRect(barX, barY, barW, barH, 2, 2);
            int hpW = (int)(barW * ((double)e.getVidaAtual() / e.getVidaMax()));
            g.setColor(new Color(0, 220, 0));
            g.fillRoundRect(barX, barY, hpW, barH, 2, 2);
            g.setColor(Color.BLACK);
            g.drawRoundRect(barX, barY, barW, barH, 2, 2);
        }
    }

    private void drawProjectiles(Graphics2D g) {
        for (Projetil p : jogo.getProjeteis()) {
            g.setColor(p.getCor().brighter());
            g.fillOval((int)p.getX() - 3, (int)p.getY() - 3, 6, 6);
            g.setColor(Color.WHITE);
            g.fillOval((int)p.getX() - 1, (int)p.getY() - 1, 2, 2);
        }
    }
}
