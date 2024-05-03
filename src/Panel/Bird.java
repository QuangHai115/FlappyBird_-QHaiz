package Panel;

import Menu.Objects;
import Menu.SoundPlayer;

import java.awt.*;
import java.io.File;

public class Bird extends Objects {
    private float vt = 0;

    private boolean isFlying = false;

    private Rectangle rect;

    private boolean isLive = true;

    public SoundPlayer flap_sound, pong_sound, point_sound, game_sound;

    public Bird(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
        flap_sound = new SoundPlayer(new File("Stock/fap.wav"));
        pong_sound = new SoundPlayer(new File("Stock/fall.wav"));
        point_sound = new SoundPlayer(new File("Stock/getpoint.wav"));
    }

    public Bird() {
        game_sound = new SoundPlayer(new File("image/game0.wav"));
    }

    public void setVt(float vt) {
        this.vt = vt;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public float getVt() {
        return vt;
    }

    public boolean getIsFlying() {
        return isFlying;
    }

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public void update(long deltaTime) {
        vt += FlappyBird.g;
        this.setPosY(this.getPosY() + vt);
        if (this.getPosY() < 0) {
            this.setPosY(0);
            vt = 0;
        }

        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());
        if (vt < 0)
            isFlying = true;
        else
            isFlying = false;
    }

    public void fly() {
        vt = -3;

        flap_sound.play();

    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

}
