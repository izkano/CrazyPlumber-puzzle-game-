package view.overlay;

import java.awt.*;

import model.GameMode;
import view.Button;

public class SelectOverlay extends Overlay {

    private Button backBtn;

	private Button[][] level = new Button[3][];
    private Button[][] locked = new Button[3][];

    private GameMode gameMode;

    private int screenWidth;
    private int screenHeight;
    private int scale;

    private int[] amountLevel;
    private boolean[][] unlocked;


	public SelectOverlay(int screenWidth, int screenHeight, int[] amountLevel, boolean[][] unlocked, int scale) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.amountLevel = amountLevel;
        this.unlocked = unlocked;
        this.scale = scale;

		loadAssets();
	}


    public Button[][] getLevelButton() {
        return level;
    }

    public Button getBackButton() {
        return backBtn;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }


	public void loadAssets() {
        int startYSelectLevel = screenHeight / 4; 
		int gapYLevel = 125*scale/3; 
        int gapXLevel = 100*scale/3; 


        for (int k = 0; k < 3; k++) {
            int taille = amountLevel[k];
            level[k] = new Button[taille];
            locked[k] = new Button[taille];
            int j = 0;
            int l = 0;
            for (int i = 0; i < taille; i++) {
                if (j % 5 == 0) j = 0;
                if (i%5==0 && i!=0) l++;
                level[k][i] = new Button("/menu/boxLevel", 250*scale/3 + gapXLevel * j, startYSelectLevel+l*gapYLevel, scale);
                locked[k][i] = new Button("/menu/locked", 250*scale/3 + gapXLevel * j, startYSelectLevel+l*gapYLevel, scale);
                j++;
            }
        }

        backBtn = new Button("/menu/previous", 15*scale/3,15*scale/3, scale);
    }
	
	
	public void resetButtons() {
        for (Button e: level[0]) {
            e.setMouseOver(false);
        }
	}


    public void update(int[] amountLevel, boolean[][] unlocked) {
        this.amountLevel = amountLevel;
        this.unlocked = unlocked;
    }

    
	public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Retro Gaming", Font.PLAIN, 50*scale/3));
        for (int i = 0; i<level[0].length;i++){
            if (unlocked[0][i]==true){
                level[0][i].draw(g2);
                if (i==0){
                    g2.drawString(String.valueOf(i + 1), level[0][i].getX()+22*scale/3, level[0][i].getY()+55*scale/3);
                }
                else if (i<9){
                    g2.drawString(String.valueOf(i + 1), level[0][i].getX()+15*scale/3, level[0][i].getY()+55*scale/3);
                }
                else if (i==10){
                    g2.drawString(String.valueOf(i + 1), level[0][i].getX()+11*scale/3, level[0][i].getY()+55*scale/3);
                }
                else if (i>=9 && i<19){
                    g2.drawString(String.valueOf(i + 1), level[0][i].getX()+2*scale/3, level[0][i].getY()+55*scale/3);
                }
            }
            
            else {
                locked[0][i].draw(g2);
            }
        }
        
        backBtn.draw(g2);
	}
}
