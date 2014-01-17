package net.bible.android.view.activity.page.actionbar;

import net.bible.android.activity.R;
import net.bible.android.control.ControlFactory;
import net.bible.android.control.PassageChangeMediator;
import net.bible.android.control.document.DocumentControl;
import net.bible.service.common.CommonUtils;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

/** 
 * Toggle Strongs numbers on/off
 */
public class StrongsActionBarButton extends QuickActionButton {

	private DocumentControl documentControl = ControlFactory.getInstance().getDocumentControl();
	
	public StrongsActionBarButton() {
		// ALWAYS is overriden by setVisible which depends on canShow() below
		// because when visible this button is ALWAYS on the Actionbar
		super(MenuItemCompat.SHOW_AS_ACTION_ALWAYS|MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
	}
	
	@Override
	public void addToMenu(Menu menu) {
		super.addToMenu(menu);
	}

	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		// update the show-strongs pref setting according to the ToggleButton
		CommonUtils.getSharedPreferences().edit().putBoolean("show_strongs_pref", !isStrongsVisible()).commit();
		// redisplay the current page; this will also trigger update of all menu items
		PassageChangeMediator.getInstance().forcePageUpdate();
		
		return true;
	}

	private boolean isStrongsVisible() {
		return CommonUtils.getSharedPreferences().getBoolean("show_strongs_pref", false);
	}

	@Override
	protected String getTitle() {
		return CommonUtils.getResourceString(isStrongsVisible() ? R.string.strongs_toggle_button_on : R.string.strongs_toggle_button_off);
	}

	/** return true if Strongs are relevant to this doc & screen */
	@Override
	protected boolean canShow() {
		return //isEnoughRoomInToolbar() && 
				documentControl.isStrongsInBook();
	}
}
