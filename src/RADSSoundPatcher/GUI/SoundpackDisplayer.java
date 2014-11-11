package RADSSoundPatcher.GUI;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class SoundpackDisplayer extends JPanel {
	
	public SoundpackDisplayer() {
		super();		
	}
	
	public List<SoundpackPreview> getAllSelectedSoundpacks()
	{
		List<SoundpackPreview> list = new ArrayList<SoundpackPreview>();
		Component[] array = this.getComponents();
		for(Component comp : array)
		{
			if(comp instanceof SoundpackPreview && ((SoundpackPreview) comp).isSelected())
				list.add((SoundpackPreview) comp);				
		}
		return list;
	}

}
