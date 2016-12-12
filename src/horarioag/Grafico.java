/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horarioag;

import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico {

    DefaultCategoryDataset dataset;
    JFreeChart chart;
    CategoryPlot p;
    ChartFrame frame;

    public Grafico() {
        
    }

    public void gerarGraficoMedia(ArrayList<Float> media) {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart("", "Geração", "Aptidão", dataset, PlotOrientation.VERTICAL, false, false, false);
        p = chart.getCategoryPlot();
        frame = new ChartFrame("Média de Aptidão por Geração", chart);
        
        p.setRangeGridlinePaint(Color.black);

        frame.setVisible(true);
        frame.setSize(800, 600);

        for (int i = 0; i < media.size(); i++) {
            dataset.setValue(media.get(i), "", i+"");
        }
    }

    public void gerarGraficoMelhor(ArrayList<Integer> melhor) {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart("", "Geração", "Aptidão", dataset, PlotOrientation.VERTICAL, false, false, false);
        p = chart.getCategoryPlot();
        frame = new ChartFrame("Melhor Aptidão por Geração", chart);
        
        p.setRangeGridlinePaint(Color.black);
        
        frame.setVisible(true);
        frame.setSize(800, 600);

        for (int i = 0; i < melhor.size(); i++) {
            dataset.setValue(melhor.get(i), "", i+"");
        }
    }
}
