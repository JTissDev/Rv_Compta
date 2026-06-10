package com.jtissdev.view;

import com.jtissdev.features.core.SelectionContext;

/**
 * Represents a NavigationContextView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class NavigationContextView {

	public void displayBreadcrumb(SelectionContext context) {
		if (context.getType() == null) return;

		StringBuilder sb = new StringBuilder();
		sb.append(ViewUtil.CYAN + "📍 PARCOURS : " + ViewUtil.RESET);
		sb.append(context.getType().getName());

		if (context.getSubType() != null) {
			sb.append(" > ").append(context.getSubType().getName());
		}

		if (context.getDetail() != null) {
			sb.append(" > ").append(ViewUtil.YELLOW).append(context.getDetail().getName()).append(ViewUtil.RESET);
		}

		System.out.println("\n" + "=".repeat(80));
		System.out.println(sb.toString());
		System.out.println("=".repeat(80) + "\n");
	}

}
