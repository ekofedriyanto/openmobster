/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.mobileCloud.test.api;

import org.openmobster.core.mobileCloud.api.model.MobileBean;
import test.openmobster.core.mobileCloud.rimos.testsuite.AbstractAPITest;

/**
 * @author openmobster@gmail.com
 *
 */
public final class TestBeanUpdate extends AbstractAPITest 
{
	public void runTest()
	{		
		try
		{
			this.startBootSync();
			this.waitForBeans();
			
			MobileBean[] beans = MobileBean.readAll(this.service);
			for(int i=0; i<beans.length; i++)
			{
				MobileBean curr = beans[i];
				
				assertEquals(curr.getService(), this.service, this.getInfo()+"://Service does not match");
				
				String id = curr.getId();
				assertTrue(id.equals("unique-1") || id.equals("unique-2"), this.getInfo()+"://Id Does not match");
				
				assertEquals(curr.getValue("from"), "from@gmail.com", this.getInfo()+"://From does not match");
				assertEquals(curr.getValue("to"), "to@gmail.com", this.getInfo()+"://To does not match");
				assertEquals(curr.getValue("subject"), "This is the subject<html><body>"+id+"</body></html>", this.getInfo()+"://Subject does not match");
				assertEquals(curr.getValue("message"), 
				"<tag apos='apos' quote=\"quote\" ampersand='&'>"+id+"/Message"+"</tag>",
				this.getInfo()+"://Message does not match");
				
				curr.setValue("from", "from/Updated");
				assertEquals(curr.getValue("from"), "from/Updated", this.getInfo()+"://From does not match");
				
				MobileBean stored = MobileBean.readById(this.service, id);
				assertEquals(stored.getValue("from"), "from@gmail.com", this.getInfo()+"://Should_Not_Be_Updated_Yet");
				
				curr.save();
				stored = MobileBean.readById(this.service, id);
				assertEquals(stored.getValue("from"), "from/Updated", this.getInfo()+"://Should_Not_Be_Updated_Now");
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}
	}	
}
