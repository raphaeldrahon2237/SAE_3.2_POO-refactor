package towerdef.View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class ViewTerrain {

    public static void remplirTerrain(TilePane tilePane) {
        Image blue_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/blue-tile.png"));
        Image gray_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/gray-tile.png"));
        Image red_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/red-tile.png"));
        Image yellow_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/yellow-tile.png"));
        Image green_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/green-tile.png"));

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
                switch (map[i][j]){
                    case 1: iv.setImage(green_tile);
                        break;
                    case 2: iv.setImage(yellow_tile);
                        break;
                    case 3: iv.setImage(blue_tile);
                        break;
                    case 4: iv.setImage(red_tile);
                        break;
                    case 5: iv.setImage(gray_tile);
                        break;
                    default: break;
                }
                tilePane.getChildren().add(iv);
            }
        }
    }
}
