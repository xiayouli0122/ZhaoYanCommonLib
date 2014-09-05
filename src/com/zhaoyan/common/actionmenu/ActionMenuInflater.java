package com.zhaoyan.common.actionmenu;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import com.zhaoyan.common.actionmenu.ActionMenu.ActionMenuItem;
import com.zhaoyan.common.lib.R;
import com.zhaoyan.common.utils.Log;

public class ActionMenuInflater {
	private static final String TAG = ActionMenuInflater.class.getSimpleName();

    /** Menu tag name in XML. */
    private static final String XML_MENU = "menu";
    
    /** Item tag name in XML. */
    private static final String XML_ITEM = "item";

    private static final int NO_ID = 0;
    
    private Context mContext;
    
    public ActionMenuInflater(Context context){
    	mContext = context;
    }
    
    /**
     * Inflate a ActionMenu hierarchy from the specified XML resource. Throws
     * {@link InflateException} if there is an error.
     * 
     * @param menuRes Resource ID for an XML layout resource to load (e.g.,
     *            <code>R.menu.main_activity</code>)
     * @param menu The Menu to inflate into. The items will be
     *            added to this Menu.
     */
    public void inflate(int menuRes, ActionMenu actionMenu) {
        XmlResourceParser parser = null;
        try {
            parser = mContext.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);
            
            parseMenu(parser, attrs, actionMenu);
        } catch (XmlPullParserException e) {
            throw new InflateException("Error inflating menu XML", e);
        } catch (IOException e) {
            throw new InflateException("Error inflating menu XML", e);
        } finally {
            if (parser != null) parser.close();
        }
    }
	
    /**
     * Called internally to fill the given actionmenu. 
     */
    private void parseMenu(XmlPullParser parser, AttributeSet attrs, ActionMenu actionMenu)
            throws XmlPullParserException, IOException {
        MenuState menuState = new MenuState(actionMenu);

        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }
                
                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);
        
        boolean reachedEndOfMenu = false;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }
                    
                    tagName = parser.getName();
                    if (tagName.equals(XML_ITEM)) {
                        menuState.readItem(attrs);
                    } else {
                        lookingForEndOfUnknownTag = true;
                        unknownTagName = tagName;
                    }
                    break;
                    
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } if (tagName.equals(XML_ITEM)) {
                    	menuState.addItem();
                    } else if (tagName.equals(XML_MENU)) {
                        reachedEndOfMenu = true;
                    }
                    break;
                    
                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }
            
            eventType = parser.next();
        }
    }
    
    /**
     * State for the current menu.
     * <p>
     * Groups can not be nested unless there is another menu (which will have
     * its state class).
     */
    private class MenuState {
        private ActionMenu actionMenu;

        private int itemId;
        private CharSequence itemTitle;
        private int itemIconEnableResId;
        private int itemIconDisableResId;
        private boolean itemEnabled;
        

        private static final int defaultItemId = NO_ID;
        private static final boolean defaultItemEnabled = true;
        
        public MenuState(ActionMenu actionMenu) {
            this.actionMenu = actionMenu;
        }

        
        /**
         * Called when the parser is pointing to an item tag.
         */
        public void readItem(AttributeSet attrs) {
            TypedArray a = mContext.obtainStyledAttributes(attrs,
                    R.styleable.ZyMenuItem);

            // Inherit attributes from the group as default value
            itemId = a.getResourceId(R.styleable.ZyMenuItem_id, defaultItemId);
            itemTitle = a.getText(R.styleable.ZyMenuItem_title);
            itemIconEnableResId = a.getResourceId(R.styleable.ZyMenuItem_iconEnable, 0);
            itemIconDisableResId = a.getResourceId(R.styleable.ZyMenuItem_iconDisable, 0);
            itemEnabled = a.getBoolean(R.styleable.ZyMenuItem_enable, defaultItemEnabled);
            
            Log.d(TAG, "=============getItem===========");
            Log.d(TAG, "itemId=" + itemId);
            Log.d(TAG, "itemTitle=" + itemTitle);
            Log.d(TAG, "itemIconEnableResId=" + itemIconEnableResId);
            Log.d(TAG, "itemIconDisableResId=" + itemIconDisableResId);
            Log.d(TAG, "itemEnabled=" + itemEnabled);
            Log.d(TAG, "=============getItem===========");
            a.recycle();

        }
        
        private void setItem(ActionMenuItem item) {
        	item.setTitle((String) itemTitle);
        	item.setEnableIcon(itemIconEnableResId);
        	item.setDisableIcon(itemIconDisableResId);
        	item.setEnable(itemEnabled);
        }

        public void addItem() {
            setItem(actionMenu.addItem(itemId));
        }
    }
}
