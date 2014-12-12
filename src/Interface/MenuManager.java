package Interface;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by João on 03/12/2014.
 */
public class MenuManager extends JFrame {

    public static CardLayout cardlayout;
    public static JPanel cards;
    private static MasterMenuChart mmch;

    public MenuManager() {
        super("Trust in Information Sources");

        setLookAndFeel();

        cardlayout=new CardLayout();
        cards = new JPanel(new CardLayout());;

        InitialMenu im=new InitialMenu();
        MasterMenu mm=new MasterMenu();
        WaitMenu wm=new WaitMenu();
        MasterMenuConsole mmc=new MasterMenuConsole();
        mmch = new MasterMenuChart();
        AgentMenu am=new AgentMenu();
        JoinMenu jm=new JoinMenu();

        cards.add(im, "InitialMenu");
        cards.add(mm, "MasterMenu");
        cards.add(wm, "WaitMenu");
        cards.add(mmc, "MasterMenuConsole");
        cards.add(mmch, "MasterMenuChart");
        cards.add(am, "AgentMenu");
        cards.add(jm, "JoinMenu");

        cardlayout = (CardLayout) cards.getLayout();
        cardlayout.show(cards, "InitialMenu");

        add(cards);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static MasterMenuChart getMasterMenuChart(){
        return mmch;
    }

    public void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        UIManager UI=new UIManager();
        UI.put("OptionPane.background", new Color(43,43,43));
        UI.put("Panel.background", new Color(43,43,43));
        UI.put("Label.foreground", Color.WHITE);
        UI.put("OptionPane.messageForeground", Color.WHITE);
    }
}
