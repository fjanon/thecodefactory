@java -Xmx256M -cp "%~dp0\src;%~dp0\bin;C:/GWT/gwt-user.jar;C:/GWT/gwt-dev-windows.jar;C:/CodeFactory/PickerPanel.jar" com.google.gwt.dev.GWTCompiler -out "%~dp0\www" %* com.foo.pickerdemo.PickerDemo