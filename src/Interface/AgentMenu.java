package Interface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by João on 03/12/2014.
 */
public class AgentMenu extends JPanel {

    public AgentMenu(){

        JPanel ipLabel_panel = new JPanel();
        JLabel ipLabel = new JLabel("Insert here the ip:");
        ipLabel_panel.add(ipLabel);

        JPanel ipText_panel = new JPanel();
        JTextField ipText = new JTextField(20);
        ipText_panel.add(ipText);

        JPanel ipButton_panel = new JPanel();
        JButton ipButton = new JButton("Join");
        ipButton_panel.add(ipButton);

        setLayout(new GridBagLayout());

        add(ipLabel_panel);
        add(ipText_panel);
        add(ipButton_panel);
    }
}
