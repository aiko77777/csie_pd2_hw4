import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class TFIDFCalculator{
    public static void main(String[] args) {
        try {
            ArrayList<String> text_section=new ArrayList<>();
            //ArrayList<String> tem_lines=new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));    //read parameter 0 : docs.txt
            String line_1;  //the string section
            String line_2;  //at ?th of the text
            String tem="";
            int docs_order=1;
            while ((line_1 = reader.readLine()) != null) {
                line_1=(line_1.replaceAll("[^a-zA-Z\\s]", " ")).replaceAll("\\s+"," "); //regular expression :replace all the non-English char and multiple space to unit space
                tem=tem+line_1;            
                if(docs_order%5==0){
                     text_section.add(tem.replaceAll("\\s+"," "));
                     tem="";
                }
                docs_order++;
            }
            System.out.println(text_section.get(0));
            System.out.println("------------------");
            System.out.println(text_section.get(1));
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
