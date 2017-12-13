package by.vbalanse.cleanup;

import java.sql.Driver;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by v_terehova on 7/26/2015.
 */
public class CleanupContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		Logger logger = Logger.getLogger("CleanupContextListener");
		logger.warn("context destroyed in out CleanupContextListener");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			ClassLoader driverclassLoader = driver.getClass().getClassLoader();
			ClassLoader thisClassLoader = this.getClass().getClassLoader();
			if (driverclassLoader != null && thisClassLoader != null && driverclassLoader.equals(thisClassLoader)) {
				try {
					logger.warn("Deregistering: " + driver);
					DriverManager.deregisterDriver(driver);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
	}
}