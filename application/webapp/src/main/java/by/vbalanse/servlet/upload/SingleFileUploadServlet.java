/*
 * This software is the exclusive and sole property of Itision.
 * Itision has the sole rights to copy the software, create derivatives or modified versions of it,
 * distribute copies to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights
 * which also includes indirect copying  such as unauthorized translation of the code into a different
 * programming language without written explicit permission from Itision is an infringer and subject
 * to liability for damages or statutory fines. Interested parties may contact e.terehov@itision.com.
 *
 * (c) 2012 Itision
 */

package by.vbalanse.servlet.upload;

import by.vbalanse.facade.storage.FileWithPath;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class SingleFileUploadServlet<T extends FileWithPath> extends HttpServlet implements Controller, ServletContextAware {

  protected static final Logger log = Logger.getLogger(SingleFileUploadServlet.class);

  /**
   * Servlet context
   *
   * @see javax.servlet.ServletContext
   */
  private ServletContext servletContext;

  /**
   * ${@inheritDoc}
   */
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  /**
   * Getting servlet context either from the {@link javax.servlet.http.HttpServlet#getServletContext() httpServlet}
   * or from the injected by Spring framework {@link by.vbalanse.servlet.upload.SingleFileUploadServlet#servletContext property}
   *
   * @return <code>ServletContext</code> object passed to this servlet
   */
  @Override
  public ServletContext getServletContext() {
    ServletConfig servletConfig = super.getServletConfig();

    if (servletConfig == null) {
      return servletContext;
    }

    return servletConfig.getServletContext();
  }

  protected abstract T generateFileNameWithPath(String fileExtension) throws IOException;

  protected abstract String prepareSuccessResponseMessage(T fileWithPath);

  protected abstract String prepareFailuerResponseMessage(Exception exception);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain;charset=UTF-8");

    PrintWriter out = response.getWriter();

    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (!isMultipart) {
      throw new ServletException("Servlet support only multipart data,");
    }

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    FileOutputStream fos = null;
    try {
      @SuppressWarnings("unchecked")
      List<FileItem> items = upload.parseRequest(request);
      if (items.size() != 1) {
        throw new ServletException("Servlet can upload only single file.");
      }

      FileItem file = items.get(0);

      // todo: implement validation, and other stuff
      if (file == null) {
        throw new ServletException("No data.");
      }

      T fileWithPath = generateFileNameWithPath(file.getName());
      String fileWithFullPath = FilenameUtils.concat(fileWithPath.getFilePath(), fileWithPath.getFileName());
      fos = new FileOutputStream(fileWithFullPath);
      IOUtils.copy(file.getInputStream(), fos);

      out.print(ResponseCodes.RESPONSE_OK + ResponseCodes.DEIMITER + prepareSuccessResponseMessage(fileWithPath));
    } catch (Exception e) {
      log.error("Failed to upload the file.", e);

      out.print(ResponseCodes.RESPONSE_FAILURE + ResponseCodes.DEIMITER + prepareFailuerResponseMessage(e));
    } finally {
      IOUtils.closeQuietly(fos);
    }
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // delegating to the default service method of the HttpServletRequest
    service(request, response);

    // returns null, because all the staff is done in the service method of the HttpServlet
    return null;
  }

}
