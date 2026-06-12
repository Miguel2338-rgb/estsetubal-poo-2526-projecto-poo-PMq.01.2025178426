package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

public class JogoApp extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        // Canvas para desenhar o jogo
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // HUD (barra superior)
        HBox hud = new HBox(10);
        Button btnIniciar = new Button("Iniciar Jogo");
        Button btnTorre = new Button("Colocar Torre");
        hud.getChildren().addAll(btnIniciar, btnTorre);

        root.setTop(hud);
        root.setCenter(canvas);

        // Cena principal
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Defend the Kingdom");
        stage.setScene(scene);
        stage.show();

        // Desenho inicial
        desenharMapa(gc);
    }

    private void desenharMapa(GraphicsContext gc) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(0, 0, 800, 600);

            gc.setFill(Color.DARKGRAY);
            gc.fillRect(100, 250, 600, 100); // caminho dos inimigos
        }
}
