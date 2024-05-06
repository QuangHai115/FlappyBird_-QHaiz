package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.event.MouseEvent;

import Menu.*;

public class Handle extends GameScreen {
    private BufferedImage birds;
    private Animation bird_anim;
    private BufferedImage bg;
    private BufferedImage gameOverimg;

    private BufferedImage settingImg;
    private BufferedImage returnImg;

    private BufferedImage musicSprite;
    private BufferedImage musicOn;
    private BufferedImage musicOff;

    private BufferedImage soundSprite;
    private BufferedImage soundOn;
    private BufferedImage soundOff;

    private boolean showSettings = false;

    public static boolean turnonMusic = false;

    private boolean turnonSound = false;

    public static float g = 0.1f;

    public Bird bird;

    private PipeGroup pipeGroup;

    private int BEGIN_SCREEN = 0;
    private int GAMEPLAY_SCREEN = 1;
    private int GAMEOVER_SCREEN = 2;
    private int SETTING_SCREEN = 3;
    private int HIGHSCORE_SCREEN = 4;

    private int CurrentScreen = BEGIN_SCREEN;

    private int point = 0;

    public static Bird h = new Bird();

    public Handle() {
        super(800, 600);

        try {
            birds = ImageIO.read(new File("Stock/bird_sprite.png"));
            bg = ImageIO.read(new File("Stock/background.jpg"));
            gameOverimg = ImageIO.read(new File("Stock/gameOver2.png"));
            settingImg = ImageIO.read(new File("Stock/setting.png"));
            returnImg = ImageIO.read(new File("Stock/return.png"));

            musicSprite = ImageIO.read(new File("Stock/music.png"));
            musicOn = musicSprite.getSubimage(0, 0, 60, 60);
            musicOff = musicSprite.getSubimage(60, 0, 60, 60);

            soundSprite = ImageIO.read(new File("Stock/sound.png"));
            soundOn = soundSprite.getSubimage(0, 0, 60, 60);
            soundOff = soundSprite.getSubimage(60, 0, 60, 60);

        } catch (IOException e) {

            e.printStackTrace();
        }

        bird_anim = new Animation(70);
        AFrameOnImage f;
        f = new AFrameOnImage(0, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);

        bird = new Bird(100, 250, 50, 50);
        pipeGroup = new PipeGroup();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (CurrentScreen == SETTING_SCREEN) {
                    if (e.getX() >= 370 && e.getX() <= 370 + musicOn.getWidth() &&
                            e.getY() >= 400 && e.getY() <= 400 + musicOn.getHeight()) {
                        turnonMusic = !turnonMusic;
                    }

                    if (e.getX() >= 370 && e.getX() <= 370 + soundOn.getWidth() &&
                            e.getY() >= 320 && e.getY() <= 320 + soundOn.getHeight()) {
                        turnonSound = !turnonSound;

                        if (!turnonSound) {
                            bird.soundTrack.playLoop();
                        } else {
                            bird.soundTrack.stop();
                        }
                    }
                    if (e.getX() >= 600 && e.getX() <= 600 + returnImg.getWidth() &&
                            e.getY() >= 30 && e.getY() <= 30 + returnImg.getHeight()) {
                        CurrentScreen = BEGIN_SCREEN;
                    }
                }
                if (CurrentScreen == BEGIN_SCREEN) {
                    if (e.getX() >= 100 && e.getX() <= 100 + settingImg.getWidth() &&
                            e.getY() >= 60 && e.getY() <= 60 + settingImg.getHeight() &&
                            CurrentScreen == BEGIN_SCREEN) {
                        showSettings = !showSettings;
                    }
                    if (showSettings) {
                        CurrentScreen = SETTING_SCREEN;
                        showSettings = !showSettings;
                    }
                }

            }
        });

        bird.soundTrack.playLoop();

        setLocationRelativeTo(null);
        BeginGame();

    }

    public void reStart() {
        bird.setPos(100, 250);
        bird.setVt(0);
        bird.setLive(true);
        pipeGroup.resetPipes();
        point = 0;

    }

    @Override
    public void GAME_UPDATE(long deltaTime) {

        if (CurrentScreen == BEGIN_SCREEN) {

            reStart();
        } else if (CurrentScreen == GAMEPLAY_SCREEN) {

            if (bird.isLive() && turnonMusic)
                bird_anim.Update_Me(deltaTime);
            bird.update(deltaTime);
            pipeGroup.Update();

            for (int i = 0; i < PipeGroup.size; i++) {
                if (bird.getRect().intersects(pipeGroup.getPipe(i).getRect())) {
                    if (!turnonMusic && bird.isLive())
                        bird.collideSound.play();
                    bird.setLive(false);
                    CurrentScreen = GAMEOVER_SCREEN;
                }
            }

            for (int i = 0; i < PipeGroup.size; i++) {
                if (bird.getPosX() > pipeGroup.getPipe(i).getPosX() && pipeGroup.getPipe(i).isPass() == false
                        && i % 2 == 0) {
                    point++;
                    pipeGroup.getPipe(i).setPass(true);
                    if (!turnonMusic)
                        bird.pointSound.play();

                }
            }

            if (bird.getPosY() + bird.getH() > 596) {
                CurrentScreen = GAMEOVER_SCREEN;
                if (!turnonMusic)
                    bird.collideSound.play();
            }

        }
        if (CurrentScreen == SETTING_SCREEN) {

        }

    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {
        g2.drawImage(bg, 0, 0, getWidth(), 600, null);

        pipeGroup.paint(g2);
        if (CurrentScreen != SETTING_SCREEN) {
            if (!bird.getIsFlying()) {
                bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);
            } else {
                bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
            }
        }

        if (CurrentScreen == BEGIN_SCREEN) {
            g2.drawImage(settingImg, 100, 50, null);
        }

        if (CurrentScreen == SETTING_SCREEN) {
            BufferedImage MusicTest = turnonMusic ? musicOff : musicOn;
            g2.drawImage(MusicTest, 370, 400, null);

            BufferedImage SoundTest = turnonSound ? soundOff : soundOn;
            g2.drawImage(SoundTest, 370, 320, null);

            g2.drawImage(returnImg, 600, 30, null);

        }

        if (CurrentScreen == GAMEOVER_SCREEN) {
            g2.drawImage(gameOverimg, 300, 240, 200, 120, null);
        }

        if (CurrentScreen == GAMEOVER_SCREEN || CurrentScreen == GAMEPLAY_SCREEN) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            g2.drawString("Point: " + point, 30, 40);
        }

    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            if (CurrentScreen == BEGIN_SCREEN) {
                CurrentScreen = GAMEPLAY_SCREEN;
            } else if (CurrentScreen == GAMEPLAY_SCREEN) {
                if (bird.isLive())
                    bird.fly();
            } else if (CurrentScreen == GAMEOVER_SCREEN) {
                CurrentScreen = BEGIN_SCREEN;
            }

        }
    }

}