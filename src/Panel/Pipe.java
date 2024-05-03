package Panel;

import Menu.Objects;

import java.awt.*;

public class Pipe extends Objects {

    private Rectangle rect;

    private boolean Pass = false;

    public boolean isPass() {
        return Pass;
    }

    public void setPass(boolean pass) {
        Pass = pass;
    }

    public Pipe(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }

    public void Update() {
        setPosX(getPosX() - 2);
        rect.setLocation((int) this.getPosX(), (int) this.getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

}
