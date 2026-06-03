package jogo;

import entidades.Inimigo;
import entidades.InimigoLento;
import entidades.InimigoRapido;
import utils.Posicao;

import java.util.ArrayList;
import java.util.List;

public class Onda {
    private List<Inimigo> inimigos;
    private int numero;

    public Onda(int numero) {
        this.numero = numero;
        inimigos = new ArrayList<>();
    }

    public void spawnInimigos(){
        inimigos.add(new InimigoLento(new Posicao(0, 0)));
        inimigos.add(new InimigoRapido(new Posicao(0, 1)));
    }

    public List<Inimigo> getInimigos(){
        return inimigos;
    }
}
