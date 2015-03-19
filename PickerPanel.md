How to use the PickerPanel and the ColorPicker derived from it.

# Introduction #

The PickerPanel is a generic picker that works with a PickerCellManager and IPickerCells. The PickerPanel can by subclassed to implement specialized instances, like the ColorPicker shown as an example.

The PickerDemo project shows how to use the different implementations of the ColorPicker. Use it if you simply want to use the ColorPicker in one of your projects.

The ColorPickerTest project contains the full source of the PickerPanel components and the ColorPicker implementations. Check this project out if you need to implement your own picker or want to change the behavior of the existing ones.

There is no Javascript involved, everything is in Java in the GWT environment. Therefore the code should work in all the browsers supported by GWT 1.5.

# Details #

Screen shots of PickerPanel used as a ColorPicker

  * Square 4 rows x 4 columns colors
![http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Square-4x4.jpg](http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Square-4x4.jpg)

  * Horizontal 1 row x 16 cols
![http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Horizontal-1x16.jpg](http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Horizontal-1x16.jpg)

  * Vertical-16x1.jpg Vertical 16 rows x 1 col
![http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Vertical-16x1.jpg](http://thecodefactory.googlecode.com/svn/trunk/PickerDemo/ColorPicker-Vertical-16x1.jpg)