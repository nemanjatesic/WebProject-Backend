package mvcrest.avioni;

import java.io.Serializable;
import java.util.Objects;

public class AvionskaKompanija implements Serializable {
    private int id;
    private String name;
    private int version;

    public AvionskaKompanija() {
    }

    public AvionskaKompanija(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public AvionskaKompanija id(int id) {
        this.id = id;
        return this;
    }

    public AvionskaKompanija name(String name) {
        this.name = name;
        return this;
    }

    public AvionskaKompanija version(int version) {
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvionskaKompanija)) return false;
        AvionskaKompanija that = (AvionskaKompanija) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "AvionskaKompanija{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
