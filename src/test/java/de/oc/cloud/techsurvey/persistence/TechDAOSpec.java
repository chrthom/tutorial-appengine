package de.oc.cloud.techsurvey.persistence;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import de.oc.cloud.techsurvey.container.Tech;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TechDAOSpec {
    private LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private TechDAO dao = new TechDAO();

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testGivenInsertTechThenReturnedAttributesShallBeEqual() throws EntityNotFoundException {
        Tech originalTech = sampleTech();
        Tech insertedTech = dao.insert(originalTech);
        assertThat(insertedTech.getName(), is(equalTo(originalTech.getName())));
        assertThat(insertedTech.getScore(), is(equalTo(originalTech.getScore())));
        assertNotNull(insertedTech.getId());
    }

    @Test
    public void testGivenInsertTechThenReturnedIdAndTimestampsAreSet() throws EntityNotFoundException {
        Tech originalTech = sampleTech();
        Tech insertedTech = dao.insert(originalTech);
        assertNotNull(insertedTech.getId());
        assertNotNull(insertedTech.getAddedDate());
        assertNotNull(insertedTech.getModifiedDate());
    }

    @Test
    public void testGivenInsertTechWhenGetByIdThenEqualsOriginal() throws EntityNotFoundException {
        Tech originalTech = sampleTech();
        Tech fetchedTech = dao.get(dao.insert(originalTech).getId());
        assertThat(fetchedTech.getName(), is(equalTo(originalTech.getName())));
        assertThat(fetchedTech.getScore(), is(equalTo(originalTech.getScore())));
    }

    @Test
    public void testGivenInsertTechWhenGetByIdThenIdAndTimestampsAreSet() throws EntityNotFoundException {
        Tech originalTech = sampleTech();
        Tech fetchedTech = dao.get(dao.insert(originalTech).getId());
        assertNotNull(fetchedTech.getId());
        assertNotNull(fetchedTech.getAddedDate());
        assertNotNull(fetchedTech.getModifiedDate());
    }

    @Test
    public void testGivenInsert2TechsWhenFetchAllThenGet2Techs() throws EntityNotFoundException {
        Tech originalTech1 = sampleTech();
        Tech originalTech2 = anotherSampleTech();
        dao.insert(originalTech1);
        assertThat(dao.list().size(), is(equalTo(1)));
        dao.insert(originalTech2);
        assertThat(dao.list().size(), is(equalTo(2)));
    }

    @Ignore
    @Test(expected=Exception.class)
    public void testGiven1TechInsertedWhenInsertTechWithSameNameThenThrowException() throws EntityNotFoundException {
        Tech originalTech = sampleTech();
        dao.insert(originalTech);
        dao.insert(originalTech);
    }

    private Tech sampleTech() {
        return new Tech().setName("Java");
    }

    private Tech anotherSampleTech() {
        return new Tech().setName("Cobol");
    }
}
