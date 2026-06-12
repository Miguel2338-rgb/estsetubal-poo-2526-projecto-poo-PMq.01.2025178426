package jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapaTest {

    private Mapa mapa;

    @BeforeEach
    void setUp() {
        mapa = new Mapa();
    }

    @Test
    void dimensoesCorretas() {
        assertEquals(20, Mapa.COLS);
        assertEquals(14, Mapa.ROWS);
        assertEquals(52, Mapa.CELL_SIZE);
    }

    @Test
    void pixelDimensoesCorretas() {
        assertEquals(Mapa.COLS * Mapa.CELL_SIZE, mapa.getPixelWidth());
        assertEquals(Mapa.ROWS * Mapa.CELL_SIZE, mapa.getPixelHeight());
    }

    @Test
    void caminhoNaoEVazio() {
        List<Point> caminho = mapa.getCaminho();
        assertNotNull(caminho);
        assertFalse(caminho.isEmpty());
    }

    @Test
    void celulaDeCaminhoNaoAceitaTorre() {
        List<Point> caminho = mapa.getCaminho();
        for (Point p : caminho) {
            assertFalse(mapa.isPosicaoValida(p.x, p.y),
                    "Célula de caminho (" + p.x + "," + p.y + ") não deve aceitar torre");
        }
    }

    @Test
    void posicaoForaDoLimitEInvalida() {
        assertFalse(mapa.isPosicaoValida(-1, 0));
        assertFalse(mapa.isPosicaoValida(0, -1));
        assertFalse(mapa.isPosicaoValida(Mapa.COLS, 0));
        assertFalse(mapa.isPosicaoValida(0, Mapa.ROWS));
    }

    @Test
    void gridNaoNulo() {
        int[][] grid = mapa.getGrid();
        assertNotNull(grid);
        assertEquals(Mapa.ROWS, grid.length);
        assertEquals(Mapa.COLS, grid[0].length);
    }

    @Test
    void existeAlgumaCelulaValidaParaTorre() {
        boolean algumaValida = false;
        for (int r = 0; r < Mapa.ROWS; r++) {
            for (int c = 0; c < Mapa.COLS; c++) {
                if (mapa.isPosicaoValida(c, r)) {
                    algumaValida = true;
                    break;
                }
            }
        }
        assertTrue(algumaValida);
    }
}
