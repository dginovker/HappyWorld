package Game.GameWindow.Game;

import Game.GameConstants;
import Game.Messages.ConnectionHandler;
import Game.Messages.Connections.ServerStats;
import Game.Player.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.util.function.BooleanSupplier;

public class Board extends JPanel {

    private Timer timer;
    private Player player;
    private ConnectionHandler mh;

    public Board(ConnectionHandler mh)
    {
        this.mh = mh;
        initBoard();
    }

    private void initBoard() {
        player = new Player();

        addKeyListener(new TAdapter());

        setFocusable(true);
        setBackground(Color.BLACK);

        timer = new Timer();
        timer.schedule(new ScheduleTask(), 0, GameConstants.MS_TICK_TIME);
    }

    public void tryToGetFocus()
    {
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run()
        {
            tryToGetFocus();
            player.move();
            mh.send("PlayerCoords:" + player.getX() + "," + player.getY() + "*" + ServerStats.ClientID);
            //Paint all the objects
            repaint();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.controls.keyPressed(e);
        }
    }
}
