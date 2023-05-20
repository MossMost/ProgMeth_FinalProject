package scenes;

import java.nio.file.Files;
import java.nio.file.Paths;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprites.MonsterCry;

public class CreditsScene extends GeneralScene{

    private static final String HEART_IMAGE = "assets/Heart.png";
    private Image background;
    private MonsterCry monster;
    public CreditsScene()
    {
        super();
        monster = new MonsterCry();
    }

    private void showCreditsMessage() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 80);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER", 250, 200);
        myFont = Font.font("Arial", FontWeight.NORMAL, 40);
        gc.setFont(myFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press Spacebar to go back to Welcome Scene", 100, 585);
    }

    @Override
    public void draw() {
        activeKeys.clear();

        new AnimationTimer() {
             public void handle(long currentNanoTime){
                     gc.setFill(Color.BLACK);
                     gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                     monster.moveTo(430, 300);
                     monster.draw(gc);
                     monster.animate();
                     showCreditsMessage();

                    if(activeKeys.contains(KeyCode.SPACE)){
                        this.stop();
                        Main.setScene(Main.WELCOME_SCENE);
                    }
            }
        }.start();
    }
}