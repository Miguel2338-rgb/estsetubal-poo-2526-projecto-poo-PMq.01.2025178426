package jogo;


import entidades.*;
import java.util.*;

public class Jogo {
    public enum Estado { MENU, JOGANDO, ENTRE_ONDAS, VITORIA, DERROTA }

    private Mapa mapa;
    private Jogador jogador;
    private List<Torre> torres;
    private Onda ondaAtual;
    private int numeroOnda;
    private static final int TOTAL_ONDAS = 8;
    private Estado estado;
    private List<Projetil> projeteis;

    private Runnable onUpdate;

    public Jogo() {
        mapa = new Mapa();
        jogador = new Jogador();
        torres = new ArrayList<>();
        projeteis = new ArrayList<>();
        estado = Estado.MENU;
        numeroOnda = 0;
    }

    public void iniciar() {
        torres.clear();
        projeteis.clear();
        jogador.reset();
        numeroOnda = 0;
        ondaAtual = null;
        estado = Estado.ENTRE_ONDAS;
    }

    public void iniciarProximaOnda() {
        if (estado != Estado.ENTRE_ONDAS) return;
        numeroOnda++;
        ondaAtual = new Onda(numeroOnda, mapa.getCaminho());
        ondaAtual.iniciar();
        estado = Estado.JOGANDO;
    }

    public void tick() {
        if (estado != Estado.JOGANDO || ondaAtual == null) return;

        ondaAtual.update();

        for (Torre t : torres) {
            Inimigo hit = t.attack(ondaAtual.getInimigos());
            if (hit != null) {
                projeteis.add(new Projetil(
                        t.getCentroX(), t.getCentroY(),
                        hit.getX(), hit.getY(),
                        t.getCor()
                ));
            }
        }

        for (Inimigo e : ondaAtual.getInimigos()) {
            if (e.estaMorto()) {
            }
        }
        processRewards();

        List<Inimigo> remover = new ArrayList<>();

        for (Inimigo e : ondaAtual.getInimigos()) {
            if (e.chegouABase()) {
                jogador.perderVida(e.getDanoBase());
                remover.add(e);
            }
        }

        ondaAtual.getInimigos().removeAll(remover);

        projeteis.removeIf(p -> { p.update(); return !p.isAtivo(); });

        if (!jogador.estaVivo()) {
            estado = Estado.DERROTA;
            return;
        }

        if (ondaAtual.estaCompleta()) {
            if (numeroOnda >= TOTAL_ONDAS) {
                estado = Estado.VITORIA;
            } else {
                estado = Estado.ENTRE_ONDAS;
                jogador.adicionarMoedas(30); // wave bonus
            }
        }

        if (onUpdate != null) onUpdate.run();
    }

    private Set<Inimigo> recompensasColetadas = new HashSet<>();
    private void processRewards() {
        for (Inimigo e : ondaAtual.getInimigos()) {
            if (e.estaMorto() && !recompensasColetadas.contains(e)) {
                recompensasColetadas.add(e);
                jogador.adicionarMoedas(e.getRecompensa());
            }
        }
    }

    public boolean colocarTorre(int col, int row, String tipo) {
        if (!mapa.isPosicaoValida(col, row)) return false;
        // Check if there's already a tower there
        for (Torre t : torres) {
            if (t.getCol() == col && t.getRow() == row) return false;
        }

        int custo;
        Torre nova;
        switch (tipo) {
            case "RAPIDA"  -> { nova = new TorreRapida(col, row);  custo = nova.getCustoBase(); }
            case "PESADA"  -> { nova = new TorrePesada(col, row);  custo = nova.getCustoBase(); }
            case "FRANCO"  -> { nova = new TorreFranco(col, row);  custo = nova.getCustoBase(); }
            default        -> { return false; }
        }

        if (!jogador.gastarMoedas(custo)) return false;
        torres.add(nova);
        return true;
    }

    public boolean upgradesTorre(Torre t) {
        int custo = t.getCustoUpgrade();
        if (!jogador.gastarMoedas(custo)) return false;
        t.upgrade(custo);
        return true;
    }

    public void removerTorre(Torre t) {
        torres.remove(t);
        jogador.adicionarMoedas(t.getCustoBase() / 2);
    }

    public Torre getTorreNaPosicao(int col, int row) {
        for (Torre t : torres) {
            if (t.getCol() == col && t.getRow() == row) return t;
        }
        return null;
    }

    // Getters
    public Mapa getMapa() { return mapa; }
    public Jogador getJogador() { return jogador; }
    public Onda getOndaAtual() { return ondaAtual; }
    public List<Torre> getTorres() { return torres; }
    public List<Projetil> getProjeteis() { return projeteis; }
    public Estado getEstado() { return estado; }
    public int getNumeroOnda() { return numeroOnda; }
    public int getTotalOndas() { return TOTAL_ONDAS; }
    public void setOnUpdate(Runnable r) { onUpdate = r; }

    public List<Inimigo> getInimigosAtivos() {
        if (ondaAtual == null) return Collections.emptyList();
        return ondaAtual.getInimigos();
    }
}