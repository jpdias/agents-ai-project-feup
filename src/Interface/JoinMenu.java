package Interface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by João on 03/12/2014.
 */
public class JoinMenu extends JPanel {

    public JoinMenu(){
        JLabel t = new JLabel("Joined...");
        t.setHorizontalAlignment(JLabel.CENTER);
        t.setFont(new Font("Consolas", Font.BOLD, 30));

        setLayout(new GridBagLayout());

        add(t);
    }
}
