package jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogadorTest {

    private Jogador jogador;

    @BeforeEach
    void setUp() {
        jogador = new Jogador();
    }

    @Test
    void deveIniciarComValoresCorretos() {
        assertEquals(150, jogador.getMoedas());
        assertEquals(20, jogador.getVida());
        assertTrue(jogador.estaVivo());
    }

    @Test
    void gastarMoedasComSaldoSuficiente() {
        boolean resultado = jogador.gastarMoedas(100);
        assertTrue(resultado);
        assertEquals(50, jogador.getMoedas());
    }

    @Test
    void gastarMoedasSemSaldoSuficiente() {
        boolean resultado = jogador.gastarMoedas(200);
        assertFalse(resultado);
        assertEquals(150, jogador.getMoedas()); // saldo inalterado
    }

    @Test
    void gastarExatamenteOSaldo() {
        boolean resultado = jogador.gastarMoedas(150);
        assertTrue(resultado);
        assertEquals(0, jogador.getMoedas());
    }

    @Test
    void adicionarMoedas() {
        jogador.adicionarMoedas(50);
        assertEquals(200, jogador.getMoedas());
    }

    @Test
    void perderVidaParcial() {
        jogador.perderVida(5);
        assertEquals(15, jogador.getVida());
        assertTrue(jogador.estaVivo());
    }

    @Test
    void perderTodaAVida() {
        jogador.perderVida(20);
        assertEquals(0, jogador.getVida());
        assertFalse(jogador.estaVivo());
    }

    @Test
    void vidaNaoFicaNegativa() {
        jogador.perderVida(999);
        assertEquals(0, jogador.getVida());
        assertFalse(jogador.estaVivo());
    }

    @Test
    void resetRestaurarValoresIniciais() {
        jogador.gastarMoedas(100);
        jogador.perderVida(10);
        jogador.reset();
        assertEquals(150, jogador.getMoedas());
        assertEquals(20, jogador.getVida());
        assertTrue(jogador.estaVivo());
    }
}
