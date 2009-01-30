package com.codefactory.gwt.client;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ColorPickerCellManagerWithInput extends ColorPickerCellManager {
// Very annoying that Hidden and TextBox don't have the same superclass or interface: getName, getValue, setValue
public final String HIDDENNAME = "selectedcolor";	// default name for the Hidden widget
Hidden hidden;	// Used if we create the input
TextBox textBox;	// Used if the client creates it with an existing textBox)

// Create color picker with a Hidden widget with a default name of "selectedcolor" and whose value is set to the color id
// The colors are supplied in an array
public ColorPickerCellManagerWithInput(Widget feedbackWidget, ColorPickerColor[] colors, int pickerRows, int pickerCols)
	{
	 super(feedbackWidget, colors, pickerRows, pickerCols);
	 hidden = new Hidden(HIDDENNAME);
	 // Attach it to the panel where the feedbackWidget is
	 addWidgetToParent(feedbackWidget, hidden);
	}

// Create color picker with a Hidden widget with a default name of "selectedcolor" and whose value is set to the color id
// The colors are supplied in a collection
public ColorPickerCellManagerWithInput(Widget feedbackWidget, Collection<ColorPickerColor> colors, int pickerRows, int pickerCols)
	{
	 super(feedbackWidget, colors, pickerRows, pickerCols);
	 hidden = new Hidden(HIDDENNAME);
	 // Attach it to the panel where the feedbackWidget is
	 addWidgetToParent(feedbackWidget, hidden);
	}

// Create color picker associated with a TextBox whose value is set to the color id when a color is selected by the user
// The colors are supplied in an array
public ColorPickerCellManagerWithInput(Widget feedbackWidget, ColorPickerColor[] colors, int pickerRows, int pickerCols, TextBox textBox)
	{
	 super(feedbackWidget, colors, pickerRows, pickerCols);
	 this.textBox = textBox;
	}

// Create color picker associated with a TextBox whose value is set to the color id when a color is selected by the user
// The colors are supplied in a collection
public ColorPickerCellManagerWithInput(Widget feedbackWidget, Collection<ColorPickerColor> colors, int pickerRows, int pickerCols, TextBox textBox)
	{
	 super(feedbackWidget, colors, pickerRows, pickerCols);
	 this.textBox = textBox;
	}

// Called by the PickerCellManager to build the internal cellArray
@Override
public void selectCell(int row, int col)
	{
	 super.selectCell(row, col);
	 GWT.log("ColorPickerCellManagerWithInput selectCell: " + row + " " + col, null);
	 ColorPickerCell cell = (ColorPickerCell)super.getSelectedCell();
	 if (cell != null)
		 {
		  ColorPickerColor color = cell.getColor();
		  String newValue = color.getId();
		  GWT.log("ColorPickerCellManager selectCell id: " + newValue, null);
		  if (textBox != null) textBox.setText(newValue);
		  if (hidden != null) hidden.setValue(newValue);
		 }
	}

// Add our Hidden to the parent panel of the feedbackWidget
protected void addWidgetToParent(Widget widget, Widget widgetToAdd)
	{
	 Widget parent = widget.getParent();
	 while (parent != null)
		{
		 if (parent instanceof Panel)
			 {
			  ((Panel)parent).add(widgetToAdd);
			  break;
			 }
		 parent.getParent();
		}
	}
} // class ColorPickerCellManagerWithInput
