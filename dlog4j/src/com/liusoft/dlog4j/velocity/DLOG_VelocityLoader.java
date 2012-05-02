/*
 *  DLOG_VelocityLoader.java
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 *  Author: Winter Lau (javayou@gmail.com)
 *  http://dlog4j.sourceforge.net
 */
package com.liusoft.dlog4j.velocity;

import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.tools.view.servlet.WebappLoader;

/**
 * 自定义Velocity模板加载器
 * @author liudong
 * @email javayou@gmail.com
 */
public class DLOG_VelocityLoader extends WebappLoader {
	
	/**
     * Defaults to return false.
     */
    public boolean isSourceModified(Resource resource)
    {
        return false;
    }

    /**
     * Defaults to return 0
     */
    public long getLastModified(Resource resource)
    {
    	/*
    	if(servletContext!=null){
	    	String vmPath = servletContext.getRealPath(resource.getName());
	    	return new File(vmPath).lastModified();
    	}*/
    	return 0;
    }
    
}
