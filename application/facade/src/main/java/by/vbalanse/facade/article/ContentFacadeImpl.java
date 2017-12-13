package by.vbalanse.facade.article;

/**
 * @author Vasilina Terehova
 */

import by.vbalanse.dao.psychologist.BonusDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.psychologist.BonusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "contentFacade")
public class ContentFacadeImpl implements ContentFacade {

    @Autowired
    private BonusDao bonusDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Long mergeContent(String className, String fieldName, Long pk, String value) {
        if (BonusEntity.class.toString().endsWith("." + className)) {
            BonusEntity bonusEntity = null;
            if (pk != null) {
                bonusEntity = bonusDao.find(pk);
            } else {
                bonusEntity = new BonusEntity();
                bonusEntity.setUserEntity(userDao.find(UserFacadeImpl.getUserId()));
            }
            if (fieldName.equals("title")) {
                bonusEntity.setTitle(value);
            } else if (fieldName.equals("description")) {
                bonusEntity.setDescription(value);
            } else if (fieldName.equals("icon")) {
                bonusEntity.setIconName(value);
            }
            if (bonusEntity.getTitle() == null || bonusEntity.getDescription() == null || bonusEntity.getIconName() == null) {
                bonusEntity.setDirty(true);
            } else {
                bonusEntity.setDirty(false);
            }
            bonusDao.saveOrUpdate(bonusEntity);
            return bonusEntity.getId();
        }
        return null;
    }
}
