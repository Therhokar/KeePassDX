/*
 * Copyright 2017 Brian Pellin, Jeremy Jamet / Kunzisoft.
 *     
 * This file is part of KeePass DX.
 *
 *  KeePass DX is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  KeePass DX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePass DX.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.keepassdroid.view;


import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunzisoft.keepass.R;
import com.keepassdroid.GroupActivity;
import com.keepassdroid.GroupBaseActivity;
import com.keepassdroid.app.App;
import com.keepassdroid.database.PwGroup;
import com.keepassdroid.database.PwGroupV3;
import com.keepassdroid.settings.PrefsUtil;


public class PwGroupView extends ClickView {
	
	protected PwGroup mPw;
	protected GroupBaseActivity mAct;
	protected TextView mTv;

	protected static final int MENU_OPEN = Menu.FIRST;
	
	public static PwGroupView getInstance(GroupBaseActivity act, PwGroup pw) {
		if ( pw instanceof PwGroupV3 ) {
			return new PwGroupViewV3(act, pw);
		} else {
			return new PwGroupView(act, pw);
		}
	}
	
	protected PwGroupView(GroupBaseActivity act, PwGroup pw) {
		super(act);
		mAct = act;
		
		View gv = View.inflate(act, R.layout.list_entries_group, null);
		
		mTv = (TextView) gv.findViewById(R.id.group_text);
		float size = PrefsUtil.getListTextSize(act); 
		mTv.setTextSize(size);
		
		TextView label = (TextView) gv.findViewById(R.id.group_label);
		label.setTextSize(size-8);
		
		populateView(gv, pw);
		
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		addView(gv, lp);
	}
	
	private void populateView(View gv, PwGroup pw) {
		mPw = pw;
		
		ImageView iv = (ImageView) gv.findViewById(R.id.group_icon);
		App.getDB().drawFactory.assignDrawableTo(iv, getResources(), pw.getIcon());
		
		mTv.setText(pw.getName());
	}
	
	public void convertView(PwGroup pw) {
		populateView(this, pw);
	}

	public void onClick() {
		launchGroup();
	}
	
	private void launchGroup() {
		GroupActivity.Launch(mAct, mPw);
	}

	@Override
	public void onCreateMenu(ContextMenu menu, ContextMenuInfo menuInfo) {
		menu.add(0, MENU_OPEN, 0, R.string.menu_open);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			
		case MENU_OPEN:
			launchGroup();
			return true;
		
		default:
			return false;
		}
	}

}