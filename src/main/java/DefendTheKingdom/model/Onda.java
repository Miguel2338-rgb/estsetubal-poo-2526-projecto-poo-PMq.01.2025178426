package DefendTheKingdom.model;

import DefendTheKingdom.model.entities.*;
import java.awt.Point;
import java.util.*;

public class Onda {
    private int numero;
    private List<Inimigo> inimigos;
    private List<Point> caminho;
    private int spawnDelay;       // ticks between spawns
    private int spawnCounter;
    private List<String> spawnQueue; // types to spawn
    private int spawnIndex;
    private boolean iniciada;

    public Onda(int numero, List<Point> caminho) {
        this.numero = numero;
        this.caminho = caminho;
        this.inimigos = new ArrayList<>();
        this.spawnQueue = new ArrayList<>();
        this.spawnIndex = 0;
        this.spawnDelay = 45;
        this.spawnCounter = 0;
        this.iniciada = false;
        buildSpawnQueue();
    }

    private void buildSpawnQueue() {
        int rapidosBase = 3 + numero * 2;
        int lentosBase = 1 + numero;
        boolean hasBoss = (numero % 3 == 0);

        for (int i = 0; i < rapidosBase; i++) spawnQueue.add("RAPIDO");
        for (int i = 0; i < lentosBase; i++) spawnQueue.add("LENTO");
        if (numero >= 2) {
            int extra = (numero - 1) * 2;
            for (int i = 0; i < extra; i++) {
                spawnQueue.add(i % 2 == 0 ? "RAPIDO" : "LENTO");
            }
        }
        if (hasBoss) spawnQueue.add("BOSS");

        Collections.shuffle(spawnQueue);
    }

    public void iniciar() { iniciada = true; }

    public void update() {
        if (!iniciada) return;

        // Spawn
        if (spawnIndex < spawnQueue.size()) {
            spawnCounter++;
            if (spawnCounter >= spawnDelay) {
                spawnCounter = 0;
                String tipo = spawnQueue.get(spawnIndex++);
                Inimigo novo = switch (tipo) {
                    case "RAPIDO" -> new InimigoRapido(caminho);
                    case "BOSS"   -> new InimigoBoss(caminho);
                    default       -> new InimigoLento(caminho);
                };
                inimigos.add(novo);
            }
        }

        // Update all enemies
        for (Inimigo e : inimigos) e.update();
    }

    public boolean estaCompleta() {
        if (!iniciada) return false;
        if (spawnIndex < spawnQueue.size()) return false;
        return inimigos.stream().allMatch(e -> e.estaMorto() || e.chegouABase());
    }

    public List<Inimigo> getInimigos() { return inimigos; }
    public int getNumero() { return numero; }
    public int getTotalInimigos() { return spawnQueue.size(); }
    public int getSpawnados() { return spawnIndex; }
}
