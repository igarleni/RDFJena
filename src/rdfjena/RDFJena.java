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
        String filename = "C:/Users/Italo/Mis archivos/Universidad/2.-Sistemas Inteligentes II/3.-Web Semántica/Ejercicio/Juan.rdf";
        //fromFOAFtoTURTLEndNTRIPLES(filename);
        
        printKnownPeople(filename);
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

    private static void printKnownPeople(String filename) {
        LogCtl.setCmdLogging();
        printKnownRecursive(filename);
        //obtener objetos de tripletas con propiedad = knows

        //imprimir tripleta sujeto knows objeto

        //acceder a objeto y volver a empezar
    }
    
    private static void printKnownRecursive(String filename){
        Model modelo = ModelFactory.createDefaultModel();
        modelo.read(filename);
        
        StmtIterator iter = modelo.listStatements();
        while(iter.hasNext()){
            System.out.println("----------------------------------");
            System.out.println("--------------OBJETO--------------");
            System.out.println("----------------------------------");
            Statement declaracion = iter.nextStatement();
            
            Resource sujeto = declaracion.getSubject();
            Property propiedad = declaracion.getPredicate();
            RDFNode objeto = declaracion.getObject();
            
            System.out.println(sujeto.toString());
            System.out.println(propiedad.toString());
            System.out.print("Objeto ->");
            if (objeto.isResource()){
                System.out.println(objeto.toString());
            }
            else{
                System.out.println(objeto);
            }
        }
    }
}
