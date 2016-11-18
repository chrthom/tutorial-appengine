package de.oc.cloud.techsurvey.container;

public class Container<T extends Container<T>> {
    String id;
    Long addedDate;
    Long modifiedDate;

    public String getId() {
        return id;
    }

    public T setId(String id) {
        this.id = id;
        return (T) this;
    }

    public Long getAddedDate() {
        return addedDate;
    }

    public T setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
        return (T) this;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public T setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
        return (T) this;
    }
}
