package com.codefactory.gwt.client;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class ColorPickerCellManager extends PickerCellManager
{
protected Widget feedbackWidget;
ColorPickerColor[] colors;

// Build a color picker with the same rows and cols as the array passed in
public ColorPickerCellManager(Widget feedbackWidget, ColorPickerColor[] colors)
	{
	 this(feedbackWidget, colors, colors.length, 1);
	}

// This constructor transforms a ColorPickerColor array into a ColorPickerCell array with the given rows and cols
public ColorPickerCellManager(Widget feedbackWidget, ColorPickerColor[] colors, int pickerRows, int pickerCols)
	{
	 if (colors.length != (pickerRows * pickerCols))
		 {
		  GWT.log("ColorPickerCellManager incorrect length!", null);
		  return;
		 }
	 this.feedbackWidget = feedbackWidget;	// Widget to set the background color when a color is selected

	 this.colors = colors;
	 super.setCells(pickerRows, pickerCols); // will call getObjectForCell(int row, int col)

	}

// This constructor takes a ColorPickerColor collection instead of an array
public ColorPickerCellManager(Widget feedbackWidget, Collection<ColorPickerColor> colorCollection, int pickerRows, int pickerCols)
	{
	 if (colorCollection.size() != (pickerRows * pickerCols))
		 {
		  GWT.log("ColorPickerCellManager incorrect collection length!", null);
		  return;
		 }
	 this.feedbackWidget = feedbackWidget;	// Widget to set the background color when a color is selected
	 this.colors = new ColorPickerColor[pickerRows * pickerCols];
	 this.colors = colorCollection.toArray(colors);
	 super.setCells(pickerRows, pickerCols); // will call getObjectForCell(int row, int col)
	}

// Called by PickerCellManager
@Override
protected IPickerCell getObjectForCell(int row, int col)
	{
	 return new ColorPickerCell(colors[(row * cols) + col]);
	}

// Used to select a cell at init time
@Override
public void selectCell(int row, int col)
	{
	 super.selectCell(row, col);
	 ColorPickerColor color = ((ColorPickerCell)cells[row][col]).getColor();
//	 GWT.log("ColorPickerCellManager selectCell color: " + color.RGBvalue, null);
	 feedbackWidget.getElement().getStyle().setProperty("backgroundColor", color.RGBvalue);
	}
} // class ColorPickerCellManager
