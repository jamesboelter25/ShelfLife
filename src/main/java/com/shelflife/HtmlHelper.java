package com.shelflife;

// I made this to change < > & and quotes into safe codes before I show them on my page
// so if someone types <script> in the notes it just shows as text (stops XSS)
public class HtmlHelper {

    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("&", "&amp;");   // I have to do this one first
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("'", "&#39;");
        return text;
    }
}
