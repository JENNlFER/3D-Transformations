package jen;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import jen.graphics.Graphics2D;
import jen.graphics.Graphics3D;
import jen.graphics.line.Line;
import jen.graphics.line.Vertex2D;
import jen.graphics.line.Vertex3D;

import static jen.Renderer.Mode.BRESENHAM;

public class Renderer {

    private final Graphics2D data2D;
    private final Graphics3D data3D;
    private final GraphicsContext ctx;
    private final PixelWriter px;
    private final int width;
    private final int height;
    public Mode mode = BRESENHAM;

    public Renderer(Graphics2D data2D, Graphics3D data3D, Canvas canvas) {
        this.data2D = data2D;
        this.data3D = data3D;
        ctx = canvas.getGraphicsContext2D();
        px = ctx.getPixelWriter();
        width = (int) canvas.getWidth();
        height = (int) canvas.getHeight();
    }

    public void update() {
        clear();
        for (Line<Vertex2D> line : data2D.getRenderData()) {
            draw((int) line.a.x, (int) line.a.y, (int) line.b.x, (int) line.b.y, line.width, line.color);
        }
        for (Line<Vertex3D> line : data3D.getRenderData()) {
            if (line.a.z > 0 && line.b.z > 0)
            {
                draw((int) line.a.x, (int) line.a.y, (int) line.b.x, (int) line.b.y, line.width, line.color);
            }
        }
    }

    private void draw(int x0, int y0, int x1, int y1, int width, Color color) {
        switch (mode) {
            case BRESENHAM: bresenham(x0, y0, x1, y1, width, color);
                break;
            case SIMPLE: simple(x0, y0, x1, y1, width, color);
        }
    }

    public void clear() {
        ctx.clearRect(0, 0, width, height);
    }

    private void simple(double x0, double y0, double x1, double y1, int width, Color color) {
        Coordinates c = collapse((int) x0, (int) y0, (int) x1, (int) y1);
        x0 = c.x0; x1 = c.x1; y0 = c.y0; y1 = c.y1;

        double dx = x1 - x0;
        double m = (y1 - y0) / dx;

        while (x0 < x1) {
            plot((int) x0, (int) y0, width, color, c.flags);
            y0 += m;
            x0++;
        }
    }

    private void bresenham(int x0, int y0, int x1, int y1, int width, Color color) {
        Coordinates c = collapse(x0, y0, x1, y1);
        x0 = c.x0; x1 = c.x1; y0 = c.y0; y1 = c.y1;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int i = 2 * dy;
        int E = i - dx;
        int k = 2 * (dy - dx);

        while (x0 < x1) {
            plot(x0, y0, width, color, c.flags);
            if (E < 0) {
                E += i;
            } else {
                y0++;
                E += k;
            }
            x0++;
        }
    }

    private void plot(int x, int y, int width, Color color, boolean[] flags) {
        if (width <= 0) return;
        if (width == 1) {
            Vertex2D xy = expand(x, y, flags);
            if (xy.x < this.width && xy.y < this.height && xy.x >= 0 && xy.y >= 0) {
                px.setColor((int) xy.x, (int) xy.y, color);
            }
        } else {
            width = width / 2;
            for (int i = y - width; i <= y + width; ++i) {
                Vertex2D xy = expand(x, i, flags);
                if (xy.x < this.width && xy.y < this.height && xy.x >= 0 && xy.y >= 0) {
                    px.setColor((int) xy.x, (int) xy.y, color);
                }
            }
        }
    }

    private Coordinates collapse(int x0, int y0, int x1, int y1) {
        boolean sFlag = false;
        if (Math.abs(x1 - x0) < Math.abs(y1 - y0)) {
            sFlag = true;
            int tmp;
            tmp = x0;
            x0 = y0;
            y0 = tmp;
            tmp = x1;
            x1 = y1;
            y1 = tmp;
        }
        boolean xFlag = false;
        if (x1 < x0) {
            xFlag = true;
            x0 = width - x0;
            x1 = width - x1;
        }
        boolean yFlag = false;
        if (y1 < y0) {
            yFlag = true;
            y0 = height - y0;
            y1 = height - y1;
        }

        return new Coordinates(x0, y0, x1, y1, xFlag, yFlag, sFlag);
    }

    private Vertex2D expand(int x, int y, boolean... flags) {
        if (flags[0]) x = width - x;
        if (flags[1]) y = height - y;
        if (flags[2]) return new Vertex2D(y, x);
        return new Vertex2D(x, y);
    }

    private static class Coordinates {
        private final int x0;
        private final int y0;
        private final int x1;
        private final int y1;
        private final boolean[] flags = new boolean[3];
        private Coordinates(int x0, int y0, int x1, int y1, boolean xFlag, boolean yFlag, boolean sFlag) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            flags[0] = xFlag;
            flags[1] = yFlag;
            flags[2] = sFlag;
        }
    }

    public enum Mode {
        BRESENHAM, SIMPLE
    }
}
