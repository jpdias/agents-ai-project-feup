package Interface;

import Run.*;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Run.Run.startExperts;

/**
 * Created by João on 01/12/2014.
 */
public class MasterMenu extends JPanel {
    private JButton start;
    private JTextField num_questions;

    private final int num_players=4, num_experts=6;
    private ArrayList<JCheckBox>checkboxes=new ArrayList<JCheckBox>();
    public static ArrayList<Boolean>checkboxes_selected=new ArrayList<Boolean>();

    public MasterMenu(){

        JPanel experts = new JPanel(new GridLayout(num_experts,1));
        JPanel players =new JPanel(new GridLayout(num_players, 1));
        JPanel buttons = new JPanel();

        //adding checkbox experts
        createJCheckBox(experts, "Sports-100%");
        createJCheckBox(experts, "Science-100%");
        createJCheckBox(experts, "History-100%");
        createJCheckBox(experts, "Arts-100%");
        createJCheckBox(experts, "Places-100%");
        createJCheckBox(experts, "Random-100%");
        TitledBorder experts_title = new TitledBorder("Experts");
        experts_title.setTitleColor(Color.WHITE);
        experts.setBorder(experts_title);

        //adding checkbox players
        createJCheckBox(players, "FIRE");
        createJCheckBox(players, "DUMMY");
        createJCheckBox(players, "RANDOM");
        createJCheckBox(players, "BETA");
        TitledBorder players_title = new TitledBorder("Players");
        players_title.setTitleColor(Color.WHITE);
        players.setBorder(players_title);

        //adding button start
        JLabel question = new JLabel("How many questions?");
        buttons.add(question);
        num_questions = new JTextField(10);
        buttons.add(num_questions);
        start = new JButton("Next");
        start.addActionListener(new Handler());
        buttons.add(start);

        JPanel up_panel = new JPanel();
        up_panel.setOpaque(false);
        up_panel.add(players);
        up_panel.add(experts);

        JPanel geral = new JPanel();
        geral.setOpaque(false);
        geral.setLayout(new BoxLayout(geral, BoxLayout.Y_AXIS));
        geral.add(up_panel);
        geral.add(buttons);

        setLayout(new GridBagLayout());
        add(geral);
    }

    public void createJCheckBox(JPanel panel, String name){
        JCheckBox cb = new JCheckBox(name);
        cb.setForeground(Color.WHITE);
        checkboxes.add(cb);
        cb.setOpaque(false);
        panel.add(cb);
    }

    public boolean getSelectedCheckBox(int i){
        return checkboxes_selected.get(i);
    }

    private class Handler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == start){
                try {
                    for(int i=0; i<checkboxes.size();i++){
                        checkboxes_selected.add(checkboxes.get(i).isSelected());
                    }
                    MenuManager.cardlayout.show(MenuManager.cards,"WaitMenu");
                    int numberofquestions = Integer.parseInt(num_questions.getText());
                    Run.addMaster(numberofquestions);
                    Run.addExperts();
                    Run.addPlayers();
                    Run.startExperts();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"You didn't introduce a number","Warning",JOptionPane.WARNING_MESSAGE);
                } catch (StaleProxyException e1) {
                    e1.printStackTrace();
                } finally{
                    num_questions.setText("");
                }
            }
        }

    }
}
