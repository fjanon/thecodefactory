package com.codefactory.gwt.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ColorPickerTest implements EntryPoint {

  /**
   * This is the entry point method.
   */
public void onModuleLoad()
	{
	 ColorPickerColor[] colorArray = createColorArray();
	 Collection<ColorPickerColor> colorCollection = createColorCollection();
	 RootPanel rootpanel = RootPanel.get();
	 Panel mainPanel = new VerticalPanel();
	 rootpanel.add(mainPanel);

	 InlineLabel label = new InlineLabel("InlineLabel with a 4 x 4 color picker...");
	 mainPanel.add(label);
	 PickerCellManager cellManager = new ColorPickerCellManager(label, colorArray, 4, 4);	// 4 x 4 color picker
	 PickerPanel picker = new PickerPanel(label, cellManager);

	 InlineLabel label1 = new InlineLabel("InlineLabel with vertical 16 x 1 color picker with autohide when clicked...");
	 mainPanel.add(label1);

	 PickerCellManager cellManager1 = new ColorPickerCellManager(label1, colorArray); // 1 column color picker
	 cellManager1.selectCell(3, 0);
	 PickerPanel picker1 = new PickerPanel(label1, cellManager1);
	 picker1.setHideOnClick(true);

	 InlineLabel label2 = new InlineLabel("InlineLabel with horizontal 1 x 16 color picker...");
	 mainPanel.add(label2);
	 PickerCellManager cellManager2 = new ColorPickerCellManagerWithInput(label2, colorCollection, 1, 16);
	 PickerPanel picker2 = new PickerPanel(label2, cellManager2);
	 picker2.setAnchorCorner(PickerPanel.SOUTH);
	}

private ColorPickerColor[] createColorArray()
	{
	 ColorPickerColor[] colors = new ColorPickerColor[]
	   {
		// first field is use to set the cell background color, the second one is the caption/tooltip
		// the last one is use to set the value of the hidden field if any
		new ColorPickerColor("#FF0000", "Red", "FF0000"),
		new ColorPickerColor("#FFC0CB", "Pink", "FFC0CB"),
		new ColorPickerColor("#FFA500", "Orange", "FFA500"),
		new ColorPickerColor("#FFFF00", "Yellow", "FFFF00"),
		new ColorPickerColor("#FF00FF", "Magenta", "FF00FF"),
		new ColorPickerColor("#800080", "Purple", "800080"),
		new ColorPickerColor("#00FF00", "Lime", "00FF00"),
		new ColorPickerColor("#008000", "Green", "008000"),
		new ColorPickerColor("#B0C4DE", "Light Steel Blue", "B0C4DE"),
		new ColorPickerColor("#0000FF", "Blue", "0000FF"),
		new ColorPickerColor("#DAA520", "Goldenrod", "DAA520"),
		new ColorPickerColor("#D2691E", "Chocolate", "D2691E"),
		new ColorPickerColor("#FFFFFF", "White", "FFFFFF"),
		new ColorPickerColor("#D3D3D3", "Light Gray", "D3D3D3"),
		new ColorPickerColor("#C0C0C0", "Silver", "C0C0C0"),
		new ColorPickerColor("#000000", "Black","000000")
	   };
	return colors;
	}

private Collection<ColorPickerColor> createColorCollection()
	{
	 Collection<ColorPickerColor> colors = new ArrayList<ColorPickerColor>();

	 // first field is use to set the cell background color, the second one is the caption/tooltip
	 // the last one is use to set the value of the hidden field if any
	 colors.add(new ColorPickerColor("#FF0000", "Red", "FF0000"));
	 colors.add(new ColorPickerColor("#FFC0CB", "Pink", "FFC0CB"));
	 colors.add(new ColorPickerColor("#FFA500", "Orange", "FFA500"));
	 colors.add(new ColorPickerColor("#FFFF00", "Yellow", "FFFF00"));
	 colors.add(new ColorPickerColor("#FF00FF", "Magenta", "FF00FF"));
	 colors.add(new ColorPickerColor("#800080", "Purple", "800080"));
	 colors.add(new ColorPickerColor("#00FF00", "Lime", "00FF00"));
	 colors.add(new ColorPickerColor("#008000", "Green", "008000"));
	 colors.add(new ColorPickerColor("#B0C4DE", "Light Steel Blue", "B0C4DE"));
	 colors.add(new ColorPickerColor("#0000FF", "Blue", "0000FF"));
	 colors.add(new ColorPickerColor("#DAA520", "Goldenrod", "DAA520"));
	 colors.add(new ColorPickerColor("#D2691E", "Chocolate", "D2691E"));
	 colors.add(new ColorPickerColor("#FFFFFF", "White", "FFFFFF"));
	 colors.add(new ColorPickerColor("#D3D3D3", "Light Gray", "D3D3D3"));
	 colors.add(new ColorPickerColor("#C0C0C0", "Silver", "C0C0C0"));
	 colors.add(new ColorPickerColor("#000000", "Black","000000"));
	 return colors;
	}
}
