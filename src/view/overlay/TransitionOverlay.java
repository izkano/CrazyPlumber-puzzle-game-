package view.overlay;

import view.Button;
import model.Map;
import model.GameMode;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TransitionOverlay extends Overlay {

    private BufferedImage victoryWindow;

    private view.Button nextLevelBtn;
    private view.Button retryBtn;
    private view.Button mainMenuBtn;
    private view.Button comingSoonBtn;

    private int screenWidth;
    private int screenHeight;
    private int minimumMoves;
    private int playerMoves;
    private int scale;
    private GamePanel gp;

    public TransitionOverlay(int screenWidth, int screenHeight, int scale, GamePanel gp) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scale = scale;
        this.gp = gp;
        
        loadAssets();
    }


    public view.Button getNextLevelButton() {
        return nextLevelBtn;
    }

    public view.Button getRetryButton() {
        return retryBtn;
    }

    public Button getMainMenuButton() {
        return mainMenuBtn;
    }
    public Button getComingSoonBtn() {
        return comingSoonBtn;
    }


    public void loadAssets() {
        try {
            victoryWindow = ImageIO.read(getClass().getResourceAsStream("/menu/transition/victory_window.png"));;
        } catch (IOException e) {
            e.printStackTrace();
        }

        int buttonCenterXtransition = (screenWidth / 2) - 246*scale/3;
        int startYtransition = screenHeight / 2;
        int gapYtransition = 100*scale/3;

        // initialisation avec les images pour la transition
        this.nextLevelBtn = new view.Button("/menu/transition/buttons/nextLevel", buttonCenterXtransition, startYtransition, scale);
        this.retryBtn = new view.Button("/menu/transition/buttons/replay", buttonCenterXtransition,startYtransition + gapYtransition, scale);
        this.mainMenuBtn = new view.Button("/menu/transition/buttons/mainMenu", buttonCenterXtransition, startYtransition + 2*gapYtransition, scale);
        this.comingSoonBtn = new view.Button("/menu/transition/buttons/comingsoon", buttonCenterXtransition, startYtransition, scale);

    }
    public boolean isLastLevel(int level) {
        return level == (gp.play.getAmountLevel()[gp.play.getGameMode().getValue()]); 
    }


    public void resetButtons() {
        nextLevelBtn.setMouseOver(false);
        mainMenuBtn.setMouseOver(false);
        retryBtn.setMouseOver(false);
    }


    private int calculateStars() {
        

        if (minimumMoves >= playerMoves) {
            return 3;
        } else if (minimumMoves * 2 > playerMoves ) {
            return 2;
        } else {
            return 1;
        }
    }


    public void drawStars(Graphics2D g2, int numStarsGained, int totalStars) {
        try {
            BufferedImage starImage = ImageIO.read(getClass().getResourceAsStream("/menu/transition/starsss.png"));
            BufferedImage emptyStarImage = ImageIO.read(getClass().getResourceAsStream("/menu/transition/emptyStar.png"));

            starImage = resizeImage(starImage, 150*scale/3, 150*scale/3);
            emptyStarImage = resizeImage(emptyStarImage, 150*scale/3, 150*scale/3);

            int starWidth = starImage.getWidth();
            int starHeight = starImage.getHeight();

            int startX = (screenWidth - (totalStars * starWidth)) / 2;
            int startY = (screenHeight - starHeight) / 2 - 230*scale/3;

            for (int i = 0; i < numStarsGained; i++) {
                g2.drawImage(starImage, startX + i * starWidth, startY, null);
            }

            for (int i = numStarsGained; i < totalStars; i++) {
                g2.drawImage(emptyStarImage, startX + i * starWidth, startY, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }


    private void drawMessage(Graphics2D g2, int numStars) {
        String message1;
        String message2;

        try {
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/menu/Retro_Gaming.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(retroFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        switch (numStars) {
            case 1:
                message1 = "Vous avez obtenu 1 étoile !";
                message2 = "Vous pouvez faire mieux !";
                break;
            case 2:
                message1 = "Vous avez obtenu 2 étoile !";
                message2 = "Pas mal du tout !";
                break;
            case 3:
                message1 = "Vous avez obtenu 3 étoile !";
                message2 = "Impressionant !";
                break;
            default:
                message1 = "Meilleure chance la prochaine fois !";
                message2 = "Meilleure chance la prochaine fois !";
                break;
        }

        Font font = new Font("Retro Gaming", Font.BOLD, 23*scale/3);
        g2.setFont(font);
        g2.setColor(Color.BLACK);

        FontMetrics metrics = g2.getFontMetrics(font);
        int messageWidth = metrics.stringWidth(message1);
        int messageX = (screenWidth - messageWidth) / 2;
        int messageY = screenHeight / 2 - 100*scale/3*scale/3;
        int message2Width = metrics.stringWidth(message2)*scale/3;
        int message2X = (screenWidth - message2Width) / 2;
        g2.drawString(message1, messageX, messageY);
        g2.drawString(message2, message2X-50*scale/3, messageY+50*scale/3);
    }
    public void setMoves(int minimumMoves, int playerMoves) {
        this.minimumMoves = minimumMoves;
        this.playerMoves = playerMoves;
    }


    public void draw(Graphics2D g2, int currentLevel, GameMode gameMode) {
        g2.drawImage(victoryWindow, screenWidth/2 - 261*scale/3, screenHeight/2-400*scale/3,victoryWindow.getWidth()*scale/3,
        victoryWindow.getHeight()*scale/3, null);

        int stars = calculateStars();
        drawStars(g2, stars, 3);

        if ((currentLevel)>=12 || (gameMode == GameMode.PLAYBUILD && currentLevel>=gp.play.getAmountLevel()[4])) {
            comingSoonBtn.draw(g2);
        } else {
            nextLevelBtn.draw(g2);
        }
        retryBtn.draw(g2);
        mainMenuBtn.draw(g2);
        drawMessage(g2, stars);
    }


    @Override
    void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}
