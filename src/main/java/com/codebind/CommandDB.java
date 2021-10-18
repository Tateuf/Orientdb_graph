package com.codebind;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandDB {
    static String databaseName = "" ; //replace by your database name
    static String user = ""; //replace by your username
    static String password = ""; //replace by your password

    public static String NumberToRID(String number){

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                OResultSet response = db.query(String.format("SELECT * from Product WHERE number = '%s'",number));
                List<String> Rids = new ArrayList<String>();
                while (response.hasNext()){
                    OResult i = response.next();
                    Rids.add(i.getProperty("@rid").toString());
                }
                db.close();
                orient.close();
                try {
                    return Rids.get(0);
                }
                catch (Exception e){
                    return "Object not found";
                }
            }
            catch (Exception e){
                db.close();
                orient.close();
                return "Object not found";
            }
    }

    public static String CreateProduct(String number) {

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                OVertex user = db.newVertex("Product");
                user.setProperty("number", number);
                user.save();
                db.close();
                orient.close();
                return NumberToRID(number);
            } catch (Exception e) {
                db.close();
                orient.close();
                return "Creation failed";
            }
    }

    public static String DeleteProduct(String number) {

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                db.command(String.format("DELETE VERTEX Product WHERE number = '%s'",number));
                db.close();
                orient.close();
                return number + " : deleted";
            }
            catch (Exception e){
                return "Delete failed";
            }
        }

    public static String CreateLink(String from, String to){

            from = NumberToRID(from);
            to = NumberToRID(to);

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                db.command(String.format("CREATE EDGE Recommendation FROM %s TO %s",from,to));
                db.close();
                orient.close();
                return "edge created between :"+ from +" and "+ to +" as recommendation";
            }
            catch (Exception e){
                return "Link creation failed";
            }
        }


    public static String DeleteLink(String from, String to){

            from = NumberToRID(from);
            to = NumberToRID(to);

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                db.command(String.format("DELETE EDGE Recommendation FROM %s TO %s",from,to));
                db.close();
                orient.close();
                return "edge deleted between :"+ from +" and "+ to ;
            }
            catch (Exception e){
                return "link deletion failed";
            }
        }

    public static String[] ShortestPath(String from, String to){
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        ODatabaseSession db = orient.open(databaseName, user, password);
        String fromQuery = String.format("Select From Product where number = '%s'",from);
        String toQuery = String.format("Select From Product where number = '%s'",to);
        OResultSet response = db.command(String.format("Select shortestPath((%s),(%s))",fromQuery,toQuery));
        String initialPath = "";
        while (response.hasNext()){
            OResult i = response.next();
            initialPath = i.toString();
        }
        String[] path = initialPath.split("\\[");
        String path2 = path[1].replace(" ","").replace("[","").replace("]","").replace("}","");
        String[] arrayPath = path2.split(",");
        db.close();
        orient.close();
        return arrayPath;
    }

    //creation of Vertex and Edge
    public static String CreateClass(String classname, String parentName){

            OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseSession db = orient.open(databaseName, user, password);
            try {
                db.command(String.format("CREATE CLASS %s EXTENDS %s",classname,parentName));
                db.close();
                orient.close();
                return "class : "+ classname + " created ";
            }
            catch (Exception e){
                db.close();
                orient.close();
                return "ERROR: class : "+ classname + " not created ";
            }
        }

    public static String[] OutRecommendation(String number){
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        ODatabaseSession db = orient.open(databaseName, user, password);
        OResultSet response = db.command(String.format("Select expand(out()) from Product where number = '%s'",number));
        ArrayList<String> recommendationList = new ArrayList<String>();
        String initialPath = "";
        while (response.hasNext()){
            OResult i = response.next();
            initialPath += i.toString() + ";";
        }
        String[] path = initialPath.split(";");
        for(int i=0; i < path.length; i++){
            String[] path2 = path[i].split("\\{");
            String path3 = path2[0].replace("$","").replace("Product","");
            recommendationList.add(path3);
        }
        db.close();
        orient.close();
        String[] result = new String[recommendationList.size()];
        for (int i = 0; i < recommendationList.size(); i++)
            result[i] = recommendationList.get(i);
        return result;
    }
}
