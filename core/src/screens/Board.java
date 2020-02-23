package screens;

import com.badlogic.gdx.graphics.Texture;
import com.darash.monopoly.MyGame;

public class Board extends BaseScreen {

    public Board(MyGame mg) {
        super(mg);
        this.background=new Texture("board/board_image.jpg");
    }
}
