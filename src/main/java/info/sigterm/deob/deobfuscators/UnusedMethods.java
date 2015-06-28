package info.sigterm.deob.deobfuscators;

import info.sigterm.deob.ClassFile;
import info.sigterm.deob.ClassGroup;
import info.sigterm.deob.Method;
import info.sigterm.deob.execution.Execution;

import java.util.ArrayList;

public class UnusedMethods
{
	public void run(ClassGroup group)
	{
		Execution execution = new Execution(group);
		execution.populateInitialMethods();
		execution.run();		
		
		int i = 0;
		for (ClassFile cf : group.getClasses())
		{
			for (Method m : new ArrayList<>(cf.getMethods().getMethods()))
			{
				/* assume obfuscated names are <= 2 chars */
				if (m.getName().length() > 2)
					continue;
				
				if (!execution.methods.contains(m))
				//if (!m.isUsed())
				{
					cf.getMethods().removeMethod(m);
					++i;
				}
			}
		}
		
		System.out.println("Removed " + i + " methods");
	}
}
