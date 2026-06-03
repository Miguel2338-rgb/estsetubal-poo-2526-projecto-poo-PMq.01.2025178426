package jogo;

import entidades.Inimigo;
import entidades.Torre;
import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private Jogador jogador;
    private Mapa mapa;
    private List<Torre> torres;
    private List<Onda> ondas;

    public Jogo(){
        jogador = new Jogador(100, 10);
        mapa = new Mapa();
        torres = new ArrayList<Torre>();
        ondas = new ArrayList<Onda>();
    }

    public void colocarTorre(Torre torre){
        torres.add(torre);
    }
    public void iniciarProximaOnda(){
        Onda onda = new Onda(ondas.size() + 1);
        onda.spawnInimigos();
        ondas.add(onda);
    }

    public void atualizar(){
        if (ondas.isEmpty()) return;

        Onda atual = new Onda(ondas.size() - 1);

        for (Inimigo i : atual.getInimigos()) {
            i.mover();
        }

        for (Torre t : torres) {
            for (Inimigo i : atual.getInimigos()) {
                if (t.podeAtacar(i)){
                    t.atacar(i);
                }
            }
        }
    }
}
