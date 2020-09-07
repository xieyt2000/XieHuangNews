package com.java.xieyuntong.backend.kg;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

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

        // class variables
        private final String type;
        private final String label;
        private final Hierarchy hierarchy;

        // constructor
        public Relation(String type, String label, boolean forward) {
            this.type = type;
            this.label = label;
            this.hierarchy = forward ? Hierarchy.Parent : Hierarchy.Child;
        }


        //getter

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        public Hierarchy getHierarchy() {
            return hierarchy;
        }
    }


    //class variable

    final private String label;
    final private Bitmap img;
    final private String description;
    final private LinkedHashMap<String, String> properties;
    final private ArrayList<Relation> relations;


    // constructor

    public Entity(String label, Bitmap img, String description,
                  LinkedHashMap<String, String> properties, ArrayList<Relation> relations) {
        this.label = label;
        this.img = img;
        this.description = description;
        this.properties = properties;
        this.relations = relations;
    }


    // getter

    public String getLabel() {
        return label;
    }

    public Bitmap getImg() {
        return img;
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
