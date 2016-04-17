package rdfjena;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;


/**
 *
 * @author Italo
 */
public class RDFJena {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename = "Juan.rdf";
        //fromFOAFtoTURTLEndNTRIPLES(filename);
        
        printKnownPeople(filename, "Juan Santana");
    }

    private static void fromFOAFtoTURTLEndNTRIPLES(String filename) {        
        LogCtl.setCmdLogging();
        Model modelo = ModelFactory.createDefaultModel();
        modelo.read(filename);
        System.out.println("----------------------------------");
        System.out.println("--------------TURTLE--------------");
        System.out.println("----------------------------------");
        RDFDataMgr.write(System.out,modelo,RDFFormat.TURTLE_PRETTY);
        
        System.out.println("----------------------------------");
        System.out.println("-------------NTRIPLES-------------");
        System.out.println("----------------------------------");
        RDFDataMgr.write(System.out,modelo,RDFFormat.NTRIPLES);
        
        
    }

    private static void printKnownPeople(String filename, String name) {
        LogCtl.setCmdLogging();
        printKnownRecursive(filename, name);
        //obtener objetos de tripletas con propiedad = knows

        //imprimir tripleta sujeto knows objeto

        //acceder a objeto y volver a empezar
    }
    
    private static void printKnownRecursive(String filename, String name){
        Model modelo = ModelFactory.createDefaultModel();
        modelo.read(filename);
        
        StmtIterator iter = modelo.listStatements();
        while(iter.hasNext()){
            Statement declaracion = iter.nextStatement();
            if (declaracion.getPredicate().toString().contains("knows")){
                String personKnownName = findPersonName(modelo, declaracion.getObject());
                if (personKnownName != null){
                    System.out.println(name + " conoce a " + personKnownName);
                    String personKnownFilename = findPersonFilename(modelo, declaracion.getObject());
                    if (personKnownFilename != null){
                        System.out.println(personKnownFilename);
                        printKnownRecursive(personKnownFilename, personKnownName);
                    }else
                        System.out.println("Fallo al buscar direccion");
                }
                else
                    System.out.println("Fallo al buscar el nombre");
            }
        }
    }

    private static String findPersonName(Model modelo, RDFNode referencia) {
        StmtIterator iter = modelo.listStatements();
        while(iter.hasNext()){
            Statement declaracion = iter.nextStatement();
            if ((declaracion.getSubject().toString().equals(referencia.toString())) && (declaracion.getPredicate().toString().contains("name"))){
                return declaracion.getObject().toString();
            }
        }
        return null;
    }

    private static String findPersonFilename(Model modelo, RDFNode referencia) {
        StmtIterator iter = modelo.listStatements();
        while(iter.hasNext()){
            Statement declaracion = iter.nextStatement();
            if ((declaracion.getSubject().toString().equals(referencia.toString())) && (declaracion.getPredicate().toString().contains("seeAlso"))){
                String result = declaracion.getObject().toString();
                return result.substring(result.lastIndexOf('/')+1);
            }
        }
        return null;
    }
}
