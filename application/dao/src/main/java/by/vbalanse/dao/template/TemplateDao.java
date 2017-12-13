package by.vbalanse.dao.template;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.template.EmailTemplateEntity;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface TemplateDao extends AbstractManagedEntityDao<EmailTemplateEntity> {
  EmailTemplateEntity getByCode(String code);
}
