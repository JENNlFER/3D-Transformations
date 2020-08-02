package jen.util;

import javafx.scene.paint.Color;
import jen.graphics.line.Line;
import jen.graphics.line.Vertex2D;
import jen.matrix.trans.d2.Rotate2D;
import jen.matrix.trans.d2.Scale2D;
import jen.matrix.trans.d2.Translate2D;
import jen.matrix.trans.Transformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static boolean write(File file, boolean background, List<Line<Vertex2D>> lines, List<Transformation> transformations) {
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            return false;
        }

        try {
            FileWriter out = new FileWriter(file);
            out.write("BGCL " + (background ? "BLACK" : "WHITE") + "\n");

            for (Line<Vertex2D> line : lines) {
                out.write("LINE " + Format.out(line.a.x) + " " + Format.out(line.a.y) + " " +
                        Format.out(line.b.x) + " " + Format.out(line.b.y) + " " + Format.out(line.width) +
                        " " + Format.color(line.color.getRed()) +
                        " " + Format.color(line.color.getGreen()) +
                        " " + Format.color(line.color.getBlue()) + "\n");
            }

            for (Transformation trans : transformations) {
                if (trans instanceof Translate2D) {
                    Translate2D t = (Translate2D) trans;
                    out.write("TRAN " + Format.out(t.Tx()) + " " + Format.out(t.Ty()));
                } else if (trans instanceof Rotate2D) {
                    Rotate2D r = (Rotate2D) trans;
                    out.write("ROTN " + Format.out(r.degrees()));
                    if (!r.isSimple()) {
                        out.write(" " + Format.out(r.Cx()) + " " + Format.out(r.Cy()));
                    } else {
                        out.write(" " + Format.out(0) + " " + Format.out(0));
                    }
                } else if (trans instanceof Scale2D) {
                    Scale2D s = (Scale2D) trans;
                    out.write("SCAL " + Format.out(s.Sx()) + " " + Format.out(s.Sy()));
                    if (!s.isSimple()) {
                        out.write(" " + Format.out(s.Cx()) + " " + Format.out(s.Cy()));
                    } else {
                        out.write(" " + Format.out(0) + " " + Format.out(0));
                    }
                }
                out.write("\n");
            }
            out.close();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public static File2D read(File file) {
        int badFormat = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            boolean black = true;
            List<Line<Vertex2D>> lineList = new ArrayList<>();
            List<Transformation> transList = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                String[] params = line.split(" ");
                if (params.length < 2) {
                    badFormat++;
                    continue;
                }
                switch (params[0]) {
                    case "BGCL":
                        switch (params[1]) {
                            case "WHITE":
                                black = false;
                                break;
                            case "BLACK":
                            default:
                                black = true;
                        }
                        break;
                    case "LINE":
                        int x0;
                        int y0;
                        int x1;
                        int y1;
                        int width = 1;
                        int r = 0;
                        int g = 0;
                        int b = 0;
                        try {
                            if (params.length >= 5) {
                                x0 = (int) Format.strToNum(params[1]);
                                y0 = (int) Format.strToNum(params[2]);
                                x1 = (int) Format.strToNum(params[3]);
                                y1 = (int) Format.strToNum(params[4]);
                            }
                            else {
                                badFormat++;
                                continue;
                            }
                            if (params.length >= 6) {
                                width = (int) Format.strToNum(params[5]);
                            }
                            if (params.length == 9) {
                                r = Math.min(255, Math.max(0, (int) Format.strToNum(params[6])));
                                g = Math.min(255, Math.max(0, (int) Format.strToNum(params[7])));
                                b = Math.min(255, Math.max(0, (int) Format.strToNum(params[8])));
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            badFormat++;
                            continue;
                        }
                        lineList.add(new Line<>(new Vertex2D(x0, y0), new Vertex2D(x1, y1), width, Color.rgb(r, g, b)));
                        break;
                    case "TRAN":
                        double x = 0;
                        double y = 0;
                        try {
                            if (params.length == 3) {
                                x = Format.strToNum(params[1]);
                                y = Format.strToNum(params[2]);
                            } else {
                                badFormat++;
                                continue;
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            badFormat++;
                            continue;
                        }
                        transList.add(new Translate2D(x, y));
                        break;
                    case "ROTN":
                        double rad = 0;
                        double Cx = 0;
                        double Cy = 0;
                        try {
                            if (params.length == 4) {
                                rad = Math.toRadians(Format.strToNum(params[1]));
                                Cx = Format.strToNum(params[2]);
                                Cy = Format.strToNum(params[3]);
                            } else {
                                badFormat++;
                                continue;
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            badFormat++;
                            continue;
                        }
                        transList.add(new Rotate2D(rad, Cx, Cy));
                        break;
                    case "SCAL":
                        x = 1;
                        y = 1;
                        Cx = 0;
                        Cy = 0;
                        try {
                            if (params.length == 5) {
                                x = Format.strToNum(params[1]);
                                y = Format.strToNum(params[2]);
                                Cx = Format.strToNum(params[3]);
                                Cy = Format.strToNum(params[4]);
                            } else {
                                badFormat++;
                                continue;
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            badFormat++;
                            continue;
                        }
                        transList.add(new Scale2D(x, y, Cx, Cy));
                        break;
                    default:
                        badFormat++;
                }
            }
            return new File2D(black, lineList, transList, badFormat);
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        return null;
    }

    public static class File2D {
        public final boolean black;
        public final List<Line<Vertex2D>> lines;
        public final List<Transformation> transformations;
        public final int errors;

        File2D(boolean black, List<Line<Vertex2D>> lines, List<Transformation> transformations, int errors) {
            this.black = black;
            this.lines = lines;
            this.transformations = transformations;
            this.errors = errors;
        }
    }
}
