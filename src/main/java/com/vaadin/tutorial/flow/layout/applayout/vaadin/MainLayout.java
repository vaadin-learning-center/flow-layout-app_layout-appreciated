package com.vaadin.tutorial.flow.layout.applayout.vaadin;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;
import static com.vaadin.flow.component.icon.VaadinIcon.SIGN_OUT;
import static com.vaadin.flow.component.icon.VaadinIcon.TRENDING_UP;
import static com.vaadin.flow.component.icon.VaadinIcon.USER;

import com.github.appreciated.app.layout.component.applayout.AppLayout;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.services.SecurityService;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.DashboardView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.ProfileView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.TrendsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
public class MainLayout extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {

  public static final  String ITM_DASHBOARD = "mainview.menue.item.dashboard";
  public static final  String ITM_PROFILE   = "mainview.menue.item.profile";
  public static final  String ITM_TRENDS    = "mainview.menue.item.trends";
  public static final  String ITM_LOGOUT    = "mainview.menue.item.logout";
  public static final  String TITLE         = "mainview.app.title";
  private static final String LOGO_PNG      = "logo.png";

  public MainLayout() {
    Image img = new Image(new StreamResource(LOGO_PNG, () -> MainLayout.class.getResourceAsStream("/" + LOGO_PNG)),
                          "Vaadin Logo"
    );

    //app layout specific
    img.setHeight("var(--app-layout-menu-button-height)");

    final LeftLayouts.LeftResponsiveHybrid appLayout = AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                                                                   .withTitle(getTranslation(TITLE))
                                                                   .withIconComponent(img)
                                                                   .withAppMenu(appMenu())
                                                                   .build();

    init(appLayout);
  }


  private Component appMenu() {
    return LeftAppMenuBuilder
        .get()
        .add(getTranslation(ITM_DASHBOARD), DASHBOARD.create(), DashboardView.class)
        .add(getTranslation(ITM_PROFILE), USER.create(), ProfileView.class)
        .add(getTranslation(ITM_TRENDS), TRENDING_UP.create(), TrendsView.class)
        .add(new LeftClickableItem(getTranslation(ITM_LOGOUT), SIGN_OUT.create(), e -> {
          UI            ui      = UI.getCurrent();
          VaadinSession session = ui.getSession();
          session.setAttribute(SecurityService.User.class, null);
          session.close();
          ui.navigate(MainView.class);
        }))
        .build();
  }
}
