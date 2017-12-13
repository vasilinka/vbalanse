package by.vbalanse.rest;

import by.vbalanse.dao.geography.CityDao;
import by.vbalanse.dao.user.psychologist.TherapyDimensionDao;
import by.vbalanse.dao.user.psychologist.WithWhomWorksDao;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.TherapyDimensionEntity;
import by.vbalanse.model.psychologist.WithWhomWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/psy/data2")
public class DataService2 {
  @Autowired
  WithWhomWorksDao withWhomWorksDao;

  @Autowired
  CityDao cityDao;

  @Autowired
  private TherapyDimensionDao therapyDimensionDao;

  @RequestMapping(value = "workWith", method = RequestMethod.GET)
  public @ResponseBody List<WithWhomWorks> workWith() {
    return withWhomWorksDao.findAll();
  }

  @RequestMapping(value = "cities", method = RequestMethod.GET)
  public @ResponseBody List<CityEntity> findCities() {
    return cityDao.findAll();
  }

  @RequestMapping(value = "therapyDimensions", method = RequestMethod.GET)
  public @ResponseBody List<TherapyDimensionEntity> findTherapyDimensions() {
    return therapyDimensionDao.findAll();
  }
}
