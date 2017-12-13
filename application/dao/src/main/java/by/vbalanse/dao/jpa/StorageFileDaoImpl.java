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
import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.AbstractStorageFileEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository("storageFileDao")
public class StorageFileDaoImpl extends AbstractManagedEntityDaoSpringImpl<AbstractStorageFileEntity> implements StorageFileDao {

  @Override
  public void save(AbstractStorageFileEntity entity) {
    entity.setDateOfLastUpdate(new Date());
    super.save(entity);
  }

  @Override
  public void saveOrUpdate(AbstractStorageFileEntity entity) {
    entity.setDateOfLastUpdate(new Date());
    super.saveOrUpdate(entity);
  }

  @Override
  public void update(AbstractStorageFileEntity entity) {
    entity.setDateOfLastUpdate(new Date());
    super.update(entity);
  }


//  @Override
//  public void delete(AbstractStorageFileEntity entity) {
//    super.delete(entity);
//    todo: must check that folder is temp and try to delete it. If it is successfull, delete the entity, otherwise must throw exception
//    throw new UnsupportedOperationException("Not yet implemented");
//  }

  @SuppressWarnings("unchecked")
  public List<AbstractStorageFileEntity> findTempStorageFileList(int maxCountOfFile) {
    CriteriaQuery criteriaQuery = createCriteria(AbstractStorageFileEntity.class);
    Root from = criteriaQuery.from(AbstractStorageFileEntity.class);
    CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
    long hour = 1000 * 60 * 60;
    Date hourAgo = new Date(new Date().getTime() - hour);
    criteriaQuery.where(criteriaBuilder.equal(from.get(AbstractStorageFileEntity_.temp), true));
    //ParameterExpression<Date> param = criteriaBuilder.parameter(Date.class);
    criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(from.get(AbstractStorageFileEntity_.dateOfCreate), hourAgo));
    DaoUtils.addOrder(criteriaQuery,criteriaBuilder, from.get(AbstractStorageFileEntity_.id), true);
    TypedQuery query = getEntityManager().createQuery(criteriaQuery);
    //query.setParameter(param, hourAgo);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Long> findUsedTempStorageFileIdList() {
    Query query = createSqlQuery("" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$document a on a.fk_stg_file$file = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$file is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$audio a on a.fk_stg_file$audio = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$audio is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$file a on a.fk_stg_file = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$content_page a on a.fk_stg_file$archive = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$archive is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$image a on a.fk_stg_file$image = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$image is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$image a on a.fk_stg_file$image$thumbnail = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$image$thumbnail is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$video a on a.fk_stg_file$video_flv = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$video_flv is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$video a on a.fk_stg_file$video_mp4 = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$video_mp4 is not null\n" +
        "union \n" +
        "select f.id_ from stg_file f \n" +
        "left join att_attachment$video a on a.fk_stg_file$image = f.id_\n" +
        "where f.is_temp_ = true and a.fk_stg_file$image is not null");

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Long> findNotUsedNotTempStorageFileIdList() {
    Query query = createSqlQuery("" +
        "select f.id_ from stg_file f\n" +
        "left join att_attachment$document doc on doc.fk_stg_file$file = f.id_\n" +
        "left join att_attachment$audio au on au.fk_stg_file$audio = f.id_\n" +
        "left join att_attachment$file file on file.fk_stg_file = f.id_\n" +
        "left join att_attachment$content_page cp on cp.fk_stg_file$archive = f.id_\n" +
        "left join att_attachment$image im on im.fk_stg_file$image = f.id_\n" +
        "left join att_attachment$image imt on imt.fk_stg_file$image$thumbnail = f.id_\n" +
        "left join att_attachment$video flv on flv.fk_stg_file$video_flv = f.id_\n" +
        "left join att_attachment$video mp on mp.fk_stg_file$video_mp4 = f.id_\n" +
        "left join att_attachment$video prev on prev.fk_stg_file$image = f.id_\n" +
        "where \n" +
        "f.is_temp_ = false and \n" +
        "doc.fk_stg_file$file is null and\n" +
        "au.fk_stg_file$audio is null and\n" +
        "file.fk_stg_file  is null and\n" +
        "cp.fk_stg_file$archive is null and\n" +
        "im.fk_stg_file$image is null and\n" +
        "imt.fk_stg_file$image$thumbnail is null and\n" +
        "flv.fk_stg_file$video_flv is null and\n" +
        "mp.fk_stg_file$video_mp4 is null and\n" +
        "prev.fk_stg_file$image is null");
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Long> findAbstractStorageFileIdList() {
    Query query = createSqlQuery("" +
        "select f.id_ from stg_file f\n" +
        "left join stg_file$archive arch on arch.id_ = f.id_\n" +
        "left join stg_file$audio au on au.id_ = f.id_\n" +
        "left join stg_file$file file on file.id_ = f.id_\n" +
        "left join stg_file$video vid on vid.id_ = f.id_\n" +
        "left join stg_file$image im on im.id_ = f.id_\n" +
        "where \n" +
        "arch.id_ is null and\n" +
        "au.id_ is null and\n" +
        "file.id_  is null and\n" +
        "vid.id_ is null and\n" +
        "im.id_ is null");
    return query.getResultList();
  }
}
