package com.codefactory.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ColorPickerCell extends Label implements IPickerCell
{
ColorPickerColor color;

public ColorPickerCell(ColorPickerColor color)
	{
	 super();
//	 GWT.log("ColorPickerCell", null);
	 this.color = color;
	 this.getElement().getStyle().setProperty("backgroundColor", color.getRGBvalue());
	 if (color.getTitle() != null) this.setTitle(color.getTitle());
	}

public ColorPickerColor getColor() { return this.color; }

public void deselect()
	{
	 GWT.log("ColorPickerCell deselect: " + color.getTitle(), null);
//	 this.removeStyleName(PICKERSELECTEDSTYLE);		// picker does it
	}

public void select()
	{
	 GWT.log("ColorPickerCell select: " + color.getTitle(), null);
//	 this.addStyleName(PICKERSELECTEDSTYLE);
	}

public Widget getWidget()
	{
//	 GWT.log("ColorPickerCell getWidget: " + this, null);
	 return this;
	}
} // class ColorPickerCell