package Interface;

import Run.Run;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by João on 03/12/2014.
 */
public class AgentMenu extends JPanel {

    private JTextField ipText,portText;
    private JButton join;
    private ArrayList<JCheckBox> checkboxes=new ArrayList<JCheckBox>();
    private final int num_players=4;

    public AgentMenu(){

        JPanel ip_panel = new JPanel();
        JLabel ipLabel = new JLabel("IP:");
        ipText = new JTextField(21);
        ip_panel.add(ipLabel);
        ip_panel.add(ipText);


        JPanel port_panel = new JPanel();
        JLabel portLabel = new JLabel("Port:");
        portText = new JTextField(20);
        portText.setText("1099");
        port_panel.add(portLabel);
        port_panel.add(portText);

        join = new JButton("Join");
        join.addActionListener(new Handler());

        //adding checkbox players

        JPanel players =new JPanel(new GridLayout(num_players, 1));
        createJCheckBox(players, "DUMMY");
        createJCheckBox(players, "RANDOM");
        createJCheckBox(players, "BETA");
        createJCheckBox(players, "FIRE");
        TitledBorder players_title = new TitledBorder("Players");
        players_title.setTitleColor(Color.WHITE);
        players.setBorder(players_title);

        JPanel geral1 = new JPanel();
        geral1.setLayout(new BoxLayout(geral1, BoxLayout.Y_AXIS));
        geral1.add(ip_panel);
        geral1.add(port_panel);
        geral1.add(join);

        JPanel geral2 = new JPanel();
        geral2.setLayout(new BoxLayout(geral2, BoxLayout.X_AXIS));
        //geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral2.add(geral1);
        geral2.add(players);

        setLayout(new GridBagLayout());
        add(geral2);
    }

    public void createJCheckBox(JPanel panel, String name){
        JCheckBox cb = new JCheckBox(name);
        cb.setForeground(Color.WHITE);
        checkboxes.add(cb);
        cb.setOpaque(false);
        panel.add(cb);
    }


    private class Handler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == join){
                for (int i=0; i< checkboxes.size();i++){
                    if(checkboxes.get(i).isSelected()){
                        Run.initRemote(ipText.getText(), Integer.parseInt(portText.getText()), i);
                    }
                    //MenuManager.cardlayout.show(MenuManager.cards,"MasterMenu");
                }
            }
        }

    }
}
