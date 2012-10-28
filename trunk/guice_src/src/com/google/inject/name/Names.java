/**
 * Copyright (C) 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.name;

import com.google.inject.Binder;
import com.google.inject.Key;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * 处理properties文件，例如：
 * 
 * 
Listing 3-18. Loading and Using db.properties 。
public class PropertiesModule extends AbstractModule { 
    protected void configure() { 
        try { 
            Properties databaseProperties = loadProperties("db.properties"); 
            Names.bindProperties(binder(), databaseProperties); 
        } catch (RuntimeException e) { 
            addError("Could not configure database properties", e); 
        } 
    } 
     
    private static Properties loadProperties(String name) { 
        Properties properties = new Properties(); 
        InputStream is = new Object(){} 
                            .getClass() 
                            .getEnclosingClass() 
                            .getResourceAsStream(name); 
        try { 
            properties.load(is); 
        } catch(IOException e) { 
            throw new RuntimeException(e); 
        } finally { 
            if (is != null) { 
                try { 
                    is.close(); 
                } catch (IOException dontCare) {} 
            } 
        } 
        return properties; 
    } 
} 
 
public class PropertiesExample { 
    @Inject 
    public void databaseURL(@Named("db.url") String url) { 
        System.out.println(url); 
    } 
     
    public static void main(String[] args) { 
        Injector i = Guice.createInjector(new PropertiesModule()); 
        i.getInstance(PropertiesExample.class); 
    } 
} 
Running this example prints jdbc:mysql://localhost/test . 


 * <p>
 * Utility methods for use with {@code @}{@link Named}.
 *
 * @author crazybob@google.com (Bob Lee)
 */
public class Names {

  private Names() {}

  /**
   * Creates a {@link Named} annotation with {@code name} as the value.
   */
  public static Named named(String name) {
    return new NamedImpl(name);
  }

  /**
   * Creates a constant binding to {@code @Named(key)} for each entry in
   * {@code properties}.
   */
  public static void bindProperties(Binder binder, Map<String, String> properties) {
    binder = binder.skipSources(Names.class);
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      binder.bind(Key.get(String.class, new NamedImpl(key))).toInstance(value);
    }
  }

  /**
   * Creates a constant binding to {@code @Named(key)} for each property. This
   * method binds all properties including those inherited from 
   * {@link Properties#defaults defaults}.
   */
  public static void bindProperties(Binder binder, Properties properties) {
    binder = binder.skipSources(Names.class);

    // use enumeration to include the default properties
    for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
      String propertyName = (String) e.nextElement();
      String value = properties.getProperty(propertyName);
      binder.bind(Key.get(String.class, new NamedImpl(propertyName))).toInstance(value);
    }
  }
}
