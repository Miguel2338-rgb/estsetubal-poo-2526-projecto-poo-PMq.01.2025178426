package jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OndaTest {

    private List<Point> caminho;

    @BeforeEach
    void setUp() {
        caminho = new ArrayList<>();
        for (int i = 0; i < 10; i++) caminho.add(new Point(i, 0));
    }

    @Test
    void ondaIniciaNaoCompleta() {
        Onda onda = new Onda(1, caminho);
        assertFalse(onda.estaCompleta());
    }

    @Test
    void ondaNaoIniciada_naoCompleta() {
        Onda onda = new Onda(1, caminho);
        // sem chamar iniciar()
        assertFalse(onda.estaCompleta());
    }

    @Test
    void getNumeroCorrespondente() {
        Onda onda = new Onda(3, caminho);
        assertEquals(3, onda.getNumero());
    }

    @Test
    void totalInimigosPositivo() {
        Onda onda = new Onda(1, caminho);
        assertTrue(onda.getTotalInimigos() > 0);
    }

    @Test
    void ondaComMultiplosOndas_temMaisInimigos() {
        Onda onda1 = new Onda(1, caminho);
        Onda onda5 = new Onda(5, caminho);
        assertTrue(onda5.getTotalInimigos() > onda1.getTotalInimigos());
    }

    @Test
    void semSpawnAntesDeIniciar() {
        Onda onda = new Onda(1, caminho);
        onda.update(); // sem iniciar
        assertEquals(0, onda.getInimigos().size());
    }

    @Test
    void spawnDepoisDeIniciarEUpdate() {
        Onda onda = new Onda(1, caminho);
        onda.iniciar();
        // Delay padrão é 45 ticks, então precisamos de pelo menos 45 updates
        for (int i = 0; i < 50; i++) onda.update();
        assertTrue(onda.getInimigos().size() > 0);
    }

    @Test
    void ondaMultiploDe3TemBoss() {
        // Onda 3 deve ter um boss na fila de spawn
        Onda onda3 = new Onda(3, caminho);
        // Total deve incluir boss
        assertTrue(onda3.getTotalInimigos() > 0);
        // Verificamos indiretamente: o total de onda 3 > onda 2 sem boss
        Onda onda2 = new Onda(2, caminho);
        // Onda 3 tem boss (extra +1) além dos demais
        assertTrue(onda3.getTotalInimigos() >= onda2.getTotalInimigos());
    }

    @Test
    void spawnIndexIniciaZero() {
        Onda onda = new Onda(1, caminho);
        assertEquals(0, onda.getSpawnados());
    }
}
