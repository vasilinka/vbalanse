package by.vbalanse.facade.article;

import by.vbalanse.dao.psychologist.BonusDao;
import by.vbalanse.model.psychologist.BonusEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vasilina Terehova.
 */
public interface ContentFacade {

  Long mergeContent(String className, String fieldName, Long pk, String value);
}
