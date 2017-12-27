package by.vbalanse.vaadin.hibernate.utils;

import by.vbalanse.vaadin.AdminUI;
import net.sf.ehcache.CacheManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class LazyJpaServletFilter implements Filter {

    private EntityManagerFactory entityManagerFactory;

    public void init(FilterConfig filterConfig) throws ServletException {
        CacheManager.getInstance().shutdown();
        entityManagerFactory = Persistence
                .createEntityManagerFactory(AdminUI.PERSISTENCE_UNIT);
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            // Create and set the entity manager
            LazyJpaEntityManagerProvider.setCurrentEntityManager(entityManagerFactory.createEntityManager());

            // Handle the request
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // Reset the entity manager
           LazyJpaEntityManagerProvider.setCurrentEntityManager(null);
        }
    }

    public void destroy() {
        entityManagerFactory = null;
    }
}