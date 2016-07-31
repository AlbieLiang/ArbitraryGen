package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 *
 * @author AlbieLiang
 */
public class XmlUtils {

    /**
     * The method will parse the XML InputStream and return a notes list.
     * At the end, it will close the InputStream.
     * If you do not want it close the stream, you can invoke
     * {@link #getXmlNotes(InputStream, SaxXmlParserHandler, boolean)} instead.
     *
     * @param ins the input stream
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @return XML notes information list
     */
    public static List<RawTemplate> getXmlNotes(InputStream ins, SaxXmlParserHandler handler) {
        return getXmlNotes(ins, handler, true);
    }

    /**
     * The method will parse the XML InputStream and return a notes list.
     *
     * @param ins the input stream
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @param close   true : close the InputStream at the end of the method, false : otherwise
     * @return XML notes information list
     *
     * @see #getXmlNotes(InputStream, SaxXmlParserHandler)
     */
    public static List<RawTemplate> getXmlNotes(InputStream ins, SaxXmlParserHandler handler, boolean close) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(ins, handler);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null && close) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return handler.getXmlNotes();
    }

    /**
     * The method will parse the XML InputStream and return a notes list. At the end, it will close the InputStream.
     *
     * @param file the input {@link File}
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @return XML notes information list
     *
     * @see #getXmlNotes(InputStream, SaxXmlParserHandler)
     */
    public static List<RawTemplate> getXmlNotes(File file, SaxXmlParserHandler handler) {
        InputStream ins = null;
        try {
            ins = new FileInputStream(file);
            return getXmlNotes(ins, handler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * {@link #getXmlNotes(File, SaxXmlParserHandler)}
     *
     * @param file the input {@link File}
     * @return XML notes information list
     */
    public static List<RawTemplate> getXmlNotes(File file) {
        return getXmlNotes(file, new SaxXmlParserHandler());
    }
}
