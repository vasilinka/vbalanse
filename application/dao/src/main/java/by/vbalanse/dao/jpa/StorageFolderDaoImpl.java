/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */

package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.storage.StorageFolderDao;
import by.vbalanse.model.storage.StorageFolderEntity;
import by.vbalanse.model.storage.StorageFolderEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("storageFolderDao")
public class StorageFolderDaoImpl extends AbstractManagedEntityDaoSpringImpl<StorageFolderEntity> implements StorageFolderDao {

  @SuppressWarnings("unchecked")
  public StorageFolderEntity getByCode(String code) {
    CriteriaQuery<StorageFolderEntity> criteriaQuery = createCriteria(StorageFolderEntity.class);
    Root from = criteriaQuery.from(StorageFolderEntity.class);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    criteriaQuery.where(criteriaBuilder.equal(from.get(StorageFolderEntity_.code), code));
    //criteriaQuery.setCacheable(true);

    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    //query.setC
    return (StorageFolderEntity) query.getSingleResult();
  }

  @Override
  public void delete(StorageFolderEntity entity) {
    throw new UnsupportedOperationException("This is static enum, stored in the database. Can not be deleted.");
  }

}
