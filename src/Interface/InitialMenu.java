package Interface;

import Run.Run;
import jade.wrapper.StaleProxyException;
import javafx.scene.text.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by João on 03/12/2014.
 */
public class InitialMenu extends JPanel {

    private JButton startMaster, startAgent;

    public InitialMenu(){

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setOpaque(false);
        panel2.add(createJLabel("Trust"), BorderLayout.NORTH);
        panel2.add(createJLabel("in"), BorderLayout.CENTER);
        panel2.add(createJLabel("Information Sources"), BorderLayout.SOUTH);

        JPanel panel0 = new JPanel();
        panel0.setOpaque(false);
        startMaster =new JButton("Start Master");
        startMaster.setBackground(new Color(86,86,86));
        startMaster.addActionListener(new Handler());
        panel0.add(startMaster);

        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        startAgent=new JButton("Start Agent");
        startAgent.addActionListener(new Handler());
        panel1.add(startAgent);

        JPanel down_panel = new JPanel(new GridLayout(1,2));
        down_panel.setOpaque(false);
        down_panel.add(panel0);
        down_panel.add(panel1);

        JPanel geral = new JPanel();
        geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral.setOpaque(false);
        geral.add(panel2);
        geral.add(down_panel);

        setLayout(new GridBagLayout());
        add(geral);
    }

    public JLabel createJLabel(String text){
        JLabel t = new JLabel(text);
        t.setHorizontalAlignment(JLabel.CENTER);
        t.setFont(new Font("Consolas", Font.BOLD, 30));
        return t;
    }

    private class Handler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startAgent){
                MenuManager.cardlayout.show(MenuManager.cards,"AgentMenu");
            }else if(e.getSource()==startMaster){
                try {
                    Run.initRMA();
                    Run.addExperts();
                    Run.addPlayers();
                } catch (StaleProxyException e1) {
                    e1.printStackTrace();
                }
                MenuManager.cardlayout.show(MenuManager.cards,"MasterMenu");
            }
        }

    }
}
