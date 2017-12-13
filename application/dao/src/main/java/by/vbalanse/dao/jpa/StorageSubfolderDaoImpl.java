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
import by.vbalanse.dao.common.jpa.DaoUtils;
import by.vbalanse.dao.storage.StorageSubfolderDao;
import by.vbalanse.model.storage.StorageSubfolderEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository("storageSubfolderDao")
public class StorageSubfolderDaoImpl extends AbstractManagedEntityDaoSpringImpl<StorageSubfolderEntity>
    implements StorageSubfolderDao {

//  @Override
//  public void delete(StorageSubfolderEntity entity) {
//    super.delete(entity);
  // todo: must check that folder is temp and try to delete it. If it is successfull, delete the entity, otherwise must throw exception
  //throw new UnsupportedOperationException("Not yet implemented");
//  }

  @SuppressWarnings("unchecked")
  public List<StorageSubfolderEntity> findTempStorageFileList(int maxCountOfSubfolder) {
    CriteriaQuery criteriaQuery = createCriteria(StorageSubfolderEntity.class);
    Root from = criteriaQuery.from(StorageSubfolderEntity.class);
    long hour = 1000 * 60 * 60;
    Date hourAgo = new Date(new Date().getTime() - hour);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    //ParameterExpression<Date> param = criteriaBuilder.parameter(Date.class);
    criteriaQuery.where(criteriaBuilder.equal(from.get(StorageSubfolderEntity_.temp), true));
    criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(from.get(StorageSubfolderEntity_.dateOfCreate), hourAgo));
    DaoUtils.addOrder(criteriaQuery, criteriaBuilder, from.get(StorageSubfolderEntity_.id), true);
    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    query.setMaxResults(maxCountOfSubfolder);
    //query.setParameter(param, hourAgo);
    return query.getResultList();
  }
}
