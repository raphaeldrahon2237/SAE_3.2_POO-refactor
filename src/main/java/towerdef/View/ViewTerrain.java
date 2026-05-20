package towerdef.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class ViewTerrain {

    public static void renderMap(TilePane tilePane, int[][] map) {
        Image blue_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/blue-tile.png"));
        Image gray_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/gray-tile.png"));
        Image red_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/red-tile.png"));
        Image yellow_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/yellow-tile.png"));
        Image green_tile = new Image(ViewTerrain.class.getResourceAsStream("/images/green-tile.png"));
        Image road_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-down.png"));
        Image road_side = new Image(ViewTerrain.class.getResourceAsStream("/images/road-side.png"));
        Image road_turn_down_left = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-down-left.png"));
        Image road_turn_down_right = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-down-right.png"));
        Image road_turn_right_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-right-down.png"));
        Image road_turn_left_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-left-down.png"));
        Image road_intersection_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-intersection-down.png"));
        Image road_intersection_up = new Image(ViewTerrain.class.getResourceAsStream("/images/road-intersection-up.png"));


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
                    case 6: iv.setImage(road_down);
                        break;
                    case 7: iv.setImage(road_side);
                        break;
                    case 8: iv.setImage(road_turn_down_left);
                        break;
                    case 9: iv.setImage(road_turn_down_right);
                        break;
                    case 10: iv.setImage(road_turn_right_down);
                        break;
                    case 11: iv.setImage(road_turn_left_down);
                        break;
                    case 12: iv.setImage(road_intersection_down);
                        break;
                    case 13: iv.setImage(road_intersection_up);
                        break;
                    default: break;
                }
                tilePane.getChildren().add(iv);
            }
        }
    }
}
