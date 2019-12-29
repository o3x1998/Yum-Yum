package ddwu.mobile.final_project.ma02_20170915;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class NaverBlogXmlParser {

    public enum TagType { NONE, TITLE, DESCRIPTION, POSTDATE };

    public NaverBlogXmlParser() {
    }

    public ArrayList<NaverBlogDto> parse(String xml) {

        ArrayList<NaverBlogDto> resultList = new ArrayList();
        NaverBlogDto dto = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            dto = new NaverBlogDto();
                        } else if (parser.getName().equals("title")) {
                            if (dto != null) tagType = TagType.TITLE;
                        } else if (parser.getName().equals("description")) {
                            if (dto != null) tagType = TagType.DESCRIPTION;
                        } else if (parser.getName().equals("postdate")) {
                            if (dto != null) tagType = TagType.POSTDATE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case TITLE:
                                dto.setTitle(parser.getText());
                                break;
                            case DESCRIPTION:
                                dto.setDescription(parser.getText());
                                break;
                            case POSTDATE:
                                dto.setPostdate(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
