package Panel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;

import Menu.QueueList;

public class PipeGroup {
    private QueueList<Pipe> pipeList;
    private BufferedImage bottom_pipe, top_pipe;

    public static int size = 6;
    private int top = -300;
    private int bottom = 300;

    public Pipe getPipe(int i) {
        return pipeList.get(i);
    }

    public PipeGroup() {
        try {
            bottom_pipe = ImageIO.read(new File("Stock/bottompipe.png"));
            top_pipe = ImageIO.read(new File("Stock/toppipe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pipeList = new QueueList<Pipe>();
        Pipe p;

        for (int i = 0; i < size / 2; i++) {
            int randomY = getRandom();
            p = new Pipe(700 + i * 300, bottom + randomY, 75, 400);
            pipeList.push(p);
            p = new Pipe(700 + i * 300, top + randomY, 75, 400);
            pipeList.push(p);
        }
    }

    public int getRandom() {
        Random random = new Random();
        int a = random.nextInt(30);
        return a * 10;
    }

    public void resetPipes() {
        pipeList = new QueueList<Pipe>();
        Pipe p;
        for (int i = 0; i < size / 2; i++) {
            int randomY = getRandom();
            p = new Pipe(1000 + i * 300, bottom + randomY, 75, 400);
            pipeList.push(p);
            p = new Pipe(1000 + i * 300, top + randomY, 75, 400);
            pipeList.push(p);
        }
    }

    public void Update() {
        for (int i = 0; i < size; i++) {
            pipeList.get(i).Update();
        }

        if (pipeList.get(0).getPosX() < -70) {
            int randomY = getRandom();
            Pipe pipe;

            pipe = pipeList.pop();
            pipe.setPosX(pipeList.get(4).getPosX() + 300);
            pipe.setPosY(bottom + randomY);
            pipe.setPass(false);
            pipeList.push(pipe);

            pipe = pipeList.pop();
            pipe.setPosX(pipeList.get(4).getPosX());
            pipe.setPosY(-bottom + randomY);
            pipe.setPass(false);
            pipeList.push(pipe);
        }

    }

    public void paint(Graphics2D g2) {
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0)
                g2.drawImage(bottom_pipe, (int) pipeList.get(i).getPosX(), (int) pipeList.get(i).getPosY(), null);
            else
                g2.drawImage(top_pipe, (int) pipeList.get(i).getPosX(), (int) pipeList.get(i).getPosY(), null);
        }
    }
}
