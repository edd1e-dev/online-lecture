package org.example;

public class Chef {
	public Cook makeCook(MenuItem menuItem) {
		return new Cook(menuItem);
	}
}
