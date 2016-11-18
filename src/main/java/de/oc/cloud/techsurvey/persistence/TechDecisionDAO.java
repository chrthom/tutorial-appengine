package de.oc.cloud.techsurvey.persistence;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import de.oc.cloud.techsurvey.container.TechDecision;

public class TechDecisionDAO {
    public TechDecision insert(TechDecision rating) {
        rating.setAddedDate(System.currentTimeMillis()).setModifiedDate(System.currentTimeMillis());
        Key key = DatastoreServiceFactory.getDatastoreService().put(techDecisionToEntity(rating));
        return rating.setId(KeyFactory.keyToString(key));
    }

    private Entity techDecisionToEntity(TechDecision decision) {
        Entity entity = decision.getId() == null ? new Entity("decision") : new Entity("decision", KeyFactory.stringToKey(decision.getId()));
        entity.setProperty("addedDate", decision.getAddedDate());
        entity.setProperty("modifiedDate", decision.getModifiedDate());
        entity.setProperty("leftTechId", decision.getLeftTechId());
        entity.setProperty("rightTechId", decision.getRightTechId());
        return entity;
    }
}
