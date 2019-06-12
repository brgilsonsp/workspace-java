package br.com.gilson.estudo.readProperties;

import java.io.IOException;
import java.util.Properties;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	Properties prop = OurProperties.getPropertiesInformix();
        System.out.println(prop.get("prop.tree.name"));
        System.out.println(prop.get("prop.tree.secondname"));
    }
}
