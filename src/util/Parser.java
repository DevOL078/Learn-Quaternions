package util;

import javafx.scene.shape.TriangleMesh;
import model.Point;

import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String file;

    public Parser(String file){
        this.file = file;
    }

    public TriangleMesh parseTriangleMesh() throws IOException {
        TriangleMesh mesh = new TriangleMesh();
        addPoints(mesh);
        mesh.getTexCoords().addAll(0,0);
        addFaces(mesh);
        return mesh;
    }

    private void addPoints(TriangleMesh mesh) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = "";
        while(!str.equals("# Node index, coordinates") && str != null){
            str = reader.readLine();
        }

        Vector<Point> vector = new Vector<>();
        Double minX = null;
        Double maxX = null;
        Double minY = null;
        Double maxY = null;
        Double minZ = null;
        Double maxZ = null;
        if(str != null){
            //Pattern pattern = Pattern.compile("\\d+ (-)?\\d+.\\d+ (-)?\\d+.\\d+ (-)?\\d+.\\d+");
            boolean exit = false;
            do{
                str = reader.readLine();
               // Matcher matcher = pattern.matcher(str);
                String[] numbers = str.split(" ");
                if(numbers.length == 4){          //matcher.matches()
                    numbers = str.split(" ");
                    double x = Double.parseDouble(numbers[1]);
                    double y = -Double.parseDouble(numbers[3]);
                    double z = Double.parseDouble(numbers[2]);
                    vector.add(new Point(x, y, z));

                    if(minX == null || x < minX){minX = x;}
                    if(maxX == null || x > maxX){maxX = x;}
                    if(minY == null || y < minY){minY = y;}
                    if(maxY == null || y > maxY){maxY = y;}
                    if(minZ == null || z < minZ){minZ = z;}
                    if(maxZ == null || z > maxZ){maxZ = z;}
                }
                else
                    exit = true;
            }while(!exit);

            double deltaX = (maxX + minX) / 2;
            double deltaY = (maxY + minY) / 2;
            double deltaZ = (maxZ + minZ) / 2;

            for (Point p:vector) {
                Point point = new Point(p.X() - deltaX, p.Y() - deltaY, p.Z() - deltaZ);
                mesh.getPoints().addAll((float)point.X(), (float)point.Y(), (float)point.Z());
            }
        }
    }

    private void addFaces(TriangleMesh mesh) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = "";
        while(!str.equals("# vertices count, indices") && str != null){
            str = reader.readLine();
        }

        if(str != null){
            Pattern pattern = Pattern.compile("\\d+ \\d+ \\d+ \\d+");
            boolean exit = false;
            do{
                str = reader.readLine();
                Matcher matcher = pattern.matcher(str);
                if(matcher.matches()){
                    String[] numbers = str.split(" ");
                    int a = Integer.parseInt(numbers[1]);
                    int b = Integer.parseInt(numbers[2]);
                    int c = Integer.parseInt(numbers[3]);
                    mesh.getFaces().addAll(a - 1, 0, b - 1, 0, c - 1, 0);
                }
                else
                    exit = true;
            }while(!exit);
        }
    }

}
