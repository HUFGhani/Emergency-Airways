package io.github.hufghani.emergencyairways.utils;

import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan.Standard;

import org.xml.sax.XMLReader;

import java.util.Vector;

public class HtmlTagHandler implements TagHandler {
    private static final String TAG = HtmlTagHandler.class.getSimpleName();
    private int listItemCount = 0;
    private Vector<String> listParents = new Vector();

    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        Logcat.m15i(TAG, (opening ? "Opening" : "Closing") + " tag " + tag);
        if (tag.equals("ul") || tag.equals("ol") || tag.equals("dd")) {
            if (opening) {
                this.listParents.add(tag);
            } else {
                this.listParents.remove(tag);
            }
            this.listItemCount = 0;
        } else if (tag.equals("li") && !opening) {
            handleListTag(output);
        }
    }

    private void handleListTag(Editable output) {
        String[] split;
        if (this.listParents.lastElement().equals("ul")) {
            output.append("\n");
            split = output.toString().split("\n");
            output.setSpan(new BulletSpan(this.listParents.size() * 15), (output.length() - split[split.length - 1].length()) - 1, output.length(), 0);
        } else if (this.listParents.lastElement().equals("ol")) {
            this.listItemCount++;
            output.append("\n");
            split = output.toString().split("\n");
            int start = (output.length() - split[split.length - 1].length()) - 1;
            output.insert(start, this.listItemCount + ". ");
            output.setSpan(new Standard(this.listParents.size() * 15), start, output.length(), 0);
        }
    }
}
