package screens;

import com.badlogic.gdx.graphics.Texture;
import com.darash.monopoly.MyGame;

import java.util.ArrayList;

import classes.Player;

public class Board extends BaseScreen {

    /**
     * Constructor of the Board class
     * Here we add the image of the board to the game
     * @param mg Game
     */
    public Board(MyGame mg, ArrayList<Player> players) {
        super(mg, players);
        this.background=new Texture("board/correct_board.PNG");
    }
}
