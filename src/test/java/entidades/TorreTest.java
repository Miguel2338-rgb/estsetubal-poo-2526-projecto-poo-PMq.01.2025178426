package entidades;

import jogo.Mapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TorreTest {

    private List<Point> caminho;

    @BeforeEach
    void setUp() {
        caminho = new ArrayList<>();
        caminho.add(new Point(0, 0));
        caminho.add(new Point(1, 0));
        caminho.add(new Point(2, 0));
        caminho.add(new Point(3, 0));
    }

    // ── TorreRapida ───────────────────────────────────────────────
    @Test
    void rapidaIniciaComValoresCorretos() {
        TorreRapida t = new TorreRapida(2, 3);
        assertEquals(2, t.getCol());
        assertEquals(3, t.getRow());
        assertEquals(90, t.getAlcance(), 1e-9);
        assertEquals(12, t.getDano());
        assertEquals(12, t.getCadencia());
        assertEquals(1, t.getNivel());
        assertEquals(70, t.getCustoBase());
        assertEquals("Torre Rápida", t.getNome());
    }

    // ── TorrePesada ───────────────────────────────────────────────
    @Test
    void pesadaIniciaComValoresCorretos() {
        TorrePesada t = new TorrePesada(0, 0);
        assertEquals(130, t.getAlcance(), 1e-9);
        assertEquals(40, t.getDano());
        assertEquals(35, t.getCadencia());
        assertEquals(330, t.getCustoBase());
        assertEquals("Torre Pesada", t.getNome());
    }

    // ── TorreFranco ───────────────────────────────────────────────
    @Test
    void francoIniciaComValoresCorretos() {
        TorreFranco t = new TorreFranco(0, 0);
        assertEquals(200, t.getAlcance(), 1e-9);
        assertEquals(70, t.getDano());
        assertEquals(60, t.getCadencia());
        assertEquals(850, t.getCustoBase());
        assertEquals("Franco-Atirador", t.getNome());
    }

    // ── getCentroX / getCentroY ───────────────────────────────────
    @Test
    void centroCalculadoCorretamente() {
        TorreRapida t = new TorreRapida(3, 2);
        double expectedX = 3 * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        double expectedY = 2 * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        assertEquals(expectedX, t.getCentroX(), 1e-9);
        assertEquals(expectedY, t.getCentroY(), 1e-9);
    }

    // ── attack ────────────────────────────────────────────────────
    @Test
    void torreSemInimigosNaoAtaca() {
        TorreRapida t = new TorreRapida(0, 0);
        Inimigo hit = t.attack(new ArrayList<>());
        assertNull(hit);
    }

    @Test
    void torreAtacaInimigoNoAlcance() {
        // Torre na célula (0,0), inimigo também na célula (0,0) — dentro do alcance
        TorreRapida t = new TorreRapida(0, 0);
        InimigoRapido e = new InimigoRapido(caminho);

        List<Inimigo> lista = List.of(e);
        Inimigo hit = t.attack(lista);

        assertNotNull(hit);
        assertEquals(70 - 12, e.getVidaAtual()); // dano = 12
    }

    @Test
    void torreCooldownImpedeSeguindoAtaque() {
        TorreRapida t = new TorreRapida(0, 0);
        InimigoRapido e = new InimigoRapido(caminho);
        List<Inimigo> lista = List.of(e);

        t.attack(lista); // primeiro ataque
        Inimigo hit2 = t.attack(lista); // cooldown ativo
        assertNull(hit2);
    }

    @Test
    void torrenaoAtacaInimigoMorto() {
        TorreRapida t = new TorreRapida(0, 0);
        InimigoRapido e = new InimigoRapido(caminho);
        e.receberDano(9999); // mata o inimigo
        assertTrue(e.estaMorto());

        Inimigo hit = t.attack(List.of(e));
        assertNull(hit);
    }

    @Test
    void francoEscolheInimigoComMaisVida() {
        // Dois inimigos no alcance: lento (180 HP) e rápido (70 HP)
        // Franco deve atacar o lento
        TorreFranco t = new TorreFranco(0, 0);
        InimigoLento lento = new InimigoLento(caminho);
        InimigoRapido rapido = new InimigoRapido(caminho);

        Inimigo hit = t.attack(List.of(rapido, lento));
        assertSame(lento, hit);
        assertEquals(180 - 70, lento.getVidaAtual());
        assertEquals(70, rapido.getVidaAtual()); // não foi atacado
    }

    // ── upgrade ───────────────────────────────────────────────────
    @Test
    void upgradeAumentaAtributos() {
        TorreRapida t = new TorreRapida(0, 0);
        int danoAntes = t.getDano();
        double alcanceAntes = t.getAlcance();
        int cadenciaAntes = t.getCadencia();

        boolean ok = t.upgrade(9999);

        assertTrue(ok);
        assertEquals(2, t.getNivel());
        assertEquals((int)(danoAntes * 1.5), t.getDano());
        assertEquals(alcanceAntes * 1.15, t.getAlcance(), 1e-6);
        assertTrue(t.getCadencia() < cadenciaAntes);
    }

    @Test
    void upgradeFalhaComMoedasInsuficientes() {
        TorreRapida t = new TorreRapida(0, 0);
        boolean ok = t.upgrade(0); // sem moedas
        assertFalse(ok);
        assertEquals(1, t.getNivel());
    }

    @Test
    void upgradeLimitadoANivel3() {
        TorreRapida t = new TorreRapida(0, 0);
        t.upgrade(9999);
        t.upgrade(9999);
        boolean terceiro = t.upgrade(9999);
        assertFalse(terceiro); // já está no nível 3
        assertEquals(3, t.getNivel());
    }

    // ── getCor ────────────────────────────────────────────────────
    @Test
    void coresCorretasPorTipo() {
        assertEquals(new Color(70, 160, 220), new TorreRapida(0, 0).getCor());
        assertEquals(new Color(200, 100, 50), new TorrePesada(0, 0).getCor());
        assertEquals(new Color(60, 200, 120), new TorreFranco(0, 0).getCor());
    }
}

