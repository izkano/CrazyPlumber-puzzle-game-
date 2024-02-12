package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cell {
    private int pipeType;
    private int orientation;
    private BufferedImage image;
    
    public Cell (int pipeType,int orientation){
        this.pipeType = pipeType;
        this.orientation = orientation;
        loadImage(pipeType);
    }
    
    private void loadImage(int pipeType) {
    	String path = null;
        switch(pipeType){
        	case 0 :
        		path = "res/pipes/none.png";
        		break;
            case 1:
            	path = "res/pipes/horizontal.png";
            	break;
            case 2:
                path = "res/pipes/curve.png";
                break;
            case 3:
                path = "res/pipes/cross.png";
                break;
            case 4:
            	path = "res/pipes/start.png";
            default :
            	break;	
        }
        
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getOrientation() {
        return orientation;
    }

    public int getPipeType() {
        return pipeType;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setPipeType(int pipeType) {
        this.pipeType = pipeType;
    }

    public BufferedImage getImage(){
        return image;
    }

    //Vraiment utile ?
    public void setImage(BufferedImage image){
        this.image = image;
    }
    
    public void rotate() {
    	if (pipeType == 0) return;
        orientation = (orientation + 1) % 4;
        
        int width = image.getWidth();
	    int height = image.getHeight();

	    BufferedImage dest = new BufferedImage(height, width, image.getType());

	    Graphics2D graphics2D = dest.createGraphics();

	    graphics2D.translate((height - width) / 2, (height - width) / 2);
	    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
	    graphics2D.drawRenderedImage(image, null);

	    image = dest;
    }

    public void drawCell(Graphics2D g2, int x, int y, int tileSize) {
    	if (pipeType != 0) {
    		g2.drawImage(image,x,y,tileSize,tileSize,null);
    	}
    }
}
