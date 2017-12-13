/*
 * Copyright (c) 2008, Itision Corporation. All Rights Reserved.
 *
 * The content of this file is copyrighted by Itision Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package by.vbalanse.model.common;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract baseclass for all unmanaged entities which using database generated identifier
 *
 * @author <a href="mailto: e.terehov@itision.com">Eugene Terehov</a>
 */
@MappedSuperclass
public abstract class AbstractUnmanagedEntity<ID extends Serializable> extends AbstractEntity<ID> {

  @Id
  @Column(name = COLUMN_ID)
  private ID id;

  protected AbstractUnmanagedEntity() {
  }

  protected AbstractUnmanagedEntity(ID id) {
    this.id = id;
  }

  /**
   * The id attribute used to uniquely identify the instance of the subclass
   *
   * @return the unique id (primary key) of the instance
   */
  public ID getId() {
    return this.id;
  }

  public void setId(ID id) {
    this.id = id;
  }

}