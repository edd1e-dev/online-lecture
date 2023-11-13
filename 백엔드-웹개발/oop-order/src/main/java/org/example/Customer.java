package org.example;

public class Customer {

	public Cook order(String menuName, Menu menu, Chef chef) {
		MenuItem menuItem = menu.choose(menuName);
		return chef.makeCook(menuItem);
	}
}
