package jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogoTest {

    private Jogo jogo;

    @BeforeEach
    void setUp() {
        jogo = new Jogo();
    }

    @Test
    void estadoInicialEMenu() {
        assertEquals(Jogo.Estado.MENU, jogo.getEstado());
    }

    @Test
    void iniciarMudaEstadoParaEntreOndas() {
        jogo.iniciar();
        assertEquals(Jogo.Estado.ENTRE_ONDAS, jogo.getEstado());
    }

    @Test
    void iniciarResetaOndaERecursos() {
        jogo.iniciar();
        assertEquals(0, jogo.getNumeroOnda());
        assertNull(jogo.getOndaAtual());
        assertTrue(jogo.getTorres().isEmpty());
    }

    @Test
    void iniciarProximaOndaAumentaContador() {
        jogo.iniciar();
        jogo.iniciarProximaOnda();
        assertEquals(1, jogo.getNumeroOnda());
        assertEquals(Jogo.Estado.JOGANDO, jogo.getEstado());
    }

    @Test
    void iniciarProximaOndaSemEstadoCorretoNaoFazNada() {
        // Estado MENU — não deve alterar nada
        jogo.iniciarProximaOnda();
        assertEquals(0, jogo.getNumeroOnda());
        assertEquals(Jogo.Estado.MENU, jogo.getEstado());
    }

    @Test
    void colocarTorreValida() {
        jogo.iniciar();
        // Encontra uma célula válida no mapa
        int col = -1, row = -1;
        outer:
        for (int r = 0; r < Mapa.ROWS; r++) {
            for (int c = 0; c < Mapa.COLS; c++) {
                if (jogo.getMapa().isPosicaoValida(c, r)) {
                    col = c; row = r;
                    break outer;
                }
            }
        }
        assertTrue(col >= 0, "Deve existir posição válida");
        boolean ok = jogo.colocarTorre(col, row, "RAPIDA");
        assertTrue(ok);
        assertEquals(1, jogo.getTorres().size());
    }

    @Test
    void colocarTorreEmCelulaDeCaminho() {
        jogo.iniciar();
        java.awt.Point p = jogo.getMapa().getCaminho().get(0);
        boolean ok = jogo.colocarTorre(p.x, p.y, "RAPIDA");
        assertFalse(ok);
        assertTrue(jogo.getTorres().isEmpty());
    }

    @Test
    void colocarTorreSemMoedasSuficientes() {
        jogo.iniciar();
        // Gastar todas as moedas
        while (jogo.getJogador().gastarMoedas(50));

        int col = -1, row = -1;
        outer:
        for (int r = 0; r < Mapa.ROWS; r++) {
            for (int c = 0; c < Mapa.COLS; c++) {
                if (jogo.getMapa().isPosicaoValida(c, r)) { col = c; row = r; break outer; }
            }
        }
        boolean ok = jogo.colocarTorre(col, row, "RAPIDA");
        assertFalse(ok);
    }

    @Test
    void colocarTorreTipoInvalido() {
        jogo.iniciar();
        boolean ok = jogo.colocarTorre(5, 5, "INVALIDO");
        assertFalse(ok);
    }

    @Test
    void removerTorreDevolveMoedas() {
        jogo.iniciar();
        int col = -1, row = -1;
        outer:
        for (int r = 0; r < Mapa.ROWS; r++) {
            for (int c = 0; c < Mapa.COLS; c++) {
                if (jogo.getMapa().isPosicaoValida(c, r)) { col = c; row = r; break outer; }
            }
        }
        jogo.colocarTorre(col, row, "RAPIDA"); // custa 70
        int moedasApos = jogo.getJogador().getMoedas();

        entidades.Torre t = jogo.getTorreNaPosicao(col, row);
        assertNotNull(t);
        jogo.removerTorre(t);

        assertEquals(moedasApos + 70 / 2, jogo.getJogador().getMoedas()); // reembolso 50%
        assertNull(jogo.getTorreNaPosicao(col, row));
    }

    @Test
    void tickSemEstadoJogandoNaoFazNada() {
        jogo.iniciar(); // estado ENTRE_ONDAS
        // Não deve lançar exceção
        assertDoesNotThrow(() -> jogo.tick());
    }

    @Test
    void getInimigosAtivosVazioSemOnda() {
        jogo.iniciar();
        assertTrue(jogo.getInimigosAtivos().isEmpty());
    }

    @Test
    void totalOndasOito() {
        assertEquals(8, jogo.getTotalOndas());
    }

    @Test
    void getTorreNaPosicaoRetornaNull_semTorre() {
        jogo.iniciar();
        assertNull(jogo.getTorreNaPosicao(0, 0));
    }
}
