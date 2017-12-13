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

package by.vbalanse.facade.storage.attachment;

import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity;

import java.util.ArrayList;
import java.util.List;

public class StorageEntitiesWrapper {
    List<AbstractStorageFileEntity> storageFiles = new ArrayList<AbstractStorageFileEntity>();
    List<StorageSubfolderEntity> storageSubfolders = new ArrayList<StorageSubfolderEntity>();

    public void addAll(StorageEntitiesWrapper storageEntitiesWrapper) {
        addStorageFiles(storageEntitiesWrapper.getStorageFiles());
        addStorageSubfolders(storageEntitiesWrapper.getStorageSubfolders());
    }

    public void addStorageFiles(List<AbstractStorageFileEntity> storageFiles) {
        this.storageFiles.addAll(storageFiles);
    }

    public void addStorageSubfolders(List<StorageSubfolderEntity> storageSubfolders) {
        this.storageSubfolders.addAll(storageSubfolders);
    }

    public void addStorageFile(AbstractStorageFileEntity storageFile) {
        this.storageFiles.add(storageFile);
    }

    public void addStorageSubfolder(StorageSubfolderEntity storageSubfolder) {
        this.storageSubfolders.add(storageSubfolder);
    }


    public List<AbstractStorageFileEntity> getStorageFiles() {
        return storageFiles;
    }

    public void setStorageFiles(List<AbstractStorageFileEntity> storageFiles) {
        this.storageFiles = storageFiles;
    }

    public List<StorageSubfolderEntity> getStorageSubfolders() {
        return storageSubfolders;
    }

    public void setStorageSubfolders(List<StorageSubfolderEntity> storageSubfolders) {
        this.storageSubfolders = storageSubfolders;
    }
}
