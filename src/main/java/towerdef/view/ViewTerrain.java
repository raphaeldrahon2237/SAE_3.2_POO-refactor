package towerdef.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class ViewTerrain {

    public static void renderMap(TilePane tilePane, int[][] map) {
        Image grass1 = new Image(ViewTerrain.class.getResourceAsStream("/images/grass1.png"));
        Image grass2 = new Image(ViewTerrain.class.getResourceAsStream("/images/grass2.png"));
        Image grass3 = new Image(ViewTerrain.class.getResourceAsStream("/images/grass3.png"));


        Image base = new Image(ViewTerrain.class.getResourceAsStream("/images/base.png"));

        Image road_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-vertical.png"));
        Image road_side = new Image(ViewTerrain.class.getResourceAsStream("/images/road-side.png"));
        Image road_turn_down_left = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-down-left.png"));
        Image road_turn_down_right = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-down-right.png"));
        Image road_turn_right_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-up-left.png"));
        Image road_turn_left_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-turn-up-right.png"));
        Image road_intersection_down = new Image(ViewTerrain.class.getResourceAsStream("/images/road-intersection-down.png"));
        Image road_intersection_up = new Image(ViewTerrain.class.getResourceAsStream("/images/road-intersection-up.png"));
        Image road_intersection_full= new Image(ViewTerrain.class.getResourceAsStream("/images/road-intersection-full.png"));

        Image mountain1 = new Image(ViewTerrain.class.getResourceAsStream("/images/mountain1.png"));
        Image mountain2 = new Image(ViewTerrain.class.getResourceAsStream("/images/mountain2.png"));



        for (int i = 0; i <= map.length - 1; i++) {
            for (int j = 0; j <= map[0].length - 1; j++) {
                ImageView iv= new ImageView();
                switch (map[i][j]){
                    case 1: iv.setImage(grass1);
                        break;
                    case 2: iv.setImage(grass2);
                        break;
                    case 3: iv.setImage(grass3);
                        break;
                    case 4: iv.setImage(base);
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
                    case 14: iv.setImage(road_intersection_full);
                        break;
                    case 15: iv.setImage(mountain1);
                        break;
                    case 16: iv.setImage(mountain2);
                        break;
                    default: break;
                }
                tilePane.getChildren().add(iv);
            }
        }
    }
}
