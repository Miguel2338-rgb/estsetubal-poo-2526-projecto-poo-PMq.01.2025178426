# Defend the Kingdom  
### Tower Defense em Java — Projeto de Programação Orientada a Objetos  
ESTSetúbal · 2025/2026

## Descrição
**Defend the Kingdom** é um jogo Tower Defense desenvolvido em **Java** com interface gráfica **Swing**.  
O objetivo é defender a base do reino contra ondas de inimigos, colocando torres estrategicamente no mapa.  
O jogador gere moedas, vida, upgrades e posicionamento de torres para sobreviver às 8 ondas.

---

## Funcionalidades Principais
- 3 tipos de inimigos: Rápido, Lento e Boss  
- 3 tipos de torres: Rápida, Pesada e Franco-Atirador  
- Sistema de upgrades até nível 3  
- Venda de torres (50% do custo)  
- Projeteis animados  
- Barra de HP dos inimigos  
- Bónus entre ondas  
- HUD completo com botões de compra, upgrade e venda  
- Ecrãs de menu, vitória e derrota  
- Validação de posicionamento no mapa  
- 73 testes unitários com JUnit 5

---

## Modelação do Domínio
O jogo é composto por várias entidades:

- **Inimigo (abstract)** → Rapido, Lento, Boss  
- **Torre (abstract)** → Rapida, Pesada, Franco  
- **Jogador** → moedas, vida  
- **Mapa** → grelha 20×14 e caminho  
- **Onda** → spawn de inimigos  
- **Jogo** → controlador central  
- **Projetil** → animação dos ataques  

O controlador **Jogo** coordena todas as entidades e gere o estado da partida.

---

## Arquitetura
O projeto segue uma arquitetura inspirada em **MVC**:

- `entidades/` → modelo de domínio  
- `jogo/` → lógica e controlo  
- `view/` → interface Swing  
- `utils/` → utilitários  
- `gui/` → Main e versão JavaFX não utilizada  

O game loop usa `javax.swing.Timer` (~30 FPS).

---

## Testes Unitários
Implementados com **JUnit 5**.

Total: **73 testes**, cobrindo:
- Jogador  
- Inimigos  
- Torres  
- Mapa  
- Projetil  
- Onda  
- Jogo  
- Posicao

---

## Como Executar
Instalar Java 17

Clonar o repositório:

git clone https://github.com/Miguel2338-rgb/estsetubal-poo-2526-projecto-poo-PMq.01.2025178426.git
Compilar:

mvn clean install
Executar a aplicação via IntelliJ
   
## Dificuldades e Soluções
Rendering em Swing → uso de Graphics2D e delegação por subclasse

Sincronização do game loop → tudo na EDT

Recompensas duplicadas → Set de controlo

Targeting da TorreFranco → override attack()

Testes independentes da UI → caminhos sintéticos

---
 
## Conclusão
O projeto permitiu aplicar:

Encapsulamento

Herança

Polimorfismo

Arquitetura em camadas

Testes unitários extensivos

Como trabalho futuro:

- guardar recordes

- dificuldade ajustável

- novas torres (ex.: slow)

- efeitos sonoros

- migração para JavaFX

---

## Autor
Miguel Belchior e Omar Botelho — ESTSetúbal, 2025/2026
