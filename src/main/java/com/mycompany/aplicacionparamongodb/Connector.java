/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aplicacionparamongodb;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author daniel
 */
public class Connector {
    private MongoDatabase database;
    private MongoClient mongoClient;
    
    public Connector(String database_name) {
        this(database_name, "27017");
    }
    
    public Connector(String database_name, String port) {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:" + port);
            this.mongoClient = mongoClient;
            this.database = mongoClient.getDatabase(database_name);
            System.out.println("Conectado a: " + database.getName());
        } catch (Exception e) {
            System.out.println("Error a la hora de crear conexión a la base de datos: " + e);
        }
    }
    
    public void imprimirColecciones() {
        for (String name : database.listCollectionNames())
            System.out.println(name);
    }
    
    public void crearColeccion(String nombre) {
        try {
            database.createCollection(nombre);
            System.out.println("Colección " + nombre + " creada");
        } catch (MongoCommandException e) {
            database.getCollection(nombre).drop();
        }
    }
    
    public void eliminarColeccion(String nombre_coleccion) {
        try {
            database.getCollection(nombre_coleccion).drop();
        } catch (Exception e) {
            System.out.println("Error al eliminar la colección: " + e);
        }
    }
    
    public void insertarEn(List<Document> documents, String nombre_coleccion){
        try {
            var collection = database.getCollection(nombre_coleccion);
            collection.insertMany(documents);
        } catch (Exception e) {
            System.out.println("Error al insertar en la colección: " + e);
        }
    }
    
    public void insertarEn(Document[] documents, String nombre_coleccion) {
        insertarEn(Arrays.asList(documents), nombre_coleccion);
    }
    
    public void insertarEn(Document document, String nombre_coleccion) {
        var document_list = new ArrayList<Document>();
        document_list.add(document);
        insertarEn(document_list, nombre_coleccion);
    }
    
    public void actualizarEn(Document a_buscar, Document actualizacion, String nombre_coleccion) {
        try {
            var collection = database.getCollection(nombre_coleccion);
            collection.updateOne(a_buscar, new Document("$set", actualizacion));
        } catch (Exception e) {
            System.out.println("Error al actualizar la coleccion " + nombre_coleccion
             + ": " + e);
        }
    }
    
    public void findEn(String nombre_coleccion, Document especificacion){
        try {
            var collection = database.getCollection(nombre_coleccion);
            try (var cur = collection.find(especificacion).iterator()) {
                while (cur.hasNext()) {
                    var doc = cur.next();
                    var keys_and_values = new ArrayList<>(doc.entrySet());

                    System.out.println(keys_and_values);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar: " + e);
        }
    }
    
    public void findEn(String nombre_coleccion) {
        findEn(nombre_coleccion, new Document());
    }
    
    public void eliminarUnoEn(Document a_eliminar, String nombre_coleccion) {
        try {
            var collection = database.getCollection(nombre_coleccion);
            collection.deleteOne(a_eliminar);
            System.out.println("Documento eliminado");
        } catch (Exception e) {
            System.out.println("Error al eliminar " + a_eliminar + " en " + 
                    nombre_coleccion + ": " + e);
        }
    }
    
    public void eliminarTodosEn(Document a_eliminar, String nombre_coleccion) {
        try {
            var collection = database.getCollection(nombre_coleccion);
            collection.deleteMany(a_eliminar);
            System.out.println("Documentos eliminado");
        } catch (Exception e) {
            System.out.println("Error al eliminar " + a_eliminar + " en " + 
                    nombre_coleccion + ": " + e);
        }
    }
    
    public void close() {
        mongoClient.close();
    }
}
