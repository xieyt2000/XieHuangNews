package com.java.xieyuntong.backend.kg;

import androidx.annotation.NonNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Entity {

    // nested class

    public static class Relation {

        public enum Hierarchy {
            Parent("父类"), Child("子类");
            private String text;

            Hierarchy(String text) {
                this.text = text;
            }

            @NonNull
            @Override
            public String toString() {
                return text;
            }
        }

        final String type;
        final String label;
        final Hierarchy hierarchy;

        public Relation(String type, String label, boolean forward) {
            this.type = type;
            this.label = label;
            this.hierarchy = forward ? Hierarchy.Parent : Hierarchy.Child;
        }
    }


    //class variable

    final private String label;
    final private URL imgURL;
    final private String description;
    final private LinkedHashMap<String, String> properties;
    final private ArrayList<Relation> relations;


    // constructor

    public Entity(String label, URL imgURL, String description,
                  LinkedHashMap<String, String> properties, ArrayList<Relation> relations) {
        this.label = label;
        this.imgURL = imgURL;
        this.description = description;
        this.properties = properties;
        this.relations = relations;
    }


    // getter

    public String getLabel() {
        return label;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public String getDescription() {
        return description;
    }

    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }
}
