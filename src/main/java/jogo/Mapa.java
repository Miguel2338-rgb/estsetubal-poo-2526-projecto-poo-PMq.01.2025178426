package jogo;

import java.util.ArrayList;
import java.util.List;
import utils.Posicao;

public class Mapa {
    private List<Posicao> caminho;
    private List<Posicao> zonasColocacao;

    public Mapa() {
        caminho = new ArrayList<>();
        zonasColocacao = new ArrayList<>();

        caminho.add(new Posicao(0, 0));
        caminho.add(new Posicao(5, 0));
        caminho.add(new Posicao(10, 5));
        caminho.add(new Posicao(15, 5));
    }

    public boolean posicaoValida(Posicao p) {
        return true;
    }

    public List<Posicao> getCaminho() {
        return caminho;
    }
}

