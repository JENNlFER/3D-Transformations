package jen.util;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import jen.graphics.Graphics2D;
import jen.graphics.Graphics3D;
import jen.graphics.line.Line;
import jen.graphics.line.Vertex3D;

import java.util.concurrent.ThreadLocalRandom;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.INDIGO;
import static javafx.scene.paint.Color.ORANGE;
import static javafx.scene.paint.Color.PURPLE;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.VIOLET;
import static javafx.scene.paint.Color.YELLOW;

public class GraphicsPresets {

    public static void spectrum2D(Graphics2D graphics) {
        Rainbow color = new Rainbow(5);
        for (double theta = 0; theta < Math.PI * 2; theta += 0.0208) {
            int x = (int) (300 * Math.cos(theta) + 450);
            int y = (int) (300 * Math.sin(theta) + 350);
            graphics.addLine(450, 350, x, y, 8, color.next());
        }
        graphics.update();
    }

    public static void cone3D(Graphics3D graphics) {
        Rainbow color = new Rainbow(22);
        for (double theta = 0; theta < Math.PI * 2; theta += 0.1) {
            int x = (int) (1000 * Math.cos(theta) + 450);
            int y = (int) (1000 * Math.sin(theta) + 350);
            int z = (int) (1000 * Math.sin(theta) + 5000);
            graphics.addLine(450, 350, 1000, x, y, z, 8, color.next());
        }
        graphics.update();
    }

    public static void HouseClose3D(Graphics3D graphics) {
        Vertex3D[] vertices = {
                new Vertex3D(100, -200, 45),  // A 0
                new Vertex3D(50, 0, 10),      // B 1
                new Vertex3D(150, 0, 10),     // C 2
                new Vertex3D(150, 0, 110),    // D 3
                new Vertex3D(50, 0, 110),     // E 4
                new Vertex3D(150, -150, 110), // G 5
                new Vertex3D(150, -150, 10),  // H 6
                new Vertex3D(50, -150, 10),   // I 7
                new Vertex3D(50, -150, 110)   // J 8
        };
        generateHouse(graphics, vertices);
    }

    public static void HouseFar3D(Graphics3D graphics) {
        Vertex3D[] vertices = {
                new Vertex3D(100, -200, 2045),  // A 0
                new Vertex3D(50, 0, 2010),      // B 1
                new Vertex3D(150, 0, 2010),     // C 2
                new Vertex3D(150, 0, 2110),     // D 3
                new Vertex3D(50, 0, 2110),      // E 4
                new Vertex3D(150, -150, 2110),  // G 5
                new Vertex3D(150, -150, 2010),  // H 6
                new Vertex3D(50, -150, 2010),   // I 7
                new Vertex3D(50, -150, 2110)    // J 8
        };
        generateHouse(graphics, vertices);
    }

    private static void generateHouse(Graphics3D graphics, Vertex3D[] vertices) {
        Line<Vertex3D>[] lines = new Line[] {
                new Line<>(vertices[1], vertices[2], 5, RED),  // BC
                new Line(vertices[2], vertices[3], 5, RED),    // CD
                new Line(vertices[3], vertices[4], 5, RED),    // DE
                new Line(vertices[4], vertices[1], 5, RED),    // EB
                new Line(vertices[1], vertices[7], 5, ORANGE), // BI
                new Line(vertices[2], vertices[6], 5, YELLOW), // CH
                new Line(vertices[3], vertices[5], 5, GREEN),  // DG
                new Line(vertices[4], vertices[8], 5, BLUE),   // EJ
                new Line(vertices[7], vertices[6], 5, PURPLE), // IH
                new Line(vertices[6], vertices[5], 5, PURPLE), // HG
                new Line(vertices[5], vertices[8], 5, PURPLE), // GJ
                new Line(vertices[8], vertices[7], 5, PURPLE), // JI
                new Line(vertices[7], vertices[0], 5, INDIGO), // IA
                new Line(vertices[6], vertices[0], 5, VIOLET), // HA
                new Line(vertices[5], vertices[0], 5, INDIGO), // GA
                new Line(vertices[8], vertices[0], 5, VIOLET), // JA
        };

        for (Line line : lines) {
            graphics.addLine(line);
        }
        graphics.update();
    }

    public static void random3D(Graphics3D graphics) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int spread = 5000;
        for (int i = 0; i < 100; ++i) {
            graphics.addLine(r.nextInt(-spread, spread), r.nextInt(-spread, spread),
                    r.nextInt(-spread, spread), r.nextInt(-spread, spread),
                    r.nextInt(0, spread*2), r.nextInt(0, spread*2), r.nextInt(10),
                    Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        }
        graphics.update();
    }

    public static void random2D(Graphics2D graphics) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 100; ++i) {
            graphics.addLine(r.nextInt(900), r.nextInt(700),
                    r.nextInt(900), r.nextInt(700), r.nextInt(10),
                    Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        }
        graphics.update();
    }
}
