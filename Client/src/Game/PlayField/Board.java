package Game.PlayField;

import Game.GameConstants;
import Game.Messages.MessageHandler;
import Game.Player.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private Timer timer;
    private MessageHandler mh;
    private Player player;

    public Board(MessageHandler mh) {
        initBoard();
        this.mh = mh;
    }

    private void initBoard() {
        player = new Player();

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                0, GameConstants.MS_TICK_TIME);
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
            player.move();
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
