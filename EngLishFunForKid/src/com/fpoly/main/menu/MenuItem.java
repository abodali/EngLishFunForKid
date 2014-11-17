package com.fpoly.main.menu;

public class MenuItem {
	private final int MENU_TYPE_HEADER = 0;
	private final int MENU_TYPE_ITEM = 1;
	private int MENU_TYPE = MENU_TYPE_ITEM;

	private String title;
	private int icon;

	public MenuItem(String header) {
		this.title = header;
		MENU_TYPE = MENU_TYPE_HEADER;

	}

	public MenuItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
		MENU_TYPE = MENU_TYPE_ITEM;

	}

	public int getMENU_TYPE() {
		return MENU_TYPE;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
