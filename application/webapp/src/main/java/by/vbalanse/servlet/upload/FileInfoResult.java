package by.vbalanse.servlet.upload;

import by.vbalanse.rest.JsonResult;

import java.util.List;

/**
 * Created by Vasilina on 27.03.2015.
 */
public class FileInfoResult extends JsonResult {
  private List<FileInfo> files;

  public FileInfoResult(List<FileInfo> files) {
    this.files = files;
  }

  public List<FileInfo> getFiles() {
    return files;
  }

  public void setFiles(List<FileInfo> files) {
    this.files = files;
  }
}
