/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import formas.Forma;
import Auxiliares.Archivo;
import Auxiliares.DescribePath;
import formas.Figura;
import Auxiliares.GuardarFigura;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jirok
 */
class MiPanel extends JPanel {

    Archivo archivo = new Archivo();
    Point p1;
    Point p2;
    Shape figura;
    Random R = new Random();
    public Color coloractual = Color.black, rellenoactual = Color.black;
    //public GradientPaint rellenoactual=new GradientPaint(0,0,Color.BLACK,0,0,Color.black);
    public BufferedImage myImage;
    Graphics2D g2D;
    int fig=-1;
    ArrayList<Figura> formas;
    ArrayList<Figura> formaselect;
    Figura forma;
    boolean relleno = false;
    boolean seleccion = false;
    boolean creafigura = false;
    boolean outline = false;
    boolean mulsel = false;
    boolean abrir = false;
    float sizestroke = 1;
    DescribePath desp = new DescribePath();
    BasicStroke strokeactual = new BasicStroke(sizestroke);
    String figguar = null;
    int tipolinea = 0;
    float alfa = 1.0f;
    static final int NONE = 0;
    static final int TRANSLATION = 1;
    static final int ROTATION = 2;
    static final int SCALING = 3;
    static final int SHEARING = 4;
    static final int REFLECTION = 5;
    int transformType = NONE;
    boolean transform = false;
    int x0,y0;
Shape tempShape=null;
    public MiPanel() {
        super();
        OyenteDeRaton miOyente = new OyenteDeRaton();
        OyenteDeMovimiento miOyente2 = new OyenteDeMovimiento();
        addMouseListener(miOyente);
        addMouseMotionListener(miOyente2);
        this.setBackground(Color.WHITE);
        formas = new <Figura>ArrayList();
        formaselect = new <Figura>ArrayList();
        x0=0;
        y0=0;
    }
     public Graphics2D crearGraphics2D() {
        Graphics2D g2 = null;
        if (myImage == null || myImage.getWidth() != getSize().width
                || myImage.getHeight() != getSize().height) {
            myImage = (BufferedImage) createImage(getSize().width, getSize().height);
        }
        if (myImage != null) {
            g2 = myImage.createGraphics();
            g2.setColor(coloractual);
            g2.setBackground(getBackground());
        }

        g2.clearRect(0, 0, getSize().width, getSize().height);
        return g2;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (myImage == null) {
            g2D = crearGraphics2D();
        }
        if (seleccion || mulsel||transform) {
            g2D = crearGraphics2D();
            for (int i = 0; i < formas.size(); i++) {
                forma = formas.get(i);
                if (forma.getStroke() != null) {
                    g2D.setStroke(forma.getStroke());
                }
                Shape s;
                s = forma.getShape();

                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) forma.getAlpha()));
                if (forma.getRelleno() != null) {
                    g2D.setColor(forma.getRelleno());
                    g2D.fill(s);
                }
                g2D.setColor(forma.getContorno());
                g2D.draw(s);
            }
            if (!formaselect.isEmpty()) {
                float dash1[] = {10.0f};
                for (int i = 0; i < formaselect.size(); i++) {
                    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2D.setColor(Color.blue);
                    g2D.setStroke(new BasicStroke(1.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f, dash1, 0.0f));
                    g2D.draw(new Rectangle2D.Double(formaselect.get(i).getShape().getBounds().x - 5, formaselect.get(i).getShape().getBounds().y - 5, formaselect.get(i).getShape().getBounds().width + 10, formaselect.get(i).getShape().getBounds().height + 10));
                    g2D.setStroke(strokeactual);
                    g2D.setColor(coloractual);
                }
            }
        } else {
            g2D = crearGraphics2D();
            for (int i = 0; i < formas.size(); i++) {
                forma = formas.get(i);
                if (forma.getStroke() != null) {
                    g2D.setStroke(forma.getStroke());
                }
                Shape s;
                s = forma.getShape();
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) forma.getAlpha()));
                if (forma.getRelleno() != null) {
                    g2D.setColor(forma.getRelleno());
                    g2D.fill(s);
                }
                g2D.setColor(forma.getContorno());
                g2D.draw(s);
            }
        }
        if (abrir) {
            System.out.println("abrir");
            abrir = false;
        }

        if (myImage != null && isShowing()) {
            g.drawImage(myImage, 0, 0, this);
        }

        figura = null;

    }
public void limpiatransform(){
Principal.move.setBorderPainted(false);
Principal.shearing.setBorderPainted(false);
Principal.escala.setBorderPainted(false);
Principal.rota.setBorderPainted(false);
//Principal.reflect.setBorderPainted(false);
this.transform=false;
this.transformType = this.NONE;
}
public void limpiatransform(int n){
    switch (n) {
        case 1:
            Principal.move.setBorderPainted(true);
Principal.shearing.setBorderPainted(false);
Principal.escala.setBorderPainted(false);
Principal.rota.setBorderPainted(false);
            break;
        case 2:
            Principal.move.setBorderPainted(false);
Principal.shearing.setBorderPainted(true);
Principal.escala.setBorderPainted(false);
Principal.rota.setBorderPainted(false);
            break;
        case 3:
            Principal.move.setBorderPainted(false);
Principal.shearing.setBorderPainted(false);
Principal.escala.setBorderPainted(true);
Principal.rota.setBorderPainted(false);
            break;
        case 4:
            Principal.move.setBorderPainted(false);
Principal.shearing.setBorderPainted(false);
Principal.escala.setBorderPainted(false);
Principal.rota.setBorderPainted(true);
            break;

    }

}
    public void mover() {
        if(transform){limpiatransform(1);}
        if (this.transformType == this.TRANSLATION){this.transform = !transform;}else{this.transform = true;}
        
        if (this.transform) {
            this.transformType = this.TRANSLATION;
            System.out.println("TRANSLATION");
            Principal.move.setBorderPainted(true);
        } else {
            this.transformType = this.NONE;
            System.out.println("quitar TRANSLATION");
            Principal.move.setBorderPainted(false);
        }
    }
    public void shearing() {
        if(transform){limpiatransform(2);}
        if (this.transformType == this.SHEARING){this.transform = !transform;}else{this.transform = true;}
        if (this.transform) {
            this.transformType = this.SHEARING;
            System.out.println("SHEARING");
            Principal.shearing.setBorderPainted(true);
        } else {
            this.transformType = this.NONE;
            System.out.println("quitar SHEARING");
            Principal.shearing.setBorderPainted(false);
        }
    }
        public void escala() {
        if(transform){limpiatransform(3);}
        if (this.transformType == this.SCALING){this.transform = !transform;}else{this.transform = true;}
        if (this.transform) {
            this.transformType = this.SCALING;
            System.out.println("SCALING");
            Principal.escala.setBorderPainted(true);
        } else {
            this.transformType = this.NONE;
            System.out.println("quitar SCALING");
            Principal.escala.setBorderPainted(false);
        }
    }
    public void rota() {
        if(transform){limpiatransform(4);}
        if (this.transformType == this.ROTATION){this.transform = !transform;}else{this.transform = true;}
        if (this.transform) {
            this.transformType = this.ROTATION;
            System.out.println("ROTATION");
            Principal.rota.setBorderPainted(true);
        } else {
            this.transformType = this.NONE;
            System.out.println("quitar ROTATION");
            Principal.rota.setBorderPainted(false);
        }
    }

    public void reflect() {
        if(transform){limpiatransform(5);}
        if (this.transformType == this.REFLECTION){this.transform = !transform;}else{this.transform = true;}
        if (this.transform) {
            this.transformType = this.REFLECTION;
            System.out.println("REFLECTION");
            //Principal.reflect.setBorderPainted(true);

        } else {
            this.transformType = this.NONE;
            System.out.println("quitar REFLECTION");
            //Principal.reflect.setBorderPainted(false);
        }
    }


    public Shape crearFigura(Point p1, Point p2, int f, String n) {
        Forma form = new Forma(p1, p2, f, n);
        return form.getShape();
    }

    public void resetAll() {
        myImage = null;
        formas = new <Figura>ArrayList();
        formaselect = new <Figura>ArrayList();
        seleccion = false;
        mulsel=false;
        transform=false;
        figura = null;
        //relleno = false;

        repaint();
    }

    public void select(int n) {
        if (n == 2) {
            this.mulsel = !mulsel;
            seleccion = false;
            if (!mulsel) {
                System.out.println("quita seleccion multiple");
                transform = false;
                limpiatransform();
            } else if (!seleccion) {
                System.out.println("quita seleccion simple");
            }
        } else if (n == 1) {
            this.seleccion = !seleccion;
            this.mulsel = false;
            formaselect.clear();
            limpiatransform();
            if (!seleccion) {
                System.out.println("quita seleccion simple");
            } else if (!mulsel) {
                System.out.println("quita seleccion multiple");
            }
            //System.out.println(formaselect.size());
            repaint();
        }

        if (seleccion == false && mulsel == false) {
            formaselect.clear();
            Principal.select.setBorderPainted(false);
            Principal.selectmult.setBorderPainted(false);
            limpiatransform();
            repaint();
        }

    }
    public void limpiaselect() {
        formaselect.clear();
        if (seleccion) {
            seleccion = false;
            Principal.select.setBorderPainted(false);
        }
        if (mulsel) {
            mulsel = false;
            Principal.selectmult.setBorderPainted(false);
        }
        if(transform){
            limpiatransform();
        }
        System.out.print("limpiaselect ");
        repaint();
    }
    private void seleccionfiguras(Point p) {
        boolean existe = false;
        /*if (!mulsel) {
            formaselect.clear();
        }//*/
        for (int i = formas.size() - 1; i >= 0; i--) {
            if (formas.get(i).getShape().contains(p)) {
                //formaselect.add(formas.get(i));
                existe = false;
                System.out.println("se encontro el punto en la figura");
                for (int j = 0; j < formaselect.size(); j++) {
                    System.out.println("buscando figura en seleccion");
                    if (formaselect.get(j).getShape().contains(p)) {
                        existe = true;
                        System.out.println("la forma ya esta seleccionada");
                    }
                }
                if (!existe) {
                    if (!mulsel) {
                        formaselect.clear();
                    }
                    formaselect.add(formas.get(i));
                    System.out.println("la forma se agrego");
                } else {
                    //quitarFiguras(formas.get(i));
                    formaselect.remove(formas.get(i));
                    System.out.println("se removio la forma");
                    existe = false;
                }
            }
        }
        System.out.println("numero de figuras seleccionadas " + formaselect.size());
        repaint();
    }

    class OyenteDeRaton extends MouseAdapter {

        public void mousePressed(MouseEvent evento) {
            System.out.println("s "+seleccion+" ms "+mulsel+" t "+transform);
            if (creafigura) {
                formaselect.clear();
                if (seleccion) {
                    seleccion = false;
                }
                if (mulsel) {
                    mulsel = false;
                }
                MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                
            }
            p1 = evento.getPoint();
        }

        public void mouseReleased(MouseEvent evento) {
            if (creafigura) {
                MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                p2 = evento.getPoint();
                if (fig == 0) {
                    figura = crearFigura(p1, p2, fig, figguar);
                } else {
                    figura = crearFigura(p1, p2, fig, "n");
                }
                Figura forma = new Figura(figura, strokeactual, desp.obtenerPath(figura), coloractual, 1.0);//(Shape shape, BasicStroke stroke, GeneralPath path, Color contorno, Color relleno, double alpha)
                if (relleno) {
                    forma.setRelleno(rellenoactual);
                }
                forma.setAlpha(alfa);
                formas.add(forma);

                repaint();

                creafigura = false;
                fig=-1;
                Principal.linea.setBorderPainted(false);
                Principal.rectangulo.setBorderPainted(false);
                Principal.elipse.setBorderPainted(false);
                Principal.triangulo.setBorderPainted(false);
                Principal.pentagono.setBorderPainted(false);
                Principal.hexagono.setBorderPainted(false);
                
            }
            if ((seleccion || mulsel)&&!transform) {
                p1 = evento.getPoint();
                seleccionfiguras(p1);
            }
            if (transform&&!formaselect.isEmpty()) {
                //int x0=formaselect.get(0).getShape().getBounds().x;
                //int y0=formaselect.get(0).getShape().getBounds().y;
                Graphics g = getGraphics();
                p2 = evento.getPoint();
                AffineTransform tr = new AffineTransform();
                switch (transformType) {
                    case TRANSLATION:
                        //tr.setToTranslation(p2.x - p1.x, p2.y - p1.y);
                        tr=AffineTransform.getTranslateInstance(p2.x - p1.x, p2.y - p1.y);
                        break;
                    case ROTATION:
                        double a = Math.atan2(p2.y - y0, p2.x - x0) - Math.atan2(p1.y - y0, p1.x - x0);
                        
                        tr.setToRotation(a);
                        //tr.setToRotation(x0, y0);

                        break;
                    case SCALING:
                        double sx = Math.abs((double) (p2.x - x0) / (p1.x - x0));
                        double sy = Math.abs((double) (p2.y - y0) / (p1.y - y0));
                        tr.setToScale(sx, sy);
                        break;
                    case SHEARING:
                        double shx = ((double) (p2.x - x0) / (p1.x - x0)) - 1;
                        double shy = ((double) (p2.y - y0) / (p1.y - y0)) - 1;
                        tr.setToShear(shx, shy);
                        break;
                    case REFLECTION:
                        tr.setTransform(-1, 0, 0, 1, 0, 0);
                        //tr.setTransform(-p2.x, 0, 0, p2.y, 0, 0);
                        break;
                }
                Shape nueva;
                int indice;
                
                //System.out.println(p2.x+" "+p2.y);
                for(int j=0;j<formaselect.size();j++){
                    indice=formas.indexOf(formaselect.get(j));
                    nueva=tr.createTransformedShape(formas.get(indice).getShape());
                    formas.get(indice).setShape(nueva);
                    //System.out.println("indice de formas "+formas.indexOf(formaselect.get(j)));
                }
                repaint();

            }
        }
    }

    class OyenteDeMovimiento extends MouseMotionAdapter {
public void mouseMoved(MouseEvent ev) {//Paint.coordenadas.setText("x: " + (ev.getX()-x0) + "   y: " + ( (ev.getY()-y0)));
        Principal.x.setText(ev.getX()+"");
        Principal.y.setText(ev.getY()+"");
        if (!formaselect.isEmpty()){
            Principal.elimina.setEnabled(true);
        }
    }
        public void mouseDragged(MouseEvent evento) {
            if (creafigura) {
                Graphics2D g2D;
                if (figura != null) {

                    g2D = (Graphics2D) MiPanel.this.getGraphics();
                    g2D.setXORMode(MiPanel.this.getBackground());
                    //g2D.setColor(coloractual);
                    g2D.setColor(Color.black);
                    g2D.draw(figura);
                    g2D.setColor(coloractual);
                }
                p2 = evento.getPoint();
                if (fig == 0) {
                    figura = crearFigura(p1, p2, fig, figguar);
                } else {
                    figura = crearFigura(p1, p2, fig, "n");
                }

                g2D = (Graphics2D) MiPanel.this.getGraphics();
                g2D.setXORMode(MiPanel.this.getBackground());
                g2D.setColor(Color.black);
                g2D.draw(figura);
                g2D.setColor(coloractual);
            }
            if (transform&&!formaselect.isEmpty()) {
                //ArrayList<Figura> formas;
                //ArrayList<Figura> formaselect;
                //int x0=formaselect.get(0).getShape().getBounds().x;
                //int y0=formaselect.get(0).getShape().getBounds().y;
                //System.out.println("coordenadas "+x0+" "+y0);
                p2 = evento.getPoint();
                AffineTransform tr = new AffineTransform();
                switch (transformType) {
                    case TRANSLATION:
                        tr.setToTranslation(p2.x - p1.x, p2.y - p1.y);
                        break;
                    case ROTATION:
                        double a = Math.atan2(p2.y - y0, p2.x - x0) - Math.atan2(p1.y - y0, p1.x - x0);
                        //System.out.println(a);
                        tr.setToRotation(a);
                        //tr.setToRotation(x0, y0);
                        break;
                    case SCALING:
                        double sx = Math.abs((double) (p2.x - x0) / (p1.x - x0));
                        double sy = Math.abs((double) (p2.y - y0) / (p1.y - y0));
                        tr.setToScale(sx, sy);
                        break;
                    case SHEARING:
                        double shx = ((double) (p2.x - x0) / (p1.x - x0)) - 1;
                        double shy = ((double) (p2.y - y0) / (p1.y - y0)) - 1;
                        tr.setToShear(shx, shy);
                        break;
                    case REFLECTION:
                        tr.setTransform(-1, 0, 0, 1, 0, 0);
                        break;
                }
                Graphics2D g = (Graphics2D) getGraphics();
                g.setXORMode(Color.white);
                //System.out.println("coordenadas originales "+x0+" "+y0);
                //System.out.println("coordenadas nuevas "+(p2.x - p1.x)+" "+(p2.y - p1.y));
                g.translate(x0, y0);
                for(int j=0;j<formaselect.size();j++){
                if (tempShape != null) {
                    //g.translate(tempShape.getBounds2D().getX(), tempShape.getBounds2D().getY());
                    g.draw(tempShape);
                }
                tempShape = tr.createTransformedShape(formaselect.get(j).getShape());
                
                g.draw(tempShape);
                
                }
                

            }//*/
        }
    }


    public void quitarFiguras(Figura f) {
        if (formas.contains(f)) {
            formas.remove(f);
        }
    }
    public void quitarFiguras(ArrayList<Figura> f){
        for(int i=0;i<f.size();i++){
            if (formas.contains(f.get(i))) {
            formas.remove(f.get(i));
            }
        }
        formaselect.clear();
        repaint();
    }

    public String[] getFormats() {
        String[] formats = javax.imageio.ImageIO.getWriterFormatNames();
        java.util.TreeSet<String> formatSet = new java.util.TreeSet<String>();
        for (String s : formats) {
            formatSet.add(s.toLowerCase());
        }
        return formatSet.toArray(new String[0]);
    }
    class MyFileFilter extends javax.swing.filechooser.FileFilter {

        private String extension;
        private String description;

        public MyFileFilter(String extension, String description) {
            this.extension = extension;
            this.description = description;
        }

        @Override
        public boolean accept(File f) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return f.getName().toLowerCase().endsWith("." + extension) || f.isDirectory();
        }

        @Override
        public String getDescription() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return description;
        }

        public String getExtension() {
            return extension;
        }

    }
    public void setalfa(float alfa) {
        this.alfa = alfa;
    }
    public void setStrokeactual(float strokeactual) {
        float dash1[] = {10.0f};
        this.sizestroke = strokeactual;
        //this.strokeactual = new BasicStroke(sizestroke);
        if (tipolinea == 1) {
            this.strokeactual = new BasicStroke(sizestroke,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_ROUND,
                    10.0f, dash1, 0.0f);
        } else {
            this.strokeactual = new BasicStroke(sizestroke,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_ROUND, 10.0f);
        }
    }
    public BasicStroke getStrokeactual(){
        return strokeactual;
    }
    public void tipolinea(int n) {
        this.tipolinea = n;
        if (tipolinea == 1) {
            float dash1[] = {10.0f};
            strokeactual = new BasicStroke(sizestroke,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);
        } else {
            strokeactual = new BasicStroke(sizestroke,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f);
        }

    }
    public Color getColorActual() {
        return coloractual;
    }
    public void setColorActual(Color color) {
        coloractual = color;
    }
    public void setRellenoActual(Color color) {
        rellenoactual = color;
        relleno = true;
        outline = true;
    }
    public void abrir() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".spd", "spd");
        fc.setFileFilter(filtro);
        int seleccion = fc.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File arch = fc.getSelectedFile();
            try {
                FileInputStream arch2 = new FileInputStream(arch);
                ObjectInputStream os = new ObjectInputStream(arch2);
                ArrayList<GuardarFigura> figs = (ArrayList<GuardarFigura>) os.readObject();
                resetAll();
                //panel.formas.clear();
                this.formas.clear();
                this.formaselect.clear();
                //panel.selectFormas.clear();
                Figura nueva;
                Shape s;
                BasicStroke b;
                //Form ff;
                AffineTransform tr;
                for (GuardarFigura f : figs) {
                    b = new BasicStroke(f.grosor, f.cap, f.join, f.qtr, f.fl, f.last);
                    tr = new AffineTransform();
                    s = tr.createTransformedShape(f.getPath());
                    nueva = new Figura(s, b, f.getPath(), f.getContorno(), f.getRelleno(), f.getAlpha());
                    //panel.formas.add(nueva);
                    formas.add(nueva);
                    s = null;
                    tr = null;
                    nueva = null;
                }
                //panel.estado = PaintDemo.NONE;
                abrir = true;
                repaint();
                //panel.StrokeGlobal = PaintDemo.BS;
                os.close();
                arch2.close();
            } catch (IOException el) {
                el.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MiPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void guardar() {
        if (formas.size() != 0) {
            ArrayList<GuardarFigura> v = new ArrayList<GuardarFigura>();
            float grosor;
            int cap;
            int join;
            float qtr;
            float[] fl;
            float last;
            Shape s;
            DescribePath d = new DescribePath();
            int i = 0;
            //Figura ff;
            for (Figura f : formas) {
                //ff=new Figura(f.getShape(),f.getStroke(),d.obtenerPath(f.getShape()),f.getColor(),f.getFilltexture(),f.getAlphacom().getAlpha());//(Shape shape, BasicStroke stroke, GeneralPath path, Color contorno, Color relleno, double alpha) 
                AffineTransform at = new AffineTransform();
                grosor = f.getStroke().getLineWidth();
                cap = f.getStroke().getEndCap();
                join = f.getStroke().getLineJoin();
                qtr = f.getStroke().getMiterLimit();
                fl = f.getStroke().getDashArray();
                last = f.getStroke().getDashPhase();
                v.add(new GuardarFigura(grosor, cap, join, qtr, fl, last, d.obtenerPath(f.getShape()), f.getContorno(), f.getRelleno(), f.getAlpha()));
            }
            archivo.ingresaVectorSesion(v);
            JOptionPane.showMessageDialog(null, "Archivo Guardado con exito");
        } else {
            JOptionPane.showMessageDialog(null, "No existen figuras dentro del panel de dibujo");
        }
    }
    public JFileChooser createJFileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setAcceptAllFileFilterUsed(false);
        String[] fileTypes = getFormats();
        for (int i = 0; i < fileTypes.length; i++) {
            jfc.addChoosableFileFilter(new MyFileFilter(fileTypes[i], fileTypes[i] + " file"));

        }
        return jfc;
    }

}
