
package com.rayzr522.nodrop;

import java.util.ArrayList;
import java.util.List;

public class Config extends Configuration {

	public List<String>	worlds			= new ArrayList<String>();
	public boolean		PREVENT_CLICK	= true;
	public boolean		PREVENT_DROP	= true;
	public boolean		PREVENT_DEATH	= true;
	public String		PERM_MANAGE		= "NoDrop.manage";

}
