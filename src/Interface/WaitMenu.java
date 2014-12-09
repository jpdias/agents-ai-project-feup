package Interface;

import Run.Run;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by João on 05/12/2014.
 */
public class WaitMenu extends JPanel {

    private JButton next;

    public WaitMenu(){

        JPanel wait_panel = new JPanel();
        JLabel wait = new JLabel("Waiting for new Agents");
        wait.setHorizontalAlignment(JLabel.CENTER);
        wait.setFont(new Font("Consolas", Font.BOLD, 30));
        wait_panel.add(wait);

        JPanel next_panel = new JPanel();
        next = new JButton("Next");
        next.addActionListener(new Handler());
        next_panel.add(next);

        JPanel geral = new JPanel();
        geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral.add(wait_panel);
        geral.add(next_panel);

        setLayout(new GridBagLayout());
        add(geral);
    }



    private class Handler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == next){
                MenuManager.cardlayout.show(MenuManager.cards,"MasterMenu");
            }
        }

    }
}
