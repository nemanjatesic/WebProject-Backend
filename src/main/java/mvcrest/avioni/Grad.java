package mvcrest.avioni;

import java.util.Objects;

public class Grad {
    private int id;
    private String name;
    private int version;

    public Grad() {
    }

    public Grad(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public Grad id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grad)) return false;
        Grad grad = (Grad) o;
        return Objects.equals(name, grad.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Grad name(String name) {
        this.name = name;
        return this;
    }

    public Grad version(int version) {
        this.version = version;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Grad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
