package com.geico.collections.quadtree;

import com.geico.collections.Geolocation;

import java.util.ArrayList;
import java.util.List;

public class QuadTree<T> {
    private static final int CAPACITY = 4;

    private final Boundary boundary;
    private final List<Element> elements;
    private final QuadTree<T>[] children;

    public QuadTree(Boundary boundary) {
        this.boundary = boundary;
        this.elements = new ArrayList<>();
        this.children = new QuadTree[4];
    }


    private class Element<T> {

        Geolocation geolocation;
        T element;
        Element(T element, Geolocation geolocation) {
            this.element = element;
            this.geolocation = geolocation;
        }
    }

    private boolean insert(Element<T> element) {
        return insert(element.geolocation, element.element);
    }

    public boolean insert(Geolocation point, T element) {
        if (!boundary.contains(point)) {
            return false;
        }

        if (elements.size() < CAPACITY) {
            elements.add(new Element<T>(element, point));
            return true;
        }

        if (children[0] == null) {
            subdivide();
        }

        for (int i = 0; i < 4; i++) {
            if (children[i].insert(point, element)) {
                return true;
            }
        }
        return false;
    }

    public List<T> queryRange(Boundary range) {
        List<T> pointsInRange = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return pointsInRange;
        }

        for (Element<T> element : elements) {
            if (range.contains(element.geolocation)) {
                pointsInRange.add(element.element);
            }
        }

        if (children[0] != null) {
            for (int i = 0; i < 4; i++) {
                pointsInRange.addAll(children[i].queryRange(range));
            }
        }

        return pointsInRange;
    }

    private void subdivide() {
        double x = boundary.getX();
        double y = boundary.getY();
        double w = boundary.getWidth() / 2;
        double h = boundary.getHeight() / 2;

        children[0] = new QuadTree<>(new Boundary(x, y, w, h));
        children[1] = new QuadTree<>(new Boundary(x + w, y, w, h));
        children[2] = new QuadTree<>(new Boundary(x, y + h, w, h));
        children[3] = new QuadTree<>(new Boundary(x + w, y + h, w, h));

        for (Element<T> element : elements) {
            for (int i = 0; i < 4; i++) {
                if (children[i].insert(element)) {
                    break;
                }
            }
        }
        elements.clear();
    }
}
