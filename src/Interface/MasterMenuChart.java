package Interface;

import Run.Master;
import com.googlecode.charts4j.*;
import jade.core.AID;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by João on 03/12/2014.
 */
public class MasterMenuChart extends JPanel {

    private ArrayList<String> algorithms = new ArrayList<String>();
    private ArrayList<Integer> right_answers = new ArrayList<Integer>();
    private ArrayList<Integer> wrong_answers = new ArrayList<Integer>();

    public MasterMenuChart(){}

    public void init(){

        initValues();
        
        BarChartPlot right = Plots.newBarChartPlot(Data.newData(right_answers), Color.newColor("549654"), "Right");
        BarChartPlot wrong = Plots.newBarChartPlot(Data.newData(wrong_answers), Color.newColor("FF6A5C"), "Wrong");

        // Instantiating chart.
        com.googlecode.charts4j.BarChart chart = GCharts.newBarChart(right, wrong);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 13, AxisTextAlignment.CENTER);
        AxisLabels score = AxisLabelsFactory.newAxisLabels("Answers", 100);
        score.setAxisStyle(axisStyle);
        AxisLabels year = AxisLabelsFactory.newAxisLabels("Players Algorithms", 50.0);
        year.setAxisStyle(axisStyle);

        // Adding axis info to chart.
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, Master.numberofquestions));
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(algorithms));
        chart.addYAxisLabels(score);
        chart.addXAxisLabels(year);

        chart.setSize(550, 350);
        chart.setBarWidth(30);
        chart.setSpaceWithinGroupsOfBars(30);
        chart.setTitle("Results", Color.WHITE, 16);
        chart.setGrid(Master.numberofquestions, 10, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("2B2B2B")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("565656"), 100);
        chart.setAreaFill(fill);

        JLabel charts=null;
        try {
            charts = new JLabel(new ImageIcon(ImageIO.read(new URL(chart.toURLString()))));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setBackground( new java.awt.Color(43,43,43));
        add(charts);
    }

    public void initValues(){
        Enumeration<AID> pl = Master.results.keys();
        while(pl.hasMoreElements()) {
            AID x =  pl.nextElement();
            String str = x.getLocalName();
            int pnts =  Master.results.get(x);
            algorithms.add(str);
            right_answers.add(pnts*10);
            wrong_answers.add((Master.numberofquestions-pnts)*10);
        }
    }
}
