package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.user.psychologist.TherapyDimensionDao;
import by.vbalanse.model.psychologist.TherapyDimensionEntity;
import org.springframework.stereotype.Repository;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository("therapyDimensionDao")
public class TherapyDimensionDaoImpl extends AbstractManagedEntityDaoSpringImpl<TherapyDimensionEntity> implements TherapyDimensionDao {
}
