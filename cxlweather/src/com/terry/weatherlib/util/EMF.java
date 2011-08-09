package com.terry.weatherlib.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create：Apr 8, 2009 11:20:36 PM
 */
public final class EMF {
	private static final EntityManagerFactory emfInstance =
        Persistence.createEntityManagerFactory("transactions-optional");

    private EMF() {}

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}
