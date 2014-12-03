package Interface;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by João on 03/12/2014.
 */
public class MasterMenuConsole extends JPanel {

    private JButton showCharts;

    public MasterMenuConsole(){

        setBackground( new Color(43,43,43));

        JTextArea ta=new JTextArea(23,65);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 10));
        redirectConsoleTo(ta);
        DefaultCaret caret = (DefaultCaret)ta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scroll =new JScrollPane(ta);
        scroll.createVerticalScrollBar();

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        showCharts = new JButton("Show Results");
        showCharts.setBackground(new Color(86,86,86));
        showCharts.addActionListener(new Handler());
        panel.add(showCharts);

        add(scroll, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
    }

    public static void redirectConsoleTo(final JTextArea textarea)
    {
        PrintStream out = new PrintStream(new ByteArrayOutputStream()
        {
            public synchronized void flush() throws IOException
            {
                textarea.setText(toString());
            }
        }
                , true);
        System.setErr(out);
        System.setOut(out);
    }

    private class Handler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==showCharts){
                MenuManager.cardlayout.show(MenuManager.cards,"MasterMenuChart");
                MenuManager.getMasterMenuChart().init();
            }
        }

    }
}
