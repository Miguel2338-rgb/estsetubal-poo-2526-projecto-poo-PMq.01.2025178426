package entidades;

import jogo.Mapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InimigoTest {

    private List<Point> caminho;

    @BeforeEach
    void setUp() {
        // Caminho simples: 3 células horizontais
        caminho = new ArrayList<>();
        caminho.add(new Point(0, 0));
        caminho.add(new Point(1, 0));
        caminho.add(new Point(2, 0));
    }

    // ── InimigoRapido ──────────────────────────────────────────────
    @Test
    void rapidoIniciaComValoresCorretos() {
        InimigoRapido r = new InimigoRapido(caminho);
        assertEquals(70, r.getVidaMax());
        assertEquals(70, r.getVidaAtual());
        assertEquals(12, r.getRecompensa());
        assertEquals(1, r.getDanoBase());
        assertEquals("Goblin", r.getNome());
        assertEquals(10, r.getRaio());
        assertFalse(r.estaMorto());
        assertFalse(r.chegouABase());
    }

    @Test
    void rapidoPosicaoInicialNoPrimeiroCelula() {
        InimigoRapido r = new InimigoRapido(caminho);
        double esperadoX = 0 * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        double esperadoY = 0 * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        assertEquals(esperadoX, r.getX(), 1e-9);
        assertEquals(esperadoY, r.getY(), 1e-9);
    }

    // ── InimigoLento ──────────────────────────────────────────────
    @Test
    void lentoIniciaComValoresCorretos() {
        InimigoLento l = new InimigoLento(caminho);
        assertEquals(180, l.getVidaMax());
        assertEquals(180, l.getVidaAtual());
        assertEquals(26, l.getRecompensa());
        assertEquals(2, l.getDanoBase());
        assertEquals("Golem", l.getNome());
        assertEquals(14, l.getRaio());
    }

    // ── InimigoBoss ───────────────────────────────────────────────
    @Test
    void bossIniciaComValoresCorretos() {
        InimigoBoss b = new InimigoBoss(caminho);
        assertEquals(700, b.getVidaMax());
        assertEquals(700, b.getVidaAtual());
        assertEquals(90, b.getRecompensa());
        assertEquals(5, b.getDanoBase());
        assertEquals("Boss", b.getNome());
        assertEquals(20, b.getRaio());
    }

    // ── receberDano ───────────────────────────────────────────────
    @Test
    void receberDanoParcial() {
        InimigoRapido r = new InimigoRapido(caminho);
        r.receberDano(30);
        assertEquals(40, r.getVidaAtual());
        assertFalse(r.estaMorto());
    }

    @Test
    void receberDanoFatal() {
        InimigoRapido r = new InimigoRapido(caminho);
        r.receberDano(70);
        assertEquals(0, r.getVidaAtual());
        assertTrue(r.estaMorto());
    }

    @Test
    void vidaNaoFicaNegativa() {
        InimigoRapido r = new InimigoRapido(caminho);
        r.receberDano(9999);
        assertEquals(0, r.getVidaAtual());
        assertTrue(r.estaMorto());
    }

    @Test
    void inimigomortoNaoAtualiza() {
        InimigoRapido r = new InimigoRapido(caminho);
        double xAntes = r.getX();
        r.receberDano(9999);
        assertTrue(r.estaMorto());
        r.update();
        assertEquals(xAntes, r.getX(), 1e-9); // não se moveu
    }

    // ── chegouABase ───────────────────────────────────────────────
    @Test
    void inimigoPercorreCaminhoEChegaABase() {
        // Caminho de apenas 2 pontos para forçar chegada rápida
        List<Point> curtinho = new ArrayList<>();
        curtinho.add(new Point(0, 0));
        curtinho.add(new Point(0, 1)); // 1 passo
        InimigoRapido r = new InimigoRapido(curtinho);

        // Velocidade é 2.5 px/tick; distância = CELL_SIZE = 52 px → ~21 ticks
        for (int i = 0; i < 100; i++) r.update();
        assertTrue(r.chegouABase());
    }

    // ── getProgresso ──────────────────────────────────────────────
    @Test
    void progressoInicialEZero() {
        InimigoRapido r = new InimigoRapido(caminho);
        assertEquals(0.0, r.getProgresso(), 1e-9);
    }

    // ── getCor ────────────────────────────────────────────────────
    @Test
    void rapidoTemCorCorreta() {
        InimigoRapido r = new InimigoRapido(caminho);
        assertNotNull(r.getCor());
        assertEquals(new Color(80, 180, 80), r.getCor());
    }

    @Test
    void lentoTemCorCorreta() {
        InimigoLento l = new InimigoLento(caminho);
        assertEquals(new Color(180, 60, 60), l.getCor());
    }

    @Test
    void bossTemCorCorreta() {
        InimigoBoss b = new InimigoBoss(caminho);
        assertEquals(new Color(120, 0, 180), b.getCor());
    }
}
