package io.github.hufghani.emergencyairways.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hamzaghani on 02/03/2018.
 */

public class Algorithm implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("entrypoint")
    @Expose
    private String entrypoint;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    private final static long serialVersionUID = -6452266806638566006L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Algorithm() {
    }

    /**
     *
     * @param name
     * @param entrypoint
     * @param steps
     */
    public Algorithm(String name, String entrypoint, List<Step> steps) {
        super();
        this.name = name;
        this.entrypoint = entrypoint;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Step findStep(String stepId) {
        Iterator it = getSteps().iterator();
        while (it.hasNext()) {
            Step step = (Step) it.next();
            if (step.getId().equals(stepId)) {
                return step;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Algorithm{").append("name='").append(name).append('\'').append(", entrypoint='").append(entrypoint).append('\'').append(", steps=").append(steps).append('}').toString();
    }
}

