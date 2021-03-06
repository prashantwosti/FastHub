package com.prettifier.pretty.helper;

import android.support.annotation.NonNull;

/**
 * Created by Kosh on 25 Dec 2016, 9:12 PM
 */

public class PrettifyHelper {

    @NonNull private static String getHtmlContent(@NonNull String css, @NonNull String text, @NonNull String wrapStyle) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"./styles/" + css + "\">\n" +
                "    " + wrapStyle + "\n" +
                "</head>\n" +
                "<body onload=\"" + (textTooLarge(text) ? "" : "prettyPrint()") + "\">\n" +
                "<pre class=\"prettyprint linenums\">" + text + "</pre>\n" +
                "<script src=\"./js/prettify.js\"></script>\n" +
                "<script>\n" +
                "function scrollToLineNumber(lineNo) {\n" +
                "    var normalizedLineNo = (lineNo - 1) %% 10;\n" +
                "    var nthLineNo = Math.floor((lineNo - 1) / 10);\n" +
                "    var elLines = document.querySelectorAll('li.L' + normalizedLineNo);\n" +
                "    if (elLines[nthLineNo]) {\n" +
                "        elLines[nthLineNo].scrollIntoView();\n" +
                "    }\n" +
                "}" +
                "</script>" +
                "</body>\n" +
                "</html>";
    }

    @NonNull private static final String WRAPPED_STYLE =
            "<style>\n " +
                    "pre, pre code, table {\n" +
                    "    white-space: pre-wrap !important;\n" +
                    "    word-wrap: break-all !important;\n" +
                    "    word-wrap: break-word !important;\n" +
                    "}\n" +
                    "img {\n" +
                    "    max-width: 100% !important;\n" +
                    "}\n" +
                    "ol {\n" +
                    "    margin-left: 0 !important;\n" +
                    "    padding-left: 6px !important;\n" +
                    "}\n" +
                    "ol li {\n" +
                    "    margin-left: 0  !important;\n" +
                    "    padding-left: 0  !important;\n" +
                    "    text-indent: -12px !important;\n" +
                    "}" +
                    "</style>";


    @NonNull public static String generateContent(@NonNull String source, boolean isDark, boolean wrap) {
        return getHtmlContent(getStyle(isDark), getFormattedSource(source), wrap ? WRAPPED_STYLE : "");
    }

    @NonNull private static String getFormattedSource(@NonNull String source) {
        return source.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    @NonNull private static String getStyle(boolean isDark) {
        return !isDark ? "prettify.css" : "prettify_dark.css";
    }

    private static boolean textTooLarge(@NonNull String text) {
        return text.length() > 204800;//>200kb ? disable highlighting to avoid crash.
    }

}
