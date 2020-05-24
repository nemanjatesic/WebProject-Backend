package mvcrest.avioni;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Let {
    private int id;
    private List<AvionskaKarta> avionskeKarte;
    private Grad grad_origin;
    private Grad grad_destination;
    private int version;

    public Let() {
        avionskeKarte = new ArrayList<>();
    }

    public Let(int id, List<AvionskaKarta> avionskeKarte, Grad grad_origin, Grad grad_destination, int version) {
        this.id = id;
        this.avionskeKarte = avionskeKarte;
        this.grad_origin = grad_origin;
        this.grad_destination = grad_destination;
        this.version = version;
    }

    public Let id(int id) {
        this.id = id;
        return this;
    }

    public Let avionskeKarte(List<AvionskaKarta> avionskeKarte) {
        this.avionskeKarte = avionskeKarte;
        return this;
    }

    public Let grad_origin(Grad grad_origin) {
        this.grad_origin = grad_origin;
        return this;
    }

    public Let grad_destination(Grad grad_destination) {
        this.grad_destination = grad_destination;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Let)) return false;
        Let let = (Let) o;
        return Objects.equals(grad_origin, let.grad_origin) &&
                Objects.equals(grad_destination, let.grad_destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grad_origin, grad_destination);
    }

    public Let version(int version) {
        this.version = version;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AvionskaKarta> getAvionskeKarte() {
        return avionskeKarte;
    }

    public void setAvionskeKarte(List<AvionskaKarta> avionskeKarte) {
        this.avionskeKarte = Objects.requireNonNullElseGet(avionskeKarte, ArrayList::new);
    }

    public Grad getGrad_origin() {
        return grad_origin;
    }

    public void setGrad_origin(Grad grad_origin) {
        this.grad_origin = grad_origin;
    }

    public Grad getGrad_destination() {
        return grad_destination;
    }

    public void setGrad_destination(Grad grad_destination) {
        this.grad_destination = grad_destination;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Let{" +
                "id=" + id +
                ", avionskeKarte=" + avionskeKarte +
                ", grad_origin=" + grad_origin +
                ", grad_destination=" + grad_destination +
                ", version=" + version +
                '}';
    }
}
