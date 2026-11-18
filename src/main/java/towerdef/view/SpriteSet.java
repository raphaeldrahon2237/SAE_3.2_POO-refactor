package towerdef.view;
import javafx.scene.image.Image;

public record SpriteSet(Image initial, Image idle, Image attack) {

    public static SpriteSet makeSpriteSetOf(String initial, String idle, String attack){
        return new SpriteSet(load(initial),load(idle),load(attack));
    }
    public static Image load(String name){
        if (name== null) return null;
        else {
            Image img= new Image(SpriteSet.class.getResourceAsStream("/images/"+name+".png"));
            return img;
        }

    }

}
