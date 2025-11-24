package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class StudentResponse {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private List<Integer> marks;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.marks);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentResponse other = (StudentResponse) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.marks, other.marks);
    }


    @Override
    public String toString() {
        return "StudentResponse{id=" + id + ", name='" + name + "', marks=" + marks + "}";
    }

}
