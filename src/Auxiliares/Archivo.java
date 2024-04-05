package Auxiliares;

import formas.Figura;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;


public class Archivo {
    public boolean leerArchivo(String file){
        boolean bandera = true;
        try {
            FileInputStream archivo = new FileInputStream(file);
            ObjectInputStream objeto = new ObjectInputStream(archivo);
            objeto.close();
            archivo.close();
        } catch (FileNotFoundException e) {
            bandera = false;
        }catch(IOException ex){
            //JOptionPane.showInternalMessageDialog(null, ex.getMessage());
            System.out.println(ex);
        }
        return bandera;
    }
    public void crearArchivo(String file, Object objeto){
        try {
            FileOutputStream archivo2 = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(archivo2);
            os.writeObject(objeto);
            os.close();
        } catch (IOException ex) {
            System.out.println(ex);
            //JOptionPane.showInternalMessageDialog(null, ex.getMessage());
        }
    }
    public void borrarArchivo(File file){
        try {
            if(file.exists()){
                file.delete();
            }
        } catch (Exception e) {
            //JOptionPane.showInternalMessageDialog(null, e.getMessage());
            System.out.println(e);
        }
    }
    public void ingresaFigura(Figura f){
        GeneralPath path = f.getPath();
        if(!leerArchivo("Figuras.pdd")){
            crearArchivo("Figuras.pdd", new HashMap<String, GeneralPath>());
        }
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la figura");
        try {
            FileInputStream archivo = new FileInputStream("Figuras.pdd");
            ObjectInputStream os = new ObjectInputStream(archivo);
            HashMap<String,GeneralPath> figuras = new HashMap<>();
            figuras = (HashMap<String, GeneralPath>) os.readObject();
            os.close();
            archivo.close();
            while(figuras.containsKey(nombre)){
                JOptionPane.showMessageDialog(null, "Figura con ese nombre se encuentra en existencia");
                nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la figura");
            }
            figuras.put(nombre, path);
            os.close();
            archivo.close();
            File archivo2 = new File("Figuras.pdd");
            borrarArchivo(archivo2);
            crearArchivo("Figuras.pdd", figuras);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        } catch(IOException ex){
            System.out.println(ex);
            //JOptionPane.showInternalMessageDialog(null, ex.getMessage());
        } catch(ClassNotFoundException ex){
            System.out.println(ex);
            //JOptionPane.showInternalMessageDialog(null, ex.getMessage());
        }
    }
    public HashMap<String,GeneralPath> leerFiguras(){
        HashMap<String,GeneralPath> figuras = null;
        if(!leerArchivo("Figuras.pdd")){
            crearArchivo("Figuras.pdd", new HashMap<String, Figura>());
        }
        try {
            FileInputStream archivo = new FileInputStream("Figuras.pdd");
            ObjectInputStream os = new ObjectInputStream(archivo);
            figuras = new HashMap<>();
            figuras = (HashMap<String, GeneralPath>) os.readObject();
            if(figuras.size()==0){
                figuras=null;
            }
            os.close();
            archivo.close();
        } catch (FileNotFoundException e) {
        } catch(IOException ex){
            JOptionPane.showInternalMessageDialog(null, ex.getMessage());
        } catch(ClassNotFoundException ex){
            JOptionPane.showInternalMessageDialog(null, ex.getMessage());
        }
        return figuras;
    }
    public void abrirSesion(ArrayList<GuardarFigura>fs){
        String nombre = JOptionPane.showInputDialog(null, "Ingresa el nombre se la sesión");
        if(!leerArchivo(nombre+".spd")){
            crearArchivo(nombre+".spd", fs);
        }else{
            while(leerArchivo(nombre+".spd")){
                JOptionPane.showMessageDialog(null, "El archivo "+nombre+"ya existe");
                nombre = JOptionPane.showInputDialog(null, "Ingresa el nombre se la sesión");
            }
        }
    }
    public void ingresaVectorSesion(ArrayList<GuardarFigura> f){
        String nom = JOptionPane.showInputDialog(null, "Ingresa el nombre del archivo de sesion sin extension");
        if (!leerArchivo(nom +".spd")){
            crearArchivo(nom + ".spd",f);
        }
        else{
            while (leerArchivo(nom +".spd")){
                JOptionPane.showMessageDialog(null, "El archivo con nombre " + nom + " ya exite");
                nom = JOptionPane.showInputDialog(null, "Ingresa el nombre del archivo de sesion sin extension");
            }
        }
    }
}
