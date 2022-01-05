package com.jpa.tutorial.compositeprimarykey;

import java.io.Serializable;
import java.util.Objects;

public class GrandChildId implements Serializable {

    private Child ChildId;

    private String id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrandChildId that = (GrandChildId) o;
        return Objects.equals(ChildId, that.ChildId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ChildId, id);
    }
}
