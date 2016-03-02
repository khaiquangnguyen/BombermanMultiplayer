/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 13, 2015
 * Time: 9:19:06 PM
 *
 * Project: csci205_final_project
 * Package: GameUtility
 * File: Resources
 * Description:
 *
 * ****************************************
 */
package GameUtility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Khoi Le
 */
public class Resources {

    private HashMap<String, BufferedImage> playerImages = new HashMap(32);
    private HashMap<String, BufferedImage> player1Images = new HashMap(32);
    private HashMap<String, BufferedImage> player2Images = new HashMap(32);
    private HashMap<String, BufferedImage> player3Images = new HashMap(32);
    private HashMap<String, BufferedImage> monsterImages = new HashMap();
    private HashMap<String, BufferedImage> wallImages = new HashMap();
    private HashMap<String, BufferedImage> explosionImages = new HashMap(35);
    private BufferedImage[] bomb = new BufferedImage[4];
    private BufferedImage[] wallVanishImages = new BufferedImage[5];
    private BufferedImage[] monsterVanishImages = new BufferedImage[5];
    private BufferedImage[] playerVanishImages = new BufferedImage[5];
    private BufferedImage[] player1VanishImages = new BufferedImage[5];
    private BufferedImage[] player2VanishImages = new BufferedImage[5];
    private BufferedImage[] player3VanishImages = new BufferedImage[5];
    private BufferedImage background;
    private HashMap<String, BufferedImage> icons = new HashMap();
    private AudioPlayer backgroundMusic;
    private HashMap<String, AudioPlayer> soundEffects = new HashMap();
    private HashMap<String, BufferedImage> menu = new HashMap();

    public Resources() {
        //Player 0
        loadPlayerImages("R");
        loadPlayerImages("L");
        loadPlayerImages("F");
        loadPlayerImages("B");
        //Player 1
        loadPlayer1Images("R");
        loadPlayer1Images("L");
        loadPlayer1Images("F");
        loadPlayer1Images("B");
        //Player 2
        loadPlayer2Images("R");
        loadPlayer2Images("L");
        loadPlayer2Images("F");
        loadPlayer2Images("B");
        //Player 3
        loadPlayer3Images("R");
        loadPlayer3Images("L");
        loadPlayer3Images("F");
        loadPlayer3Images("B");
        //Monster
        loadMonsterImages("R");
        loadMonsterImages("L");
        loadMonsterImages("F");
        loadMonsterImages("B");
        //Explosion
        loadExplosionImages("C");
        loadExplosionImages("H");
        loadExplosionImages("V");
        loadExplosionImages("U");
        loadExplosionImages("D");
        loadExplosionImages("R");
        loadExplosionImages("L");
        //Background music
        loadBackgroundMusic();
        //Background
        loadBackground();
        //Wall & Brick
        loadWallImages();
        //Sound effects
        loadSoundEffects();
        //Menu
        loadMenu();
        //Icons
        loadIcons();
        //Bomb
        loadBomb();
        //Wall vanish animation
        loadWallVanishImages();
        //Monster vanish animation
        loadMonsterVanishImages();
        //Player 0 vanish animation
        loadPlayerVanishImages();
        loadPlayer1VanishImages();
        loadPlayer2VanishImages();
        loadPlayer3VanishImages();
    }

    public void reloadSound() {
        soundEffects.replace("placeBomb", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/PlaceBomb.wav"));
        soundEffects.replace("explode", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Explode.wav"));
        soundEffects.replace("background", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Background.wav"));
        soundEffects.replace("win", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Win.wav"));
        soundEffects.replace("lose", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Lose.wav"));
        soundEffects.replace("start", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Start.wav"));
        soundEffects.replace("die", new AudioPlayer(System.getProperty(
                             "user.dir") + "/res/Sound/Die.wav"));
    }

    private void loadBomb() {
        for (int i = 0; i < 4; i++) {
            try {
                bomb[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Bomb/Bomb%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadWallVanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                wallVanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Wall/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadMonsterVanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                monsterVanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Monster/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayerVanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                playerVanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Player/0/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer1VanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                player1VanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Player/1/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer2VanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                player2VanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Player/2/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer3VanishImages() {
        for (int i = 0; i < 5; i++) {
            try {
                player3VanishImages[i] = ImageIO.read(new File(String.format(
                        "%1$s/res/Animation/Vanish/Player/3/%2$s.png",
                        System.getProperty("user.dir"), i + 1)));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayerImages(String direction) {
        for (int i = 0; i < 8; i++) {
            try {
                String fileName = direction + (i + 1);
                playerImages.put(fileName, ImageIO.read(new File(String.format(
                                 "%1$s/res/Animation/Player/0/%2$s.png",
                                 System.getProperty("user.dir"), fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer1Images(String direction) {
        for (int i = 0; i < 8; i++) {
            try {
                String fileName = direction + (i + 1);
                player1Images.put(fileName, ImageIO.read(new File(String.format(
                                  "%1$s/res/Animation/Player/1/%2$s.png",
                                  System.getProperty("user.dir"), fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer2Images(String direction) {
        for (int i = 0; i < 8; i++) {
            try {
                String fileName = direction + (i + 1);
                player2Images.put(fileName, ImageIO.read(new File(String.format(
                                  "%1$s/res/Animation/Player/2/%2$s.png",
                                  System.getProperty("user.dir"), fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadPlayer3Images(String direction) {
        for (int i = 0; i < 8; i++) {
            try {
                String fileName = direction + (i + 1);
                player3Images.put(fileName, ImageIO.read(new File(String.format(
                                  "%1$s/res/Animation/Player/3/%2$s.png",
                                  System.getProperty("user.dir"), fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadExplosionImages(String direction) {
        for (int i = 0; i < 5; i++) {
            try {
                String fileName = direction + (i + 1);
                explosionImages.put(fileName, ImageIO.read(new File(
                                    String.format(
                                            "%1$s/res/Animation/Explosion/%2$s.png",
                                            System.getProperty("user.dir"),
                                            fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadMonsterImages(String direction) {
        for (int i = 0; i < 4; i++) {
            try {
                String fileName = direction + (i + 1);
                monsterImages.put(fileName, ImageIO.read(new File(String.format(
                                  "%1$s/res/Animation/Monster/%2$s.png",
                                  System.getProperty("user.dir"), fileName))));
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                                null, ex);
            }
        }
    }

    private void loadBackgroundMusic() {
        backgroundMusic = new AudioPlayer(
                System.getProperty("user.dir") + "/res/Sound/MenuMusic.wav");
    }

    private void loadBackground() {
        try {
            background = ImageIO.read(new File(
                    System.getProperty("user.dir") + "/res/Animation/Background.png"));
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null,
                                                            ex);
        }
    }

    private void loadWallImages() {
        try {
            wallImages.put("brick", ImageIO.read(new File(System.getProperty(
                           "user.dir") + "/res/Animation/Wall/Brick.png")));
            wallImages.put("wall", ImageIO.read(new File(System.getProperty(
                           "user.dir") + "/res/Animation/Wall/Wall.png")));
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null,
                                                            ex);
        }
    }

    private void loadSoundEffects() {
        soundEffects.put("placeBomb", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/PlaceBomb.wav"));
        soundEffects.put("explode", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Explode.wav"));
        soundEffects.put("background", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Background.wav"));
        soundEffects.put("win", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Win.wav"));
        soundEffects.put("lose", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Lose.wav"));
        soundEffects.put("start", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Start.wav"));
        soundEffects.put("die", new AudioPlayer(System.getProperty(
                         "user.dir") + "/res/Sound/Die.wav"));

    }

    private void loadIcons() {
        try {
            icons.put("monster", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Monster.png")));
            icons.put("player0", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Player0.png")));
            icons.put("player1", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Player1.png")));
            icons.put("player2", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Player2.png")));
            icons.put("player3", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Player3.png")));
            icons.put("bombRange", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/BombRange.png")));
            icons.put("bombLimit", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/BombLimit.png")));
            icons.put("shield", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Shield.png")));
            icons.put("speed", ImageIO.read(new File(System.getProperty(
                      "user.dir") + "/res/Animation/Icon/Speed.png")));
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null,
                                                            ex);
        }
    }

    private void loadMenu() {
        try {
            menu.put("background", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/Background.png")));
            menu.put("backgroundHost", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/BackgroundHost.png")));
            menu.put("normalGame", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/NormalGame.png")));
            menu.put("normalGameSelected", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/NormalGameSelected.png")));
            menu.put("battleGame", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/BattleGame.png")));
            menu.put("battleGameSelected", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/BattleGameSelected.png")));
            menu.put("exit", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/Exit.png")));
            menu.put("exitSelected", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/ExitSelected.png")));
            menu.put("yes", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/Yes.png")));
            menu.put("yesSelected", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/YesSelected.png")));
            menu.put("no", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/No.png")));
            menu.put("noSelected", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/NoSelected.png")));
            menu.put("client", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/Client.png")));
            menu.put("back", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/Back.png")));
            menu.put("backSelected", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/BackSelected.png")));
            menu.put("clientBack", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/ClientBack.png")));
            menu.put("clientBackSelected", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/ClientBackSelected.png")));
            menu.put("clientEnter", ImageIO.read(new File(System.getProperty(
                     "user.dir") + "/res/Animation/Menu/ClientEnter.png")));
            menu.put("clientEnterSelected", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/ClientEnterSelected.png")));
            menu.put("win", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Win.png")));
            menu.put("lose", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Lose.png")));
            menu.put("pregame", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/PreGame.png")));
            menu.put("player0", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Player0.png")));
            menu.put("player1", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Player1.png")));
            menu.put("player2", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Player2.png")));
            menu.put("player3", ImageIO.read(new File(
                     System.getProperty(
                             "user.dir") + "/res/Animation/Menu/Player3.png")));
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null,
                                                            ex);
        }
    }

    public HashMap<String, BufferedImage> getMenu() {
        return menu;
    }

    public HashMap<String, BufferedImage> getPlayerImages() {
        return playerImages;
    }

    public HashMap<String, BufferedImage> getMonsterImages() {
        return monsterImages;
    }

    public HashMap<String, BufferedImage> getWallImages() {
        return wallImages;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public AudioPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    public HashMap<String, AudioPlayer> getSoundEffects() {
        return soundEffects;
    }

    public HashMap<String, BufferedImage> getIcons() {
        return icons;
    }

    public BufferedImage[] getBomb() {
        return bomb;
    }

    public HashMap<String, BufferedImage> getExplosionImages() {
        return explosionImages;
    }

    public HashMap<String, BufferedImage> getPlayer1Images() {
        return player1Images;
    }

    public HashMap<String, BufferedImage> getPlayer2Images() {
        return player2Images;
    }

    public HashMap<String, BufferedImage> getPlayer3Images() {
        return player3Images;
    }

    public BufferedImage[] getWallVanishImages() {
        return wallVanishImages;
    }

    public BufferedImage[] getMonsterVanishImages() {
        return monsterVanishImages;
    }

    public BufferedImage[] getPlayerVanishImages() {
        return playerVanishImages;
    }

}
