package components.popups;

import com.google.inject.Inject;
import commons.AbsCommon;
import support.GuiceScoped;

public class AuthPopup extends AbsCommon implements IPopup<AuthPopup> {

    @Inject
    public AuthPopup(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @Override
    public AuthPopup popupShouldBeVisible() {
        System.out.println("Popup is visible.");
        return this;
    }

    @Override
    public AuthPopup popupShouldNotBeVisible() {
        System.out.println("Popup isn't visible.");
        return this;
    }

    @Override
    public AuthPopup closePopup() {
        System.out.println("Popup is close.");
        return this;
    }
}