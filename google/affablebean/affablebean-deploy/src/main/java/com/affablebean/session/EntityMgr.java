package com.affablebean.session;

import com.google.appengine.api.utils.SystemProperty;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author osman
 */
public enum EntityMgr {

	EM;
	final EntityManagerFactory emf;

	private EntityMgr() {
		Map<String, String> properties = new HashMap<>();

		if (SystemProperty.environment.value()
						== SystemProperty.Environment.Value.Production) {

			properties.put("javax.persistence.jdbc.driver",
							"com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.url",
							"jdbc:google:mysql://apt-perception-499:affablebean/affablebean");

		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Cannot find the driver in the classpath!", e);
			}

			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
			properties.put("javax.persistence.jdbc.url",
							"jdbc:mysql://localhost:3306/affablebean");
		}

		properties.put("javax.persistence.jdbc.user", "root");
		properties.put("javax.persistence.jdbc.password", "");

		emf = Persistence.createEntityManagerFactory("AffableBeanPU", properties);
	}

	public EntityManager create() {
		return emf.createEntityManager();
	}

	public void close() {
		if (emf.isOpen()) {
			emf.close();
		}
	}
}
