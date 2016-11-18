package de.oc.cloud.techsurvey.persistence;

import com.google.appengine.api.datastore.*;
import de.oc.cloud.techsurvey.container.Tech;

import java.util.ArrayList;
import java.util.List;

public class TechDAO {
    private static TechDAO instance;

    public static TechDAO getInstance() {
        if (instance == null) instance = new TechDAO();
        return instance;
    }

    private final long INITIAL_SCORE = 100;

    public List<Tech> list() {
        List<Tech> result = new ArrayList<>();
        Query query = new Query("tech").addSort("score", Query.SortDirection.DESCENDING);
        PreparedQuery preparedQuery = DatastoreServiceFactory.getDatastoreService().prepare(query);
        for(Entity entity : preparedQuery.asList(FetchOptions.Builder.withDefaults()))
            result.add(entityToTech(entity));
        return result;
    }

    public Tech get(String id) throws EntityNotFoundException {
        return entityToTech(DatastoreServiceFactory.getDatastoreService().get(KeyFactory.stringToKey(id)));
    }

    public Tech insert(Tech tech) {
        return put(tech, false);
    }

    public Tech update(Tech tech) {
        return put(tech, true);
    }

    private Tech put(Tech tech, boolean update) {
        tech.setModifiedDate(System.currentTimeMillis());
        if (!update)
            tech.setAddedDate(System.currentTimeMillis()).setScore(INITIAL_SCORE);
        Key key = DatastoreServiceFactory.getDatastoreService().put(techToEntity(tech));
        if (!update)
            tech.setId(KeyFactory.keyToString(key));
        return tech;
    }
    public void delete(String id) {
        DatastoreServiceFactory.getDatastoreService().delete(KeyFactory.stringToKey(id));
    }

    private Tech entityToTech(Entity entity) {
        return new Tech().setId(KeyFactory.keyToString(entity.getKey()))
                .setAddedDate((Long) entity.getProperty("addedDate"))
                .setModifiedDate((Long) entity.getProperty("modifiedDate"))
                .setName((String) entity.getProperty("name"))
                .setType((String) entity.getProperty("type"))
                .setLogo((String) entity.getProperty("logo"))
                .setScore((Long) entity.getProperty("score"));
    }

    private Entity techToEntity(Tech tech) {
        Entity entity = tech.getId() == null ? new Entity("tech") : new Entity(KeyFactory.stringToKey(tech.getId()));
        entity.setProperty("addedDate", tech.getAddedDate());
        entity.setProperty("modifiedDate", tech.getModifiedDate());
        entity.setProperty("name", tech.getName());
        entity.setProperty("type", tech.getType());
        entity.setProperty("logo", tech.getLogo());
        entity.setProperty("score", tech.getScore());
        return entity;
    }
}
