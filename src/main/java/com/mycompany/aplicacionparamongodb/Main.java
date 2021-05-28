/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aplicacionparamongodb;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        var connector = new Connector("Scott");
        
        var docs_list = new ArrayList<Document>();
        
        var doc = new Document();
        doc.append("DEPTNO", "10");
        doc.append("DNAME", "ACCOUNTING");
        doc.append("LOC", "NEW YORK");
        docs_list.add(doc);
        
        doc = new Document();
        doc.append("DEPTNO", "20");
        doc.append("DNAME", "RESEARCH");
        doc.append("LOC", "DALLAS");
        docs_list.add(doc);
        
        doc = new Document();
        doc.append("DEPTNO", "30");
        doc.append("DNAME", "SALES");
        doc.append("LOC", "CHICAGO");
        docs_list.add(doc);
        
        doc = new Document();
        doc.append("DEPTNO", "40");
        doc.append("DNAME", "OPERATIONS");
        doc.append("LOC", "BOSTON");
        docs_list.add(doc);
        
        connector.insertarEn(docs_list, "Dept");
        
        connector.findEn("Dept");
        
        connector.close();
    }
    
}
