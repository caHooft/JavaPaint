package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class saves shapes as a txt file and can also load txt files that follow the same format
//this class also handles conversion to and from TXT files
public class TxtConverter 
{
    //sets path to where the safe file. 
    //this should first be checked when having issues on first try on new platform!
    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    private final String standardFilePath = path.toString().replace("\\", "/");
    private final String savedItemPath = "/saveFile/";
    
    //this keeps track of the groups saved in the save file
    private int groupNum = -1;

    //Create a savefile in txt format
    public void SaveShapeToFile(ArrayList<BaseShape> shapeList) throws Exception 
    {
        String fileName = "savedshapes";
        new File(standardFilePath + "/saveFile").mkdirs();
        FileOutputStream fileStream = new FileOutputStream(new File(standardFilePath + savedItemPath + fileName + ".txt"));

        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, "windows-1252")) 
        {
            for (BaseShape s : shapeList) 
            {
                String stringText = "";
                String groupNumText = "";
                String tabSpacer = "\t";
                String repeatTab = tabSpacer.repeat(groupNum+1);

                if(s.GetGroup() != null)
                {
                    groupNum++;
                    if(groupNum == 0)
                    {
                        groupNumText += "group" + " "+groupNum +"\n";
                        repeatTab = tabSpacer.repeat(groupNum+1);
                    }
                    else
                        groupNumText += repeatTab +"group" + " "+groupNum +"\n";
                    for (Text text : s.textList)
                        stringText += "ornament " + text.textPosition + " \""+ text.text + "\"\n";
                    if (s instanceof Rectangle) 
                    {
                        outputStreamWriter
                                .write(groupNumText + repeatTab +stringText + "rectangle" + " " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight() + '\n');
                        System.out.println(stringText + "Rectangle: " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight());
                    } else if (s instanceof Circle)
                    {
                        outputStreamWriter
                                .write(groupNumText + repeatTab +stringText + "ellipse" + " " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight() + '\n');
                        System.out.println(stringText + "Ellipse: " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight());
                    }
                    for (BaseShape b : s.GetGroup())
                    {
                        for (Text text : b.textList) 
                        {
                            stringText += "ornament " + text.textPosition + " \""+ text.text + "\"\n";
                        }
        
                        if (b instanceof Rectangle)
                         {
                            outputStreamWriter
                                    .write(repeatTab +stringText + repeatTab +"rectangle" + " " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight() + '\n');
                            System.out.println(stringText + "Rectangle: " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight());
                        } 
                        
                        else if (b instanceof Circle) 
                        {
                            outputStreamWriter
                                    .write(repeatTab +stringText + repeatTab +"ellipse" + " " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight() + '\n');
                            System.out.println(stringText + "Ellipse: " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight());
                        }
                    }
                }
                else if (s.GetGroup() == null)
                {
                    for (BaseShape x : shapeList)
                    {
                        try
                        {
                            for (BaseShape t : x.GetGroup())
                            {
                                if (t.GetGroup().contains(s) && t.GetGroup() != null)
                                {
                                    System.out.println("In a group");
                                }
                                else
                                {
                                    for (Text text : s.textList) 
                                    {
                                        stringText += "ornament " + text.textPosition + " \""+ text.text + "\"\n";
                                    }

                                    if (s instanceof Rectangle) 
                                    {
                                        outputStreamWriter
                                                .write(stringText + "rectangle" + " " +s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight() + '\n');
                                        System.out.println(stringText + "Rectangle: " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight());
                                    } 

                                    else if (s instanceof Circle) 
                                    {
                                        outputStreamWriter
                                                .write(stringText + "ellipse" + " " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight() + '\n');
                                        System.out.println(stringText + "Ellipse: " + s.getX() + " " + s.getY() + " " + s.getWidth() + " " + s.getHeight());
                                    }
                                }
                            }
                        }
                        catch(NullPointerException e){}
                    }
                }
            }
            outputStreamWriter.close();
        }
    }
    
    //Load the savefile from txt format
    public void LoadShapeFromFile() throws Exception 
    {

        String fileName = "savedshapes";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(standardFilePath + savedItemPath + fileName + ".txt")), "ISO-8859-1"))) 
        {

            String line = "";
            String groupNumLoad = "";
            String ornamentPos = "";
            String ornamentText = "";
            List<BaseShape> tA = new ArrayList<BaseShape>();
            
            while ((line = br.readLine()) != null) 
            {
                Matcher matcher = Pattern.compile("\\t*(.*?)\\s(\\d*)\\s(\\d*)\\s(\\d*)\\s(\\d*)").matcher(line);
                Matcher ornamentMatch = Pattern.compile("\\t*(.*?)\\s(.*?)\\s\\\"(.*?)\\\"").matcher(line);
                Matcher groupMatch = Pattern.compile("\\t*(.*?)\\s(\\d*)").matcher(line);

                if(line.contains("group"))
                {
                    if(groupMatch.find())
                    {
                        groupNumLoad = groupMatch.group(2);
                        if(!tA.isEmpty())
                            tA.clear();
                    }
                }
                if(line.contains("ornament"))
                {
                    if(ornamentMatch.find())
                    {
                        ornamentPos = ornamentMatch.group(2);
                        ornamentText = ornamentMatch.group(3);
                    }
                }
                if (line.contains("rectangle"))
                 {
                    if (matcher.find())
                     {
                        BaseShape rectangle = new Rectangle();
                        rectangle.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));
                        tA.add(rectangle);

                        if(ornamentPos != "" && ornamentText != "")
                        {
                            rectangle.addText(ornamentPos, ornamentText);
                            ornamentPos = "";
                            ornamentText = "";
                        }

                        if(groupNumLoad != "" && !tA.isEmpty())
                        {
                            tA.get(0).CreateGroup(rectangle);
                        }
                    }

                } else if (line.contains("ellipse")) 
                {
                    if (matcher.find()) 
                    {
                        BaseShape oval = new Circle();
                        oval.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));

                        if(ornamentPos != "" && ornamentText != "")
                        {
                            oval.addText(ornamentPos, ornamentText);
                            ornamentPos = "";
                            ornamentText = "";
                        }

                        if(groupNumLoad != "" && !tA.isEmpty())
                        {
                            tA.get(0).CreateGroup(oval);
                        }
                    }
                }
            }
            br.close();
        }
    }
}