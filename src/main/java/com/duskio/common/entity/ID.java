package com.duskio.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class ID implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof ID otherObject)) {
            return false;
        }
        Class<?> objectEffectiveClass = other instanceof HibernateProxy
                ? ((HibernateProxy) other).getHibernateLazyInitializer().getPersistentClass()
                : other.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) {
            return false;
        }

        return this.getId() != null && otherObject.getId() != null && this.getId().equals(otherObject.getId());
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return Objects.hashCode(this.getId());
        } else {
            return this instanceof HibernateProxy
                    ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                    : super.hashCode();
        }
    }
}
