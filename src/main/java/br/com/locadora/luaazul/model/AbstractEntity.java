package br.com.locadora.luaazul.model;

public abstract class AbstractEntity<T> {

    public abstract T getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity<?> that = (AbstractEntity<?>) o;

        return this.getId() != null ? this.getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + getId() + "]";
    }
}
