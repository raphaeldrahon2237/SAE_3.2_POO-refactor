package towerdef.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private TilePane tilePane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        remplirTerrain();
    }

    public void remplirTerrain () {
        Image blue_tile = new Image(getClass().getResourceAsStream("/images/blue-tile.png"));
        Image gray_tile = new Image(getClass().getResourceAsStream("/images/gray-tile.png"));
        Image red_tile = new Image(getClass().getResourceAsStream("/images/red-tile.png"));
        Image yellow_tile = new Image(getClass().getResourceAsStream("/images/yellow-tile.png"));
        Image green_tile = new Image(getClass().getResourceAsStream("/images/green-tile.png"));

        int[][] map = { //grass=1 path=2 water=3 base=4 mountains=5
                {1, 2, 3, 4, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1}
        };
        for (int i = 0; i <= map.length - 1; i++) {
            for (int j = 0; j <= map[0].length - 1; j++) {
                ImageView iv= new ImageView();
                if (map[i][j] == 1) {
                    iv.setImage(green_tile);
                }
                else if (map[i][j] == 2) {
                    iv.setImage(yellow_tile);
                }
                else if (map[i][j] == 3) {
                    iv.setImage(blue_tile);
                }
                else if (map[i][j] == 4) {
                    iv.setImage(red_tile);
                }
                else if (map[i][j] == 5) {
                    iv.setImage(gray_tile);
                }
                tilePane.getChildren().add(iv);
            }
        }
    }
}
