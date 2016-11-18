package de.oc.cloud.techsurvey.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import de.oc.cloud.techsurvey.container.Preference;
import de.oc.cloud.techsurvey.container.Tech;
import de.oc.cloud.techsurvey.container.TechDecision;
import de.oc.cloud.techsurvey.persistence.TechDAO;

import java.util.List;

@Api(canonicalName = "Tech Survey",
        description = "Services for the Tech Survey frontend",
        name = "techsurvey",
        namespace = @ApiNamespace(
                ownerDomain = "opitz-consulting.de",
                ownerName = "OPITZ CONSULTING Deutschland GmbH",
                packagePath = "de/oc/cloud/octechpoll/api/techpoll"),
        title = "TechSurvey",
        version = "v1")
public class TechPollV1API {
    @ApiMethod(name = "tech.list",
            path = "tech",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Tech> listTech() {
        return TechDAO.getInstance().list();
    }

    @ApiMethod(name = "tech.get",
            path = "tech/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Tech getTech(@Named("id") String techId) throws NotFoundException {
        try {
            return TechDAO.getInstance().get(techId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    @ApiMethod(name = "tech.insert",
            path = "tech",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Tech insertTech(Tech tech) {
        return TechDAO.getInstance().insert(tech);
    }

    @ApiMethod(name = "tech.update",
            path = "tech",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Tech updateTech(Tech tech) {
        return TechDAO.getInstance().update(tech);
    }

    @ApiMethod(name = "tech.delete",
            path = "tech/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void deleteTech(@Named("id") String techId) {
        TechDAO.getInstance().delete(techId);
    }

    @ApiMethod(name = "tech.choose",
            path="choose",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void choose(TechDecision decision) throws NotFoundException {
        try {
            Tech techLeft = TechDAO.getInstance().get(decision.getLeftTechId());
            Tech techRight = TechDAO.getInstance().get(decision.getRightTechId());
            Tech winnerTech = decision.getPreference() == Preference.left ? techLeft : techRight;
            Tech loserTech = decision.getPreference() == Preference.right ? techLeft : techRight;
            double winnerChance = 1 / (1 + Math.pow(10, ((double) loserTech.getScore() - winnerTech.getScore()) / 400));
            double loserChance = 1 / (1 + Math.pow(10, ((double) winnerTech.getScore() - loserTech.getScore()) / 400));
            winnerTech.setScore(Math.round(winnerTech.getScore() + 20 * (1 - winnerChance)));
            loserTech.setScore(Math.round(loserTech.getScore() - 20 * loserChance));
            TechDAO.getInstance().update(winnerTech);
            TechDAO.getInstance().update(loserTech);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        }
    }
}
