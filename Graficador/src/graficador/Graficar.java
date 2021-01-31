/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author camil
 */
public class Graficar {

    public static ArrayList<Equation> ParseEquation(String Equation) {
        ArrayList<Equation> result = new ArrayList<>();
        String v[] = Equation.split(" ");
        boolean negativo = false;

        for (int i = 0; i < v.length; i++) {
            Equation eq = new Equation();

            try {
                if ((v[i].contains("/")) && (!v[i].contains("x"))) {
                    float fraccion = fracciones(v[i]);
                     if (!negativo) {
                        eq.constant = fraccion;
                        eq.exp = 0;
                    } else {
                        eq.constant = -fraccion;
                        eq.exp = 0;
                    }
                } else {
                    float c = Integer.parseInt(v[i]);
                    if (!negativo) {
                        eq.constant = c;
                        eq.exp = 0;
                    } else {
                        eq.constant = -c;
                        eq.exp = 0;
                    }
                }

            } catch (NumberFormatException e) {

                //3*x**2 ese caso no existe
                
                if (v[i].equals("-")) {
                    negativo = true;
                }
                if ((!v[i].contains("**")) && (v[i].contains("*"))) {
                    String subV[] = v[i].split("\\*");
                    float number;
                    try{
                       number  = Integer.parseInt(subV[0]);
                    }catch(NumberFormatException es){
                        number = fracciones (subV[0]);
                    }
                    eq.constant = number;
                    eq.exp = 1;
                }
                if (v[i].contains("**")) {
                    String subV[] = v[i].split("\\*");
                    float number;
                    try{
                       number  = Integer.parseInt(subV[subV.length - 1].substring(0));
                    }catch(NumberFormatException es){
                        number = fracciones (subV[subV.length - 1].substring(0));
                    }
                    eq.exp = number;
                    eq.constant = 1;
                    //System.out.println("exp 0 es: "+eq.exp);
                }
                if (v[i].equals("x")) {
                    eq.constant = 1;
                    eq.exp = 1;
                }
            }
            result.add(eq);
        }

        return result;
    }

    public static float fracciones(String fraci) {
        String frac[] = fraci.split("/");
        String num = frac[0];
        String dem = frac[1];
        float result = Float.parseFloat(num) / Float.parseFloat(dem);

        return result;
    }

    public static ArrayList<Par> calc(ArrayList<Equation> eq) {
        ArrayList<Par> result = new ArrayList<>();
        double y;

        for (double j = -100; j < 100; j += 0.25) {
            y = 0;
            for (Equation e : eq) {
                System.out.println("exp es: " + e.exp);
                y = y + e.constant * Math.pow(j, e.exp);
            }

            Par p = new Par(j, y);
            System.out.println("par es: " + j + "," + y);
            result.add(p);
        }
        return result;
    }

    public static void Draw(ArrayList<Par> pares, JPanel panel) {
        Graphics g = panel.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Random r = new Random();
        int color = r.nextInt(5 - 0) + 0;
        switch (color) {
            case 0:
                g.setColor(Color.RED);
                break;

            case 1:
                g.setColor(Color.BLUE);
                break;

            case 2:
                g.setColor(Color.GRAY);
                break;

            case 3:
                g.setColor(Color.orange);
                break;

            case 4:
                g.setColor(Color.GREEN);
                break;

            case 5:
                g.setColor(Color.YELLOW);
                break;
        }

        for (Par p : pares) {
            g2d.draw(new Ellipse2D.Double(Cartesian.getx(p.x), Cartesian.gety(p.y), 2, 2));
            //g.fillOval( Cartesian.getx(p.x),  Cartesian.gety(p.y), 2, 2);
            //System.out.println("x es: "+(int)Cartesian.getx((int)p.x)+" y es: "+(int) Cartesian.gety((int)p.y));
        }
    }
}
