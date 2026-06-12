package jogo;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ProjetilTest {

    @Test
    void iniciaAtivo() {
        Projetil p = new Projetil(0, 0, 100, 0, Color.RED);
        assertTrue(p.isAtivo());
    }

    @Test
    void posicaoInicialCorreta() {
        Projetil p = new Projetil(10, 20, 100, 200, Color.BLUE);
        assertEquals(10.0, p.getX(), 1e-9);
        assertEquals(20.0, p.getY(), 1e-9);
    }

    @Test
    void corCorreta() {
        Projetil p = new Projetil(0, 0, 50, 0, Color.GREEN);
        assertEquals(Color.GREEN, p.getCor());
    }

    @Test
    void moveEmDirecaoAlvo() {
        Projetil p = new Projetil(0, 0, 100, 0, Color.RED);
        double xAntes = p.getX();
        p.update();
        assertTrue(p.getX() > xAntes); // moveu para direita
        assertTrue(p.isAtivo());
    }

    @Test
    void desativaAoChegar() {
        // Alvo muito próximo (distância < speed=8)
        Projetil p = new Projetil(0, 0, 1, 0, Color.RED);
        p.update();
        assertFalse(p.isAtivo());
    }

    @Test
    void moveEmDiagonalCorrectamente() {
        Projetil p = new Projetil(0, 0, 100, 100, Color.RED);
        p.update();
        // Deve mover-se em diagonal: x e y aumentam igualmente
        assertEquals(p.getX(), p.getY(), 1e-9);
        assertTrue(p.isAtivo());
    }
}
