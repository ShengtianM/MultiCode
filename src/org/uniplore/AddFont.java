package org.uniplore;

import org.apache.fop.fonts.apps.TTFReader;

public class AddFont {
    public static void main(String args[]){
        String[] parameters = {
        "-ttcname",
        "simkai",
        "e:\\simkai.ttf", "e:simkai.xml", };
        TTFReader.main(parameters);
        }
}
