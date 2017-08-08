package com.chmap.kloop.confchmap.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kloop1996 on 09.08.2016.
 */
public class PolutionSet implements Serializable {
    private List<Polution> polutions;

    public PolutionSet(List<Polution> polutions) {
        this.polutions = polutions;
    }

    public List<Polution> getPolutions() {
        return polutions;
    }

    public void setPolutions(List<Polution> polutions) {
        this.polutions = polutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolutionSet that = (PolutionSet) o;

        return polutions != null ? polutions.equals(that.polutions) : that.polutions == null;

    }

    @Override
    public int hashCode() {
        return polutions != null ? polutions.hashCode() : 0;
    }
}
