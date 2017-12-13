package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.geography.CityDao;
import by.vbalanse.model.geography.CityEntity;
import org.springframework.stereotype.Repository;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "cityDao")
public class CityDaoImpl extends AbstractManagedEntityDaoSpringImpl<CityEntity> implements CityDao {
}
