/**
package byow.Core;

import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class ListenKeyboardThread implements Runnable{
    @Override
    public void run() {

        do {
//            c = keyboardInputSource.getNextKey();
            // through this way, IO is not be blocked
            if(StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toUpperCase(c);
                switch (c) {
                    case 'W':
//                    System.out.println("w");
//                    if(avatar.getExit())
                        avatar.toUp(world);
                        break;
                    case 'S':
                        avatar.toDown(world);
                        break;
                    case 'A':
                        avatar.toLeft(world);
                        break;
                    case 'D':
                        avatar.toRight(world);
                        break;
                    default:
                        break;

                }
                if (world[avatar.getCurPos().x][avatar.getCurPos().y] == Tileset.UNLOCKED_DOOR)
                    gameOver = true;
            }

            ter.renderFrame(world);
        } while (!gameOver);
    }
}
 */
