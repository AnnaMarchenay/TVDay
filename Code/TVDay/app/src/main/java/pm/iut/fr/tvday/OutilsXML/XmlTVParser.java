/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package pm.iut.fr.tvday.OutilsXML;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pm.iut.fr.tvday.programmeDUneChaine.Program;

/**
 * Permet de parser du XML
 */
public class XmlTVParser {
    private static final String ns = null;

    public ArrayList<Program> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }


    private ArrayList<Program> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Program> programs = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                    programs=readSurplus(parser);
            } else {
                skip(parser);
            }
        }
        return programs;
    }

    /**
     * Permet de passer les valeurs inutiles pour plus tard
     * @param parser XmlPullParser
     * @return une liste de programmes
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private ArrayList<Program> readSurplus(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Program> programs = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        String title = null;
        String link = null;
        String description = null;
        while (parser.next() != XmlPullParser.END_TAG)  {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readOtherTitle(parser);
                    break;
                case "link":
                    link = readLink(parser);
                    break;
                case "description":
                    description = readDescription(parser);
                    break;
                case "item":
                    programs.add(readProgram(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return programs;
    }

    /**
     * Parse un programme
     * @param parser XmlPullParser
     * @return un programme
     * @throws XmlPullParserException exception
     * @throws IOException exception
     */
    private Program readProgram(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        ArrayList<String> title = new ArrayList<>();
        String link = null;
        String category = null;
        String description = null;
        String comments = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "link":
                    link = readLink(parser);
                    break;
                case "category":
                    category = readCategory(parser);
                    break;
                case "description":
                    description = readDescription(parser);
                    break;
                case "comments":
                    comments = readComments(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Program(link,category,description, comments,title.get(0),title.get(1),title.get(2));
    }


    private String readComments(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "comments");
        String comments = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "comments");
        return comments;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    private String readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "category");
        String category = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return category;
    }

    private  ArrayList<String> readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        ArrayList<String> title = new ArrayList<>();
        for ( String element: readText(parser).split("\\|")) {
            if (!element.contains("|")){
                //Chaines inexistantes dans le xml
                if (element.equals(" ")){
                    title.add("TF1 Séries Films et TFX");
                }else {
                    title.add(element);
                }
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "title");
        if(title.size()==2){
            title.add("");
        }
        return title;
    }

    private String readOtherTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private  String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }



    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
