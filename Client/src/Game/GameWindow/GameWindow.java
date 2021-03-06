package Game.GameWindow;

import Game.GameWindow.Game.Board;
import Game.Messages.ConnectionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private CardLayout layout = new CardLayout();

    private JPanel panelCont = new JPanel();
    private JPanel highscoresPanel = new JPanel();
    private JPanel logsPanel = new JPanel();
    private JButton game = new JButton("Game");
    private JButton highscoresButton = new JButton("Highscores");
    private JButton logsButton = new JButton("Logs");

    private String currentPanel;

    public GameWindow(ConnectionHandler mh)
    {
        panelCont.setLayout(layout);

        setTitle("HappyWorld");
        setSize(new Dimension(800, 600));

        highscoresPanel.add(new JLabel("Highscores"));
        logsPanel.add(new JLabel("Logs"));

        panelCont.add(new Board(mh), "Game");
        panelCont.add(new Highscores(), "Highscores");
        panelCont.add(new Logs(mh), "Logs");

        show("Game");

        add(panelCont);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(options(), BorderLayout.PAGE_START);
        add(panelCont);
    }

    public boolean gwHasFocus()
    {
        return this.hasFocus();
    }

    private void show(String pan) {
        layout.show(panelCont, pan);
        currentPanel = pan;
    }

    private JMenuBar options()
    {
        JMenuBar options = new JMenuBar();
        options.setLayout(new GridLayout(1,5));

        options.add(game);
        options.add(highscoresButton);
        options.add(logsButton);

        game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show("Game");
            }
        });
        highscoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leaveGame())
                {
                    show("Highscores");
                }
            }
        });
        logsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leaveGame())
                {
                    show("Logs");
                }
            }
        });


        return options;
    }

    private boolean leaveGame()
    {
        if (currentPanel.equals("Game"))
        {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to leave the game?", "Leave Game?", JOptionPane.YES_NO_OPTION);
            if (dialogResult != JOptionPane.YES_OPTION)
            {
                return false;
            }
        }
        return true;
    }
}
