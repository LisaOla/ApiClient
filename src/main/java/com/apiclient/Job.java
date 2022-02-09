package com.apiclient;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.StringWriter;
import java.io.Writer;

public class Job implements Jsonable {
    public int id;
    public int basesalary;
    public String title;

    enum keys implements JsonKey {
        ID("id"),
        BASESALARY("basesalary"),
        TITLE("title");

        private final Object value;

        /**
         * Instantiates a JsonKey with the provided value.
         *
         * @param value represents a valid default for the key.
         */
        keys(final Object value) {this.value = value;}

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            /* Can represent a valid default, error value, or null adhoc for the JsonKey. See the javadocs for more
             * information about its intended use. */
            return this.value;
        }
    }

    //Constructors
    public Job() {
    }

    public Job(String title, int basesalary) {
        this.title = title;
        this.basesalary = basesalary;
    }

    public Job(int id, String title, int basesalary) {
        this.id = id;
        this.title = title;
        this.basesalary = basesalary;
    }


    public String getTitle() {
        return this.title;
    }

    public int getBasesalary() {
        return this.basesalary;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final Exception e) {
            /* See java.io.StringWriter. */
        }
        return writable.toString();
    }

    @Override
    public void toJson(final Writer writable) {
        try {
            final JsonObject json = new JsonObject();
            json.put(keys.ID.getKey(), this.getID());
            json.put(keys.TITLE.getKey(), this.getTitle());
            json.put(keys.BASESALARY.getKey(), this.getBasesalary());
            json.toJson(writable);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public String toString() {
        return "JsonSimpleExample [id=\" + this.id + \", title=" + this.title + ", basesalary=" + this.basesalary + "]";
    }
}
