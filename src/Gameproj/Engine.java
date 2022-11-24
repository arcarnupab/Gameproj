package Gameproj;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class Engine
{
    private static final int TIME_STEP = 10;
    private static int width = 20;
    private static int height = 20;
    private static int nrOfEnemies = 10; //จำนวนศัตรู
    private static Timer clockTimer = null;

    private Engine() {}

    public static void main(String[] args) {
	startGame();
    }

    public static void startGame() { ///เริ่มเกม
	Floor floor = new Floor(width, height, nrOfEnemies); ////กว้าง,สูง,จำนวนศัตรู เพื่อเจนเนอเรทแมพ
	BombermanFrame frame = new BombermanFrame("Putin bombing game", floor); ///frame throw NameFrame and floor to parameter for openframe and game run
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	floor.addFloorListener(frame.getBombermanComponent());

	Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		tick(frame, floor);
	    }
	};
	clockTimer = new Timer(TIME_STEP, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    private static void gameOver(BombermanFrame frame, Floor floor) { ///เกมโอเวอร์ = แพ้!
	clockTimer.stop();
	frame.dispose();
	startGame();
    }

    private static void tick(BombermanFrame frame, Floor floor) { //เจนเนอเรท
	if (floor.getIsGameOver()) { ///เช็คว่าถ้าเกมโอเวอร์ให้ทำ
	    gameOver(frame, floor);
	} else { ///ถ้าไม่เกมโอเวอร์
	    floor.moveEnemies();
	    floor.bombCountdown();
	    floor.explosionHandler();
	    floor.characterInExplosion();
	    floor.notifyListeners();
	}
    }
}
