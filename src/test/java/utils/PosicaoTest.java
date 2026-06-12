package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PosicaoTest {

    @Test
    void gettersRetornamValoresCorretos() {
        Posicao p = new Posicao(3.0, 4.0);
        assertEquals(3.0, p.getX());
        assertEquals(4.0, p.getY());
    }

    @Test
    void distanciaEntrePontosDistintos() {
        Posicao a = new Posicao(0, 0);
        Posicao b = new Posicao(3, 4);
        assertEquals(5.0, a.distancia(b), 1e-9);
    }

    @Test
    void distanciaParaMesmoPontoEZero() {
        Posicao p = new Posicao(7.0, 7.0);
        assertEquals(0.0, p.distancia(p), 1e-9);
    }

    @Test
    void distanciaESimetrica() {
        Posicao a = new Posicao(1, 2);
        Posicao b = new Posicao(4, 6);
        assertEquals(a.distancia(b), b.distancia(a), 1e-9);
    }

    @Test
    void distanciaComCoordenadasNegativas() {
        Posicao a = new Posicao(-3, -4);
        Posicao b = new Posicao(0, 0);
        assertEquals(5.0, a.distancia(b), 1e-9);
    }
}
