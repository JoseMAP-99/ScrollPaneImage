package com.fabiyjose.practise7scrollpane;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author José María y Fabián
 */
public class Canvas extends JPanel{
    
    private Mat actualImage = null;
    private int[] portionMax = new int[3];
    private int[] portionMin = new int[3];
    private int[] portionMean = new int[3]; 
    private int height;
    private int width;
    

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
    
    public BufferedImage getActualImage(){
        return (BufferedImage) HighGui.toBufferedImage(actualImage);
    }
        
    //0 Red, 1 Green, 2 Blue
    public String getValue(String max, int color){
        int res;
        switch(max){
            case "max":
                res = this.portionMax[color];
                break;
            case "min":
                res = this.portionMin[color];
                break;
            default:
                res = this.portionMean[color];
        }   
        return String.valueOf(res);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (this.actualImage != null){
            g.drawImage(this.getActualImage(), 0, 0, null);
        }   
    }
    
    public void loadImage(File img){  
        this.actualImage = null;
        this.revalidate();        
        this.actualImage = Imgcodecs.imread(img.getAbsolutePath());
        this.height = this.actualImage.height();
        this.width = this.actualImage.width();
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.repaint();
    }
    
    public void setValues(Point point, Dimension dim){        
        EstadisticasImagen imageSt = new EstadisticasImagen();
        try{
            imageSt.calculaEstadisticas(this.actualImage, point, dim);
        }catch (Exception ex){
            this.revalidate();
            imageSt.calculaEstadisticas(this.actualImage, point, dim);
        }
        this.portionMax = imageSt.getMaximo(); 
        this.portionMin = imageSt.getMinimo();
        this.portionMean = imageSt.getPromedio();
    }
}
